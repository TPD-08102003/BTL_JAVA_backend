package org.example.btl_java.controller;

import org.example.btl_java.dto.EmployeesDTO;
import org.example.btl_java.model.Accounts;

import org.example.btl_java.service.AccountsService;

import org.example.btl_java.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Employees")
public class EmployeesController {

    @Autowired
    private EmployeesService employeeService;

    @Autowired
    private AccountsService accountService;

    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody EmployeesDTO employeeDTO) {
        try {
            // Tạo tài khoản trước
            Accounts account = new Accounts();
            account.setAccountName(employeeDTO.getFirstname() + " " + employeeDTO.getLastname());
            account.setEmail(employeeDTO.getEmail());
            account.setPassword("defaultPassword123"); // Nên mã hóa mật khẩu
            account.setRole("employee");
            Accounts savedAccount = accountService.createAccount(account);

            // Chuyển đổi EmployeeDTO thành EmployeesDTO
            EmployeesDTO employeesDTO = new EmployeesDTO();
            employeesDTO.setFirstname(employeeDTO.getFirstname());
            employeesDTO.setLastname(employeeDTO.getLastname());
            employeesDTO.setEmail(employeeDTO.getEmail());
            employeesDTO.setPhone(employeeDTO.getPhone());
            employeesDTO.setAddress(employeeDTO.getAddress());
            employeesDTO.setAccountId(savedAccount.getAccountId()); // Gán accountId từ tài khoản vừa tạo

            // Gọi EmployeeService với EmployeesDTO
            EmployeesDTO savedEmployeeDTO = employeeService.createEmployee(employeesDTO);
            return ResponseEntity.ok(savedEmployeeDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Integer id, @RequestBody EmployeesDTO employeeDTO) {
        try {
            // Chuyển đổi EmployeeDTO thành EmployeesDTO
            EmployeesDTO employeesDTO = new EmployeesDTO();
            employeesDTO.setFirstname(employeeDTO.getFirstname());
            employeesDTO.setLastname(employeeDTO.getLastname());
            employeesDTO.setEmail(employeeDTO.getEmail());
            employeesDTO.setPhone(employeeDTO.getPhone());
            employeesDTO.setAddress(employeeDTO.getAddress());

            EmployeesDTO updatedEmployeeDTO = employeeService.updateEmployee(id, employeeDTO);
            return ResponseEntity.ok(updatedEmployeeDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("Xoá nhân viên thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }
}