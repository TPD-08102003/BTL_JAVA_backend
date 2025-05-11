package org.example.btl_java.service;

import org.example.btl_java.DTO.OrderItemsDTO;
import org.example.btl_java.model.OrderItems;
import org.example.btl_java.model.Orders;
import org.example.btl_java.model.Products;
import org.example.btl_java.repository.OrderItemsRepository;
import org.example.btl_java.repository.OrdersRepository;
import org.example.btl_java.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemsService {
    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private ProductsRepository productsRepository;

    public List<OrderItemsDTO> getAllOrderItems() {
        return orderItemsRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public OrderItemsDTO getOrderItemById(Integer id) {
        OrderItems orderItem = orderItemsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order item not found"));
        return toDTO(orderItem);
    }

    public OrderItemsDTO createOrderItem(OrderItemsDTO orderItemDTO) {
        validateOrderItemDTO(orderItemDTO);
        OrderItems orderItem = toEntity(orderItemDTO);
        OrderItems savedOrderItem = orderItemsRepository.save(orderItem);
        return toDTO(savedOrderItem);
    }

    public OrderItemsDTO updateOrderItem(Integer id, OrderItemsDTO orderItemDTO) {
        validateOrderItemDTO(orderItemDTO);
        OrderItems orderItem = orderItemsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setPrice(orderItemDTO.getPrice());

        if (orderItemDTO.getOrderId() != null) {
            Orders order = ordersRepository.findById(orderItemDTO.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            orderItem.setOrder(order);
        }

        if (orderItemDTO.getProductId() != null) {
            Products product = productsRepository.findById(orderItemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            orderItem.setProduct(product);
        }

        OrderItems updatedOrderItem = orderItemsRepository.save(orderItem);
        return toDTO(updatedOrderItem);
    }

    public void deleteOrderItem(Integer id) {
        if (!orderItemsRepository.existsById(id)) {
            throw new RuntimeException("Order item not found");
        }
        orderItemsRepository.deleteById(id);
    }

    private OrderItemsDTO toDTO(OrderItems orderItem) {
        return new OrderItemsDTO(
                orderItem.getOrderItemId(),
                orderItem.getOrder() != null ? orderItem.getOrder().getOrderId() : null,
                orderItem.getProduct() != null ? orderItem.getProduct().getProductId() : null,
                orderItem.getQuantity(),
                orderItem.getPrice()
        );
    }

    private OrderItems toEntity(OrderItemsDTO orderItemDTO) {
        OrderItems orderItem = new OrderItems();
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setPrice(orderItemDTO.getPrice());

        if (orderItemDTO.getOrderId() != null) {
            Orders order = ordersRepository.findById(orderItemDTO.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            orderItem.setOrder(order);
        }

        if (orderItemDTO.getProductId() != null) {
            Products product = productsRepository.findById(orderItemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            orderItem.setProduct(product);
        }

        return orderItem;
    }

    private void validateOrderItemDTO(OrderItemsDTO orderItemDTO) {
        if (orderItemDTO.getOrderId() == null) {
            throw new RuntimeException("Order ID is required");
        }
        if (orderItemDTO.getProductId() == null) {
            throw new RuntimeException("Product ID is required");
        }
        if (orderItemDTO.getQuantity() == null || orderItemDTO.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }
        if (orderItemDTO.getPrice() == null || orderItemDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Price must be greater than 0");
        }
    }
}