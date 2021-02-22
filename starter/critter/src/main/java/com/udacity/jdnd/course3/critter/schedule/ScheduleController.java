package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import com.udacity.jdnd.course3.critter.services.PetService;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.checkerframework.checker.units.qual.A;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDTOToEntity(scheduleDTO);
        Schedule newAddedSchedule = scheduleService.addSchedule(schedule);

        return convertScheduleEntityToDTO(newAddedSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getSchedules();

        return scheduleListToDTO(schedules);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Pet pet = petService.getPet(petId);
        List<Schedule> schedules = scheduleService.getScheduleForPet(pet);

        return scheduleListToDTO(schedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        List<Schedule> schedules = scheduleService.getScheduleForEmployee(employee);

        return scheduleListToDTO(schedules);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = customerService.findById(customerId);
        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customer);

        return scheduleListToDTO(schedules);
    }

    // DTOs
    private Schedule convertScheduleDTOToEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        return  schedule;
    }

    private static ScheduleDTO convertScheduleEntityToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        return scheduleDTO;
    }

    private List<ScheduleDTO> scheduleListToDTO(List<Schedule> schedules) {
        ModelMapper modelMapper = new ModelMapper();
        List<ScheduleDTO> scheduleDTOList =
                Arrays.asList(modelMapper.map(schedules, ScheduleDTO[].class));

        return scheduleDTOList;
    }
}
