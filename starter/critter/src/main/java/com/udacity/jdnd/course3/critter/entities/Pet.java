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
public class Pet {
    @Id
    @GeneratedValue
    long id;

    PetType type;
    String name;
    LocalDate birthDate;
    String notes;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Customer.class, optional = false)
    private Customer owner;
}
