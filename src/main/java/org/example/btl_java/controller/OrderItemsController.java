package org.example.btl_java.controller;

import jakarta.validation.Valid;
import org.example.btl_java.DTO.OrderItemsDTO;
import org.example.btl_java.service.OrderItemsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/OrderItems")
public class OrderItemsController {
    private final OrderItemsService orderItemsService;

    public OrderItemsController(OrderItemsService orderItemsService) {
        this.orderItemsService = orderItemsService;
    }

    @GetMapping
    public ResponseEntity<List<OrderItemsDTO>> getAllOrderItems() {
        List<OrderItemsDTO> orderItems = orderItemsService.getAllOrderItems();
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemsDTO> getOrderItemById(@PathVariable Integer id) {
        try {
            OrderItemsDTO orderItem = orderItemsService.getOrderItemById(id);
            return ResponseEntity.ok(orderItem);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<OrderItemsDTO> createOrderItem(@Valid @RequestBody OrderItemsDTO orderItemDTO) {
        try {
            OrderItemsDTO createdOrderItem = orderItemsService.createOrderItem(orderItemDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderItem);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemsDTO> updateOrderItem(@PathVariable Integer id, @Valid @RequestBody OrderItemsDTO orderItemDTO) {
        try {
            OrderItemsDTO updatedOrderItem = orderItemsService.updateOrderItem(id, orderItemDTO);
            return ResponseEntity.ok(updatedOrderItem);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderItem(@PathVariable Integer id) {
        try {
            orderItemsService.deleteOrderItem(id);
            return ResponseEntity.ok("Order item deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}