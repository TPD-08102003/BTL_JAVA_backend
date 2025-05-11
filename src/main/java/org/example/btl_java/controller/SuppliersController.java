package org.example.btl_java.controller;

import jakarta.validation.Valid;
import org.example.btl_java.DTO.SuppliersDTO;
import org.example.btl_java.service.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/Suppliers")
public class SuppliersController {
    @Autowired
    private SuppliersService suppliersService;

    @GetMapping
    public ResponseEntity<List<SuppliersDTO>> getAllSuppliers() {
        List<SuppliersDTO> suppliers = suppliersService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuppliersDTO> getSupplierById(@PathVariable Integer id) {
        try {
            SuppliersDTO supplier = suppliersService.getSupplierById(id);
            return ResponseEntity.ok(supplier);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<SuppliersDTO> createSupplier(@Valid @RequestBody SuppliersDTO supplierDTO) {
        try {
            SuppliersDTO createdSupplier = suppliersService.createSupplier(supplierDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSupplier);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuppliersDTO> updateSupplier(@PathVariable Integer id, @Valid @RequestBody SuppliersDTO supplierDTO) {
        try {
            SuppliersDTO updatedSupplier = suppliersService.updateSupplier(id, supplierDTO);
            return ResponseEntity.ok(updatedSupplier);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable Integer id) {
        try {
            suppliersService.deleteSupplier(id);
            return ResponseEntity.ok("Supplier deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}