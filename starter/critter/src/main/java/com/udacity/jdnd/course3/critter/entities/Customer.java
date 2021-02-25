package com.udacity.jdnd.course3.critter.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String phoneNumber;
    private String notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", targetEntity = Pet.class, cascade = CascadeType.ALL)
    private Set<Pet> pets;

    public void addPet(Pet pet) {
        if (pets == null) {
            pets = new HashSet<>();
        }

        pets.add(pet);
    }
}
