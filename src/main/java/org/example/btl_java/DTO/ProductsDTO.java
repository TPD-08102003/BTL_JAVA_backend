package org.example.btl_java.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class ProductsDTO {
    private Integer productId;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String productName;

    @Positive(message = "Giá sản phẩm phải lớn hơn 0")
    private BigDecimal price; // Đổi từ double sang BigDecimal

    private Integer categoryId;

    private Integer supplierId;

    private String description;

    @PositiveOrZero(message = "Số lượng sản phẩm không được âm")
    private int quantity;





    public ProductsDTO(Integer productId, String productName, BigDecimal price, Integer categoryId,
                       Integer supplierId, String description, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
        this.description = description;
        this.quantity = quantity;
    }

    // Getters và Setters
    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public BigDecimal getPrice() { return price; } // Đổi từ double sang BigDecimal
    public void setPrice(BigDecimal price) { this.price = price; } // Đổi từ double sang BigDecimal
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public Integer getSupplierId() { return supplierId; }
    public void setSupplierId(Integer supplierId) { this.supplierId = supplierId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}