package org.example.btl_java.controller;

import jakarta.validation.Valid;
import org.example.btl_java.DTO.CategoriesDTO;
import org.example.btl_java.service.CategoriesService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/Categories")
public class CategoriesController {
    private final CategoriesService categoriesService;

    // Constructor injection
    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriesDTO>> getAllCategories() {
        List<CategoriesDTO> categories = categoriesService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoriesDTO> createCategory(@Valid @RequestBody CategoriesDTO categoryDTO) {
        try {
            CategoriesDTO createdCategory = categoriesService.createCategory(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriesDTO> updateCategory(@PathVariable Integer id, @Valid @RequestBody CategoriesDTO categoryDTO) {
        try {
            CategoriesDTO updatedCategory = categoriesService.updateCategory(id, categoryDTO);
            return ResponseEntity.ok(updatedCategory);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        try {
            categoriesService.deleteCategory(id);
            return ResponseEntity.ok("Category deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriesDTO> getCategoryById(@PathVariable Integer id) {
        try {
            CategoriesDTO category = categoriesService.getCategoryById(id);
            return ResponseEntity.ok(category);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}