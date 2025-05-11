package org.example.btl_java.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SuppliersDTO {
    private Integer supplierId;

    @NotBlank(message = "Tên nhà cung cấp không được để trống")
    private String supplierName;

    private String address;

    @Pattern(regexp = "\\d{10,11}", message = "Số điện thoại không hợp lệ (phải có 10-11 chữ số)")
    private String phone;

    @Email(message = "Email không hợp lệ")
    private String email;

    // Constructor mặc định
    public SuppliersDTO() {}

    // Constructor đầy đủ
    public SuppliersDTO(Integer supplierId, String supplierName, String address, String phone, String email) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    // Getters và Setters
    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}