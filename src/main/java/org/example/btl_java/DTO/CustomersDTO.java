package org.example.btl_java.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CustomersDTO {
    private Integer customerId;
    private Integer accountId; // Có thể null, nếu null thì tạo mới Accounts

    @NotBlank(message = "Tên không được để trống")
    private String firstname;

    @NotBlank(message = "Họ không được để trống")
    private String lastname;

    @Pattern(regexp = "\\d{10,11}", message = "Số điện thoại không hợp lệ (phải có 10-11 chữ số)")
    private String phone;

    @Email(message = "Email không hợp lệ")

    private String email;

    private String address;

    // Thông tin tài khoản (dùng để tạo mới nếu accountId là null)
    @NotBlank(message = "Tên tài khoản không được để trống", groups = CreateAccountValidation.class)
    private String accountName;

    @NotBlank(message = "Mật khẩu không được để trống", groups = CreateAccountValidation.class)
    private String password;

    @Pattern(regexp = "admin|customer|employee", message = "Vai trò phải là 'admin', 'customer', hoặc 'employee'", groups = CreateAccountValidation.class)
    private String role;

    // Constructor


    public CustomersDTO(Integer customerId, Integer accountId, String firstname, String lastname, String phone, String email, String address, String accountName, String password, String role) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.accountName = accountName;
        this.password = password;
        this.role = role;
    }

    // Getters và Setters
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public interface CreateAccountValidation {}
}