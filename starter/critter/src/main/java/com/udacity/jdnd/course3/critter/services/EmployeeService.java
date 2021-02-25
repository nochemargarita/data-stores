package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void setEmployeeAvailability(Set<DayOfWeek> availability, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).get();
        employee.setAvailability(availability);
    }

    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id).get();
    }

    public Set<Employee> getAvailableEmployees(LocalDate date, HashSet<EmployeeSkill> skills) {
        Set<Employee> availableEmployees =
                employeeRepository.findEmployeeByAvailability(date.getDayOfWeek());
        Set<Employee> employeesWithMatchSkills = new HashSet<>();

        availableEmployees.forEach(employee -> {
            boolean matched = employee.getSkills().containsAll(skills);
            if (matched) {
                employeesWithMatchSkills.add(employee);
            }
        });

        return employeesWithMatchSkills;
    }
}
