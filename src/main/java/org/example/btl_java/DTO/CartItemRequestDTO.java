package org.example.btl_java.DTO;

import lombok.Data;

@Data
public class CartItemRequestDTO {
    private Integer productId;
    private Integer quantity;
}