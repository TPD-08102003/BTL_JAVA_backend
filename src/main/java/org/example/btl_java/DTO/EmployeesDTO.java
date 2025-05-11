package org.example.btl_java.dto; // Sửa DTO thành dto (theo chuẩn Java)

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class EmployeesDTO {
    private Integer employeeId;
    private Integer accountId;

    @NotBlank(message = "Tên không được để trống")
    private String firstname;

    @NotBlank(message = "Họ không được để trống")
    private String lastname;

    @Pattern(regexp = "\\d{10,11}", message = "Số điện thoại không hợp lệ (phải có 10-11 chữ số)")
    private String phone;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    private String email;

    private String address;

    private String position; // Thêm trường position để khớp với getter và setter

    // Constructor
    public EmployeesDTO() {}

    public EmployeesDTO(Integer employeeId, Integer accountId, String firstname, String lastname, String phone, String email, String address, String position) {
        this.employeeId = employeeId;
        this.accountId = accountId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.position = position;
    }

    // Getters và Setters
    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}