package org.example.btl_java.model;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Integer imageId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "image_path", nullable = false)
    private String imagePath;

    // Constructor mặc định
    public Images() {}

    // Constructor đầy đủ
    public Images(Integer imageId, Integer productId, String imagePath) {
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