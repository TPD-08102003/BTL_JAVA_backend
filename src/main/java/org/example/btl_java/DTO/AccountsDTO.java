package org.example.btl_java.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.OffsetDateTime;

public class AccountsDTO {
    private Integer accountId;

    @NotBlank(message = "Tên tài khoản không được để trống")
    private String accountName;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @Pattern(regexp = "admin|customer|employee", message = "Vai trò phải là 'admin', 'customer', hoặc 'employee'")
    private String role;

    private OffsetDateTime createdAt;

    // Constructor
    public AccountsDTO() {}

    public AccountsDTO(Integer accountId, String accountName, String password, String email, String role, OffsetDateTime createdAt) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
    }

    // Getters và Setters
    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}