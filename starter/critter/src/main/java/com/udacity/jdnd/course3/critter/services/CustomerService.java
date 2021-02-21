package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findByPet(Pet pet) {
        return customerRepository.findCustomerByPetsContaining(pet);
    }

    public Long addCustomer(Customer customer) {
        return customerRepository.save(customer).getId();
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).get();
    }
}
