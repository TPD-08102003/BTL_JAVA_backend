package org.example.btl_java.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.btl_java.DTO.AccountsDTO;
import org.example.btl_java.DTO.CustomersDTO;
import org.example.btl_java.model.Accounts;
import org.example.btl_java.model.LoginRequest;
import org.example.btl_java.service.AccountsService;
import org.example.btl_java.service.CustomersService;
import org.example.btl_java.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {
    private final AccountsService accountsService;
    private final CustomersService customersService;
    private final EmployeesService employeesService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    public AccountsController(AccountsService accountsService, CustomersService customersService, EmployeesService employeesService) {
        this.accountsService = accountsService;
        this.customersService = customersService;
        this.employeesService = employeesService;
        System.out.println("JWT Secret: " + jwtSecret); // Debug
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Map<String, String> response = new HashMap<>();
        try {
            if (loginRequest.getEmail() == null || loginRequest.getPassword() == null ||
                    loginRequest.getEmail().trim().isEmpty() || loginRequest.getPassword().trim().isEmpty()) {
                response.put("error", "Email và mật khẩu không được để trống");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            AccountsDTO account = accountsService.getAccountByEmail(loginRequest.getEmail());
            if (account == null) {
                response.put("error", "Email '" + loginRequest.getEmail() + "' không tồn tại");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            if (!loginRequest.getPassword().equals(account.getPassword())) {
                response.put("error", "Mật khẩu không đúng");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            String token = JwtResponse.generateToken(account.getEmail(), account.getRole(), account.getAccountName(), jwtSecret);
            System.out.println("Generated token: " + token); // Debug
            Map<String, String> responseData = new HashMap<>();
            responseData.put("token", token);
            responseData.put("role", account.getRole());
            responseData.put("accountName", account.getAccountName());

            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            response.put("error", "Đã xảy ra lỗi: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            System.out.println("Authorization header: " + authHeader); // Debug
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Thiếu hoặc sai định dạng header Authorization");
            }

            String token = authHeader.replace("Bearer ", "").trim();
            System.out.println("Token nhận được: " + token); // Debug

            if (token.split("\\.").length != 3) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token có định dạng không hợp lệ");
            }

            String email = JwtResponse.getEmailFromToken(token, jwtSecret);
            System.out.println("Email từ token: " + email); // Debug
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không chứa email hợp lệ hoặc đã hết hạn");
            }

            try {
                CustomersDTO profile = customersService.getCustomerProfileByEmail(email);
                System.out.println("Profile fetched: " + profile); // Debug
                return ResponseEntity.ok(profile);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy hồ sơ khách hàng cho email: " + email);
            }
        } catch (Exception e) {
            System.out.println("Lỗi server: " + e.getMessage()); // Debug
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi server: " + e.getMessage());
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(HttpServletRequest request, @Valid @RequestBody CustomersDTO customersDTO) {
        try {
            String authHeader = request.getHeader("Authorization");
            System.out.println("Authorization header: " + authHeader); // Debug
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Thiếu hoặc sai định dạng header Authorization");
            }

            String token = authHeader.replace("Bearer ", "").trim();
            System.out.println("Token nhận được: " + token); // Debug

            if (token.split("\\.").length != 3) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token có định dạng không hợp lệ");
            }

            String email = JwtResponse.getEmailFromToken(token, jwtSecret);
            System.out.println("Email từ token: " + email); // Debug
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không chứa email hợp lệ hoặc đã hết hạn");
            }

            try {
                CustomersDTO updatedProfile = customersService.updateCustomerProfile(email, customersDTO);
                System.out.println("Updated profile: " + updatedProfile); // Debug
                return ResponseEntity.ok(updatedProfile);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy hồ sơ khách hàng để cập nhật: " + e.getMessage());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi server khi cập nhật hồ sơ: " + e.getMessage());
        }
    }

    // Xử lý lỗi validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
        try {
            CustomersDTO customer = customersService.getCustomerById(id);
            return ResponseEntity.ok(customer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy khách hàng với ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi lấy thông tin khách hàng: " + e.getMessage());
        }
    }

    @GetMapping("/customers/email/{email}")
    public ResponseEntity<?> getCustomerByEmail(@PathVariable String email) {
        try {
            CustomersDTO customer = customersService.getCustomerProfileByEmail(email);
            return ResponseEntity.ok(customer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy khách hàng với email: " + email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi lấy thông tin khách hàng: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<AccountsDTO>> getAllAccounts() {
        List<AccountsDTO> accounts = accountsService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountsDTO> getAccountById(@PathVariable Integer id) {
        try {
            AccountsDTO account = accountsService.getAccountById(id);
            return ResponseEntity.ok(account);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<AccountsDTO> createAccount(@Valid @RequestBody AccountsDTO accountDTO) {
        try {
            if (accountDTO.getEmail() == null || accountDTO.getPassword() == null || accountDTO.getRole() == null) {
                throw new RuntimeException("Email, mật khẩu và vai trò không được để trống");
            }
            Accounts account = convertToEntity(accountDTO);
            Accounts createdAccount = accountsService.createAccount(account);
            AccountsDTO createdAccountDTO = convertToDTO(createdAccount);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAccountDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    private AccountsDTO convertToDTO(Accounts account) {
        if (account == null) {
            return null;
        }
        AccountsDTO dto = new AccountsDTO();
        dto.setAccountId(account.getAccountId());
        dto.setAccountName(account.getAccountName());
        dto.setEmail(account.getEmail());
        dto.setPassword(account.getPassword());
        dto.setRole(account.getRole());
        return dto;
    }

    private Accounts convertToEntity(@Valid AccountsDTO accountDTO) {
        if (accountDTO == null) {
            return null;
        }
        Accounts account = new Accounts();
        account.setAccountId(accountDTO.getAccountId());
        account.setAccountName(accountDTO.getAccountName());
        account.setEmail(accountDTO.getEmail());
        account.setPassword(accountDTO.getPassword());
        account.setRole(accountDTO.getRole());
        return account;
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountsDTO> updateAccount(@PathVariable Integer id, @Valid @RequestBody AccountsDTO accountDTO) {
        try {
            AccountsDTO updatedAccount = accountsService.updateAccount(id, accountDTO);
            return ResponseEntity.ok(updatedAccount);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Integer id) {
        try {
            accountsService.deleteAccount(id);
            return ResponseEntity.ok("Account deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}