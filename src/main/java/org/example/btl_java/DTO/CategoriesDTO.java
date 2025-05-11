package org.example.btl_java.DTO;

import jakarta.validation.constraints.NotBlank;

public class CategoriesDTO {
    private Integer categoryId;

    @NotBlank(message = "Tên danh mục không được để trống")
    private String categoryName;

    private String description;

    // Constructor mặc định
    public CategoriesDTO() {}

    // Constructor đầy đủ
    public CategoriesDTO(Integer categoryId, String categoryName, String description) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
    }

    // Getters và Setters
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
