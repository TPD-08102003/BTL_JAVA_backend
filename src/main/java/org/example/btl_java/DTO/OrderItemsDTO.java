package org.example.btl_java.DTO;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import javax.lang.model.element.Name;
import java.math.BigDecimal;

public class OrderItemsDTO {
    private Integer orderItemId;

    @NotNull(message = "ID đơn hàng không được để trống")
    private Integer orderId;

    @NotNull(message = "ID sản phẩm không được để trống")
    private Integer productId;

    @Positive(message = "Số lượng phải lớn hơn 0")
    private Integer quantity;

    @Positive(message = "Giá phải lớn hơn 0")
    private BigDecimal price;

    // Constructor
    public OrderItemsDTO() {}

    public OrderItemsDTO(Integer orderItemId, Integer orderId, Integer productId, Integer quantity, BigDecimal price) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters và Setters
    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


}