package com.gaven.dzone_flyway.controller;

import com.gaven.dzone_flyway.entity.Contact;
import com.gaven.dzone_flyway.entity.Customer;
import com.gaven.dzone_flyway.repository.ContactRepository;
import com.gaven.dzone_flyway.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ContactRepository contactRepository;

    @GetMapping(path = "/customers")
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    @GetMapping(path = "/customers/{customerId}/contacts")
    public List<Contact> getCustomerContacts(@PathVariable("customerId") Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new EntityNotFoundException());
        return customer.getContacts();
    }

    @PostMapping(path = "/customers/{customerId}/contacts")
    public Contact newContact(@PathVariable("customerId") Long customerId, @RequestBody Contact contact){
        contactRepository.save(contact);
        return contact;
    }

    @PostMapping(path="/customers")
    public Customer newCustomer(@RequestBody Customer customer){
        customerRepository.save(customer);
        return customer;
    }

}
