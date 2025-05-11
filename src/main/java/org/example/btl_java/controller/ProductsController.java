package org.example.btl_java.controller;

import jakarta.validation.Valid;
import org.example.btl_java.DTO.ProductsDTO;
import org.example.btl_java.model.Images;
import org.example.btl_java.repository.ImagesRepository;
import org.example.btl_java.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/Products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;
    private ImagesRepository imageRepository;

    @PostMapping("")
    public ResponseEntity<ProductsDTO> createProduct(@Valid @RequestBody ProductsDTO productDTO) {
        try {
            ProductsDTO createdProduct = productsService.createProduct(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductsDTO> updateProduct(@PathVariable Integer id, @Valid @RequestBody ProductsDTO productDTO) {
        try {
            ProductsDTO updatedProduct = productsService.updateProduct(id, productDTO);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        try {
            productsService.deleteProduct(id);
            return ResponseEntity.ok("Sản phẩm đã được xóa thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductsDTO> getProductById(@PathVariable Integer id) {
        try {
            ProductsDTO product = productsService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<ProductsDTO>> getAllProducts() {
        List<ProductsDTO> products = productsService.getAllProducts();

        return ResponseEntity.ok(products);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }

}
