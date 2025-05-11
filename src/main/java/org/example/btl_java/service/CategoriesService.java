package org.example.btl_java.service;

import org.example.btl_java.DTO.CategoriesDTO;
import org.example.btl_java.model.Categories;
import org.example.btl_java.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriesService {
    @Autowired
    private CategoriesRepository categoriesRepository;

    public List<CategoriesDTO> getAllCategories() {
        return categoriesRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CategoriesDTO createCategory(CategoriesDTO categoryDTO) {
        validateCategoryDTO(categoryDTO);
        Categories category = toEntity(categoryDTO);
        Categories savedCategory = categoriesRepository.save(category);
        return toDTO(savedCategory);
    }

    public CategoriesDTO updateCategory(Integer id, CategoriesDTO categoryDTO) {
        validateCategoryDTO(categoryDTO);
        Categories category = categoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setCategoryName(categoryDTO.getCategoryName());
        category.setDescription(categoryDTO.getDescription());

        Categories updatedCategory = categoriesRepository.save(category);
        return toDTO(updatedCategory);
    }

    public void deleteCategory(Integer id) {
        Categories category = categoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (category.getProducts() != null && !category.getProducts().isEmpty()) {
            throw new RuntimeException("Cannot delete category with products.");
        }

        categoriesRepository.delete(category);
    }

    public CategoriesDTO getCategoryById(Integer id) {
        Categories category = categoriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return toDTO(category);
    }

    // Ánh xạ Categories -> CategoriesDTO
    private CategoriesDTO toDTO(Categories category) {
        return new CategoriesDTO(
                category.getCategoryId(),
                category.getCategoryName(),
                category.getDescription()
        );
    }

    // Ánh xạ CategoriesDTO -> Categories
    private Categories toEntity(CategoriesDTO categoryDTO) {
        Categories category = new Categories();
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }

    // Validation cơ bản
    private void validateCategoryDTO(CategoriesDTO categoryDTO) {
        if (categoryDTO.getCategoryName() == null || categoryDTO.getCategoryName().trim().isEmpty()) {
            throw new RuntimeException("Tên danh mục không được để trống");
        }
    }
}