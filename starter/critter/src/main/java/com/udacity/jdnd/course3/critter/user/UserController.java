package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = convertCustomerDTOToEntity(customerDTO);
        Customer newAddedCustomer = customerService.addCustomer(customer);

        return convertCustomerEntityToDTO(newAddedCustomer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();

        return customerListToDTO(customers);
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.getPet(petId);
        Customer owner = customerService.findByPet(pet);

        return convertCustomerEntityToDTO(owner);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertEmployeeDTOToEntity(employeeDTO);
        Employee newAddedEmployee = employeeService.addEmployee(employee);

        return convertEmployeeEntityToDTO(newAddedEmployee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);

        return convertEmployeeEntityToDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        LocalDate date = employeeDTO.getDate();
        HashSet<EmployeeSkill> skills = new HashSet<EmployeeSkill>(employeeDTO.getSkills());

        List<Employee> availableEmployees = employeeService.getAvailableEmployees(date, skills);
        return employeeListToDTO(availableEmployees);
    }

    // Customer DTOs
    private Customer convertCustomerDTOToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        return  customer;
    }

    private static CustomerDTO convertCustomerEntityToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);

        return customerDTO;
    }

    private List<CustomerDTO> customerListToDTO(List<Customer> customers) {
        ModelMapper modelMapper = new ModelMapper();
        List<CustomerDTO> customerDTOList =
                Arrays.asList(modelMapper.map(customers, CustomerDTO[].class));

        return customerDTOList;
    }

    // Employee DTOs
    private Employee convertEmployeeDTOToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        return  employee;
    }

    private static EmployeeDTO convertEmployeeEntityToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);

        return employeeDTO;
    }

    private List<EmployeeDTO> employeeListToDTO(List<Employee> employees) {
        ModelMapper modelMapper = new ModelMapper();
        List<EmployeeDTO> employeeDTOList =
                Arrays.asList(modelMapper.map(employees, EmployeeDTO[].class));

        return employeeDTOList;
    }
}
