package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
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

    @Autowired
    CustomerRepository customerRepository;

    // reference: https://knowledge.udacity.com/questions/430058
    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Customer customer = null;

        if ((Long) petDTO.getOwnerId() != null) { // returns true all the time
            customer = customerRepository.getOne(petDTO.getOwnerId());
        }

        Pet petToSave = convertDtoToPetEntity(petDTO);
        petToSave.setOwner(customer);
        Pet petSaved = petService.addPet(petToSave);

        return convertPetEntityToDto(petSaved);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
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

    // DTOs
    public Pet convertDtoToPetEntity(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }

    public PetDTO convertPetEntityToDto(Pet pet){
        PetDTO dto = new PetDTO();
        BeanUtils.copyProperties(pet, dto);

        dto.setOwnerId(pet.getOwner().getId());
        return dto;
    }

    private List<PetDTO> listToDTO(List<Pet> pets) {
        ModelMapper modelMapper = new ModelMapper();
        List<PetDTO> petDTOList = Arrays.asList(modelMapper.map(pets, PetDTO[].class));

        return petDTOList;
    }
}
