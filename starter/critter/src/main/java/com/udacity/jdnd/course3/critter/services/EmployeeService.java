package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Long addEmployee(Employee employee) {
        return employeeRepository.save(employee).getId();
    }

    public void setEmployeeAvailability(Set<DayOfWeek> availability, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).get();
        employee.setAvailability(availability);
    }
}
