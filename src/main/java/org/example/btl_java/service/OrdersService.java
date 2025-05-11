package org.example.btl_java.service;

import org.example.btl_java.DTO.OrdersDTO;
import org.example.btl_java.model.Customers;
import org.example.btl_java.model.Orders;
import org.example.btl_java.repository.CustomersRepository;
import org.example.btl_java.repository.OrdersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final CustomersRepository customersRepository;

    public OrdersService(OrdersRepository ordersRepository, CustomersRepository customersRepository) {
        this.ordersRepository = ordersRepository;
        this.customersRepository = customersRepository;

    }

    public List<OrdersDTO> getAllOrders() {
        return ordersRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    public OrdersDTO getOrderById(Integer id) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToDTO(order);
    }

    public OrdersDTO createOrder(OrdersDTO orderDTO) {
        Orders order = new Orders();

        // Tìm khách hàng theo ID
        Customers customer = customersRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        order.setCustomer(customer);
        order.setTotalAmount(orderDTO.getTotalAmount());  // Giữ nguyên nếu bạn truyền tổng tiền từ client
        order.setStatus("Pending"); // Có thể thay đổi tùy ý

        Orders savedOrder = ordersRepository.save(order);
        return convertToDTO(savedOrder);
    }

    public OrdersDTO updateOrder(Integer id, OrdersDTO orderDTO) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Customers customer = customersRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        order.setCustomer(customer);
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setStatus("Updated"); // Tùy chỉnh nếu cần

        Orders updatedOrder = ordersRepository.save(order);
        return convertToDTO(updatedOrder);
    }

    public void deleteOrder(Integer id) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        ordersRepository.delete(order);
    }
    public OrdersDTO approveOrder(Integer id) {
        Optional<Orders> optionalOrder = ordersRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Orders order = optionalOrder.get();
            if (!"Approved".equalsIgnoreCase(order.getStatus())) {
                order.setStatus("Approved");
                Orders updatedOrder = ordersRepository.save(order);
                return convertToDTO(updatedOrder);
            } else {
                throw new RuntimeException("Order with ID " + id + " is already approved.");
            }
        } else {
            throw new RuntimeException("Order with ID " + id + " not found.");
        }
    }
    private OrdersDTO convertToDTO(Orders order) {
        OrdersDTO dto = new OrdersDTO();
        dto.setCustomerFullName(order.getCustomer().getFirstname() + " " + order.getCustomer().getLastname());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setCustomerId(order.getCustomer().getCustomerId());
        return dto;
    }


}
