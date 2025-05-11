package org.example.btl_java.DTO;

import java.time.ZonedDateTime;

public class CartDTO {
    private Long cartId;
    private Integer accountId;
    private Long productId;  // Thay đổi từ Integer sang Long
    private Integer quantity;
    private ZonedDateTime addedAt;

    // Constructors
    public CartDTO() {}

    public CartDTO(Integer accountId, Long productId, Integer quantity) {
        this.accountId = accountId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Long getProductId() {  // Đổi kiểu trả về thành Long
        return productId;
    }

    public void setProductId(Long productId) {  // Đổi tham số thành Long
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ZonedDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(ZonedDateTime addedAt) {
        this.addedAt = addedAt;
    }
}