package org.example.btl_java.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.btl_java.DTO.OrdersDTO;
import org.example.btl_java.service.OrdersService;
import org.example.btl_java.controller.OrderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/Orders")
public class OrdersController {
    private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);
    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<OrdersDTO> approveOrder(@PathVariable Integer id) {
        logger.info("Approving order with ID: {}", id);
        if (id == null) {
            logger.error("Invalid ID: null");
            return ResponseEntity.badRequest().body(null);
        }
        OrdersDTO updatedOrder = ordersService.approveOrder(id);
        if (updatedOrder == null) {
            throw new OrderNotFoundException("Không tìm thấy đơn hàng với ID: " + id);
        }
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/{id}/export-pdf")
    public ResponseEntity<byte[]> exportOrderToPdf(@PathVariable Integer id, HttpServletResponse response) {
        try {
            if (id == null) {
                logger.error("Invalid ID for export PDF: null");
                return ResponseEntity.badRequest().body(null);
            }
            OrdersDTO order = ordersService.getOrderById(id);
            if (order == null) {
                throw new OrderNotFoundException("Không tìm thấy đơn hàng với ID: " + id);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);

            document.open();
            document.add(new Paragraph("Chi tiết đơn hàng"));
            document.add(new Paragraph("Mã đơn hàng: " + order.getOrderId()));
            document.add(new Paragraph("Ngày đặt hàng: " + order.getOrderDate()));
            document.add(new Paragraph("Tên khách hàng: " + order.getCustomerFullName()));
            document.add(new Paragraph("Tổng tiền: " + order.getTotalAmount()));
            document.add(new Paragraph("Trạng thái: " + order.getStatus()));

            if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
                document.add(new Paragraph("Không có sản phẩm trong đơn hàng."));
            } else {
                document.add(new Paragraph("\n--- Chi tiết sản phẩm ---"));
                PdfPTable table = new PdfPTable(4);
                table.addCell(createCell("Mã sản phẩm"));
                table.addCell(createCell("Giá"));
                table.addCell(createCell("Số lượng"));
                table.addCell(createCell("Thành tiền"));
                for (var item : order.getOrderItems()) {
                    table.addCell(createCell(String.valueOf(item.getProductId())));
                    table.addCell(createCell(item.getPrice().toString()));
                    table.addCell(createCell(String.valueOf(item.getQuantity())));
                    table.addCell(createCell(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).toString()));
                }
                document.add(table);
                document.add(new Paragraph("\nTổng tiền đơn hàng: " + order.getTotalAmount()));
            }

            document.close();

            byte[] pdfBytes = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "order_" + id + ".pdf");
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (DocumentException e) {
            logger.error("Error generating PDF for order ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private PdfPCell createCell(String content) {
        return new PdfPCell(new Paragraph(content));
    }

    @GetMapping
    public ResponseEntity<List<OrdersDTO>> getAllOrders() {
        logger.info("Fetching all orders");
        List<OrdersDTO> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdersDTO> getOrderById(@PathVariable Integer id) {
        if (id == null) {
            logger.error("Invalid ID: null");
            return ResponseEntity.badRequest().body(null);
        }
        OrdersDTO order = ordersService.getOrderById(id);
        if (order == null) {
            throw new OrderNotFoundException("Không tìm thấy đơn hàng với ID: " + id);
        }
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<OrdersDTO> createOrder(@Valid @RequestBody OrdersDTO orderDTO) {
        logger.info("Creating new order");
        OrdersDTO createdOrder = ordersService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdersDTO> updateOrder(@PathVariable Integer id, @Valid @RequestBody OrdersDTO orderDTO) {
        logger.info("Updating order with ID: {}", id);
        if (id == null) {
            logger.error("Invalid ID: null");
            return ResponseEntity.badRequest().body(null);
        }
        OrdersDTO updatedOrder = ordersService.updateOrder(id, orderDTO);
        if (updatedOrder == null) {
            throw new OrderNotFoundException("Không tìm thấy đơn hàng với ID: " + id);
        }
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Integer id) {
        logger.info("Deleting order with ID: {}", id);
        if (id == null) {
            logger.error("Invalid ID: null");
            return ResponseEntity.badRequest().body("ID không hợp lệ");
        }
        ordersService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully");
    }
}