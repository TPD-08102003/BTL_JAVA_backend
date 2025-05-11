package org.example.btl_java.service;

import org.example.btl_java.DTO.AccountsDTO;
import org.example.btl_java.DTO.CustomersDTO;
import org.example.btl_java.model.Accounts;
import org.example.btl_java.model.Customers;
import org.example.btl_java.repository.AccountsRepository;
import org.example.btl_java.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomersService {
    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    public List<CustomersDTO> getAllCustomers() {
        return customersRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CustomersDTO getCustomerById(Integer id) {
        Customers customer = customersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return toDTO(customer);
    }

    public CustomersDTO createCustomer(CustomersDTO customerDTO) {
        validateCustomerDTO(customerDTO);

        Accounts account;
        if (customerDTO.getAccountId() != null) {
            account = accountsRepository.findById(customerDTO.getAccountId())
                    .orElseThrow(() -> new RuntimeException("Account not found"));
        } else {
            validateAccountInfo(customerDTO);

            if (accountsRepository.findByEmail(customerDTO.getEmail()).isPresent()) {
                throw new RuntimeException("Email đã tồn tại");
            }

            account = new Accounts();
            account.setAccountName(customerDTO.getAccountName());
            account.setPassword(customerDTO.getPassword());
            account.setEmail(customerDTO.getEmail());
            account.setRole(customerDTO.getRole() != null ? customerDTO.getRole() : "customer");
            account = accountsRepository.save(account);
        }

        Customers customer = toEntity(customerDTO);
        customer.setAccount(account);
        Customers savedCustomer = customersRepository.save(customer);
        return toDTO(savedCustomer);
    }

    public CustomersDTO updateCustomer(Integer id, CustomersDTO customerDTO) {
        validateCustomerDTO(customerDTO);
        Customers customer = customersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setFirstname(customerDTO.getFirstname());
        customer.setLastname(customerDTO.getLastname());
        customer.setPhone(customerDTO.getPhone());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());

        if (customerDTO.getAccountId() != null) {
            Accounts account = accountsRepository.findById(customerDTO.getAccountId())
                    .orElseThrow(() -> new RuntimeException("Account not found"));
            customer.setAccount(account);
        }

        Customers updatedCustomer = customersRepository.save(customer);
        return toDTO(updatedCustomer);
    }

    public void deleteCustomer(Integer id) {
        Customers customer = customersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customersRepository.delete(customer);
    }

    private CustomersDTO toDTO(Customers customer) {
        return new CustomersDTO(
                customer.getCustomerId(),
                customer.getAccount() != null ? customer.getAccount().getAccountId() : null,
                customer.getFirstname(),
                customer.getLastname(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getAccount() != null ? customer.getAccount().getAccountName() : null,
                null, // Không trả về password vì lý do bảo mật
                customer.getAccount() != null ? customer.getAccount().getRole() : null
        );
    }

    private Customers toEntity(CustomersDTO customerDTO) {
        Customers customer = new Customers();
        customer.setFirstname(customerDTO.getFirstname());
        customer.setLastname(customerDTO.getLastname());
        customer.setPhone(customerDTO.getPhone());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());
        return customer;
    }

    private void validateCustomerDTO(CustomersDTO customerDTO) {
        if (customerDTO.getFirstname() == null || customerDTO.getFirstname().trim().isEmpty()) {
            throw new RuntimeException("Tên không được để trống");
        }
        if (customerDTO.getLastname() == null || customerDTO.getLastname().trim().isEmpty()) {
            throw new RuntimeException("Họ không được để trống");
        }
        if (customerDTO.getEmail() == null || !customerDTO.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new RuntimeException("Email không hợp lệ");
        }
        if (customerDTO.getPhone() != null && !customerDTO.getPhone().matches("\\d{10,11}")) {
            throw new RuntimeException("Số điện thoại không hợp lệ (phải có 10-11 chữ số)");
        }
    }

    private void validateAccountInfo(CustomersDTO customerDTO) {
        if (customerDTO.getAccountName() == null || customerDTO.getAccountName().trim().isEmpty()) {
            throw new RuntimeException("Tên tài khoản không được để trống");
        }
        if (customerDTO.getPassword() == null || customerDTO.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Mật khẩu không được để trống");
        }
        if (customerDTO.getRole() != null && !customerDTO.getRole().matches("admin|customer|employee")) {
            throw new RuntimeException("Vai trò phải là 'admin', 'customer', hoặc 'employee'");
        }
    }

    public CustomersDTO getCustomerProfileByEmail(String email) {
        Customers customer = customersRepository.findByAccount_Email(email);
        if (customer == null) {
            throw new RuntimeException("Customer profile not found for email: " + email);
        }
        return toDTO(customer);
    }

    public CustomersDTO updateCustomerProfile(String email, CustomersDTO customersDTO) {
        Customers customer = customersRepository.findByAccount_Email(email);
        if (customer == null) {
            throw new RuntimeException("Customer profile not found for email: " + email);
        }

        // Validate DTO trước khi cập nhật
        validateCustomerDTOForProfileUpdate(customersDTO);

        // Cập nhật các trường, không thay đổi email vì người dùng không được phép chỉnh sửa email qua giao diện này
        customer.setFirstname(customersDTO.getFirstname());
        customer.setLastname(customersDTO.getLastname());
        customer.setPhone(customersDTO.getPhone());
        customer.setAddress(customersDTO.getAddress());

        // Lưu và trả về DTO
        Customers updatedCustomer = customersRepository.save(customer);
        return toDTO(updatedCustomer);
    }

    // Validation riêng cho updateCustomerProfile để bỏ qua kiểm tra email
    private void validateCustomerDTOForProfileUpdate(CustomersDTO customerDTO) {
        if (customerDTO.getFirstname() == null || customerDTO.getFirstname().trim().isEmpty()) {
            throw new RuntimeException("Tên không được để trống");
        }
        if (customerDTO.getLastname() == null || customerDTO.getLastname().trim().isEmpty()) {
            throw new RuntimeException("Họ không được để trống");
        }
        if (customerDTO.getPhone() != null && !customerDTO.getPhone().matches("\\d{10,11}")) {
            throw new RuntimeException("Số điện thoại không hợp lệ (phải có 10-11 chữ số)");
        }
        // Không kiểm tra email vì email được lấy từ token và không cho phép thay đổi
    }
}