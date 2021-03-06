package com.example.demodeploy.controller;

import com.example.demodeploy.model.Customer;
import com.example.demodeploy.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomer() {
        List<Customer> customers = (List<Customer>) customerService.findAll();
        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //NO_CONTENT - 204
        }
        return new ResponseEntity<>(customers, HttpStatus.OK); // OK - 200
    }

    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED); //CREATED - 201
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        Optional<Customer> customerOptional = customerService.findById(id);
        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        Optional<Customer> customerOptional = customerService.findById(id);
        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customer.setId(id);
        customerService.save(customer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
        Optional<Customer> customerOptional = customerService.findById(id);
        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
