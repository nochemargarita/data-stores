package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.pet.PetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue
    Long id;

    PetType type;
    String name;
    LocalDate birthDate;
    String notes;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Customer.class)
    private Customer owner;
}
