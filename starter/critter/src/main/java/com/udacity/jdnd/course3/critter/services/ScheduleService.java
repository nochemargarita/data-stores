package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule addSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(Pet pet) {
        return scheduleRepository.findSchedulesByPets(pet);
    }

    public List<Schedule> getScheduleForEmployee(Employee employee) {
        return scheduleRepository.findSchedulesByEmployees(employee);
    }

    public List<Schedule> getScheduleForCustomer(Customer customer) {
        Set<Pet> pets = customer.getPets();
        Set<Schedule> schedules = new HashSet<>();

        pets.forEach(pet -> {
            List<Schedule> scheduledPets = scheduleRepository.findSchedulesByPets(pet);
            // bulk add
            schedules.addAll(scheduledPets);
        });

        return (List<Schedule>) schedules;
    }
}
