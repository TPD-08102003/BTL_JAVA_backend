package org.example.btl_java.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ImagesDTO {
    private Integer imageId;

    @NotNull(message = "ID sản phẩm không được để trống")
    private Integer productId;

    @NotBlank(message = "Đường dẫn ảnh không được để trống")
    private String imagePath;

    // Constructor mặc định
    public ImagesDTO() {}

    // Constructor đầy đủ
    public ImagesDTO(Integer imageId, Integer productId, String imagePath) {
        this.imageId = imageId;
        this.productId = productId;
        this.imagePath = imagePath;
    }

    // Getters và Setters
    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}