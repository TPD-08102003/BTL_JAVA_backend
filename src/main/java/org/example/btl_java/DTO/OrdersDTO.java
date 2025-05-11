package org.example.btl_java.DTO;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrdersDTO {
    private Integer orderId;

    @NotNull(message = "Customer ID is required")
    private Integer customerId; // Using ID instead of the entire Customer object for DTO

    private LocalDateTime orderDate;

    @NotNull(message = "Total amount is required")
    private BigDecimal totalAmount;

    private String status;

    private List<OrderItemsDTO> orderItems; // Using DTO for related entities

    private String customerFullName; // Add customer's full name for easier display

    // Constructors

    public OrdersDTO() {
    }

    public OrdersDTO(Integer orderId, Integer customerId, LocalDateTime orderDate, BigDecimal totalAmount, String status, List<OrderItemsDTO> orderItems, String customerFullName) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderItems = orderItems;
        this.customerFullName = customerFullName;
    }

    // Getters and Setters

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItemsDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemsDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public String getCustomerFullName() {
        return customerFullName;
    }

    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }
}