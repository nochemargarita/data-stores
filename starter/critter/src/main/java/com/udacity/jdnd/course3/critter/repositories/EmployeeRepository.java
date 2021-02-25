package com.udacity.jdnd.course3.critter.repositories;

import com.udacity.jdnd.course3.critter.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.Set;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Set<Employee> findEmployeeByAvailability(DayOfWeek dayOfWeek);
}
