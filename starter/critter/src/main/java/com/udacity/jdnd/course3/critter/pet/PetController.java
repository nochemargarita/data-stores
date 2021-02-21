package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.services.CustomerService;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTOToEntity(petDTO);
        Pet newAddedPet = petService.addPet(pet);

        return convertEntityToPetDTO(newAddedPet);
    }

    private Pet convertPetDTOToEntity(PetDTO petDTO) {
        Customer owner = customerService.findById(petDTO.getId());

        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setOwner(owner);

        Set<Pet> petsOwner = owner.getPets();

        if (petsOwner == null) {
            Set<Pet> newPetForOwner = new HashSet<Pet>();

            newPetForOwner.add(pet);
            owner.setPets(newPetForOwner);
        } else {
            petsOwner.add(pet);
            owner.setPets(petsOwner);
        }
        return pet;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable Long petId) {
        Pet pet = petService.getPet(petId);

        return convertEntityToPetDTO(pet);
    }

    private static PetDTO convertEntityToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);

        return petDTO;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getPets();

        return listToDTO(pets);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
       List<Pet> pets = petService.getPetsByOwner(ownerId);

       return listToDTO(pets);
    }

    private List<PetDTO> listToDTO(List<Pet> pets) {
        ModelMapper modelMapper = new ModelMapper();
        List<PetDTO> petDTOList = Arrays.asList(modelMapper.map(pets, PetDTO[].class));

        return petDTOList;
    }
}
