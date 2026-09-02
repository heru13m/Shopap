/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shopap.customer;

/**
 *
 * @author Mateusz
 */

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public CustomerResponse register(CustomerRegistrationRequest request) {
        Customer c = new Customer();
        c.setFirstName(request.getFirstName());
        c.setLastName(request.getLastName());
        c.setEmail(request.getEmail());
        c.setPhone(request.getPhone());
        c.setStreet(request.getStreet());
        c.setCity(request.getCity());
        c.setPostalCode(request.getPostalCode());
        c.setCountry(request.getCountry());

        Customer saved = repository.save(c);
        return toResponse(saved);
    }

    public Customer getByIdOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + id));
    }

    private CustomerResponse toResponse(Customer c) {
        CustomerResponse r = new CustomerResponse();
        r.setId(c.getId());
        r.setFirstName(c.getFirstName());
        r.setLastName(c.getLastName());
        r.setEmail(c.getEmail());
        r.setPhone(c.getPhone());
        r.setStreet(c.getStreet());
        r.setCity(c.getCity());
        r.setPostalCode(c.getPostalCode());
        r.setCountry(c.getCountry());
        return r;
    }
}
