package org.example.btl_java.controller;

import jakarta.validation.Valid;
import org.example.btl_java.DTO.CustomersDTO;
import org.example.btl_java.service.CustomersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Customers")
public class CustomersController {
    private static final Logger logger = LoggerFactory.getLogger(CustomersController.class);

    private final CustomersService customersService;

    public CustomersController(CustomersService customersService) {
        this.customersService = customersService;
    }

    @GetMapping
    public ResponseEntity<List<CustomersDTO>> getAllCustomers() {
        logger.info("Received request to get all customers");
        List<CustomersDTO> customers = customersService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomersDTO> getCustomerById(@PathVariable Integer id) {
        logger.info("Received request to get customer with ID: {}", id);
        CustomersDTO customer = customersService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<CustomersDTO> createCustomer(@Valid @RequestBody CustomersDTO customerDTO) {
        logger.info("Received request to create customer: {}", customerDTO);
        CustomersDTO createdCustomer = customersService.createCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomersDTO> updateCustomer(@PathVariable Integer id, @Valid @RequestBody CustomersDTO customerDTO) {
        logger.info("Received request to update customer with ID: {}", id);
        CustomersDTO updatedCustomer = customersService.updateCustomer(id, customerDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCustomer(@PathVariable Integer id) {
        logger.info("Received request to delete customer with ID: {}", id);
        customersService.deleteCustomer(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Customer deleted successfully");
        return ResponseEntity.ok(response);
    }
}