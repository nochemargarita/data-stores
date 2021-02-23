package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.pet.PetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Pet {
    @Id
    @GeneratedValue
    private long id;

    private PetType type;
    private String name;
    private LocalDate birthDate;
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Customer.class, optional = false)
    private Customer owner;

    @ManyToMany(targetEntity = Schedule.class)
    private List<Schedule> schedules;
}
