package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {
    @Autowired
    PetRepository petRepository;

    public List<Pet> getPetsByOwner(Long id) {
        return petRepository.findAllByOwnerId(id);
    }

    public Pet addPet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet getPet(Long id) {
        return petRepository.findById(id).get();
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }
}
