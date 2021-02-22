package com.udacity.jdnd.course3.critter.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    Long id;

    private String name;
    private String phoneNumber;
    private String notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", targetEntity = Pet.class, cascade = CascadeType.ALL)
    private Set<Pet> pets;
}
