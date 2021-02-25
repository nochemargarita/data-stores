package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public Schedule addSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow();

        return scheduleRepository.getSchedulesByPetsContains(pet);
    }

    public List<Schedule> getScheduleForEmployee(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();

        return scheduleRepository.getSchedulesByEmployeesContains(employee);
    }

    public List<Schedule> getScheduleForCustomer(Customer customer) {
        Set<Pet> pets = customer.getPets();
        List<Schedule> schedules = new ArrayList<>();

        pets.forEach(pet -> {
            List<Schedule> scheduledPets = scheduleRepository.getSchedulesByPetsContains(pet);
            // bulk add
            schedules.addAll(scheduledPets);
        });

        return schedules;
    }
}
