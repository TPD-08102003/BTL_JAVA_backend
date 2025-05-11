package org.example.btl_java.service;

import org.example.btl_java.dto.EmployeesDTO;
import org.example.btl_java.model.Accounts;
import org.example.btl_java.model.Employees;
import org.example.btl_java.repository.AccountsRepository;
import org.example.btl_java.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeesService {
    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    public List<EmployeesDTO> getAllEmployees() {
        return employeesRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EmployeesDTO getEmployeeById(Integer id) {
        Employees employee = employeesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return toDTO(employee);
    }

    public EmployeesDTO createEmployee(EmployeesDTO employeeDTO) {
        validateEmployeeDTO(employeeDTO);
        Employees employee = toEntity(employeeDTO);
        Employees savedEmployee = employeesRepository.save(employee);
        return toDTO(savedEmployee);
    }

    public EmployeesDTO updateEmployee(Integer id, EmployeesDTO employeeDTO) {
        validateEmployeeDTO(employeeDTO);
        Employees employee = employeesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setFirstname(employeeDTO.getFirstname());
        employee.setLastname(employeeDTO.getLastname());
        employee.setPhone(employeeDTO.getPhone());
        employee.setEmail(employeeDTO.getEmail());
        employee.setAddress(employeeDTO.getAddress());
        employee.setPosition(employeeDTO.getPosition());

        if (employeeDTO.getAccountId() != null) {
            Accounts account = accountsRepository.findById(employeeDTO.getAccountId())
                    .orElseThrow(() -> new RuntimeException("Account not found"));
            employee.setAccount(account);
        }

        Employees updatedEmployee = employeesRepository.save(employee);
        return toDTO(updatedEmployee);
    }

    public void deleteEmployee(Integer id) {
        Employees employee = employeesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employeesRepository.delete(employee);
    }

    // Ánh xạ Employees -> EmployeesDTO
    private EmployeesDTO toDTO(Employees employee) {
        return new EmployeesDTO(
                employee.getEmployeeId(),
                employee.getAccount() != null ? employee.getAccount().getAccountId() : null,
                employee.getFirstname(),
                employee.getLastname(),
                employee.getPhone(),
                employee.getEmail(),
                employee.getAddress(),
                employee.getPosition() // Thêm position để khớp với constructor
        );
    }

    // Ánh xạ EmployeesDTO -> Employees
    private Employees toEntity(EmployeesDTO employeeDTO) {
        Employees employee = new Employees();
        employee.setFirstname(employeeDTO.getFirstname());
        employee.setLastname(employeeDTO.getLastname());
        employee.setPhone(employeeDTO.getPhone());
        employee.setEmail(employeeDTO.getEmail());
        employee.setAddress(employeeDTO.getAddress());
        employee.setPosition(employeeDTO.getPosition());

        if (employeeDTO.getAccountId() != null) {
            Accounts account = accountsRepository.findById(employeeDTO.getAccountId())
                    .orElseThrow(() -> new RuntimeException("Account not found"));
            employee.setAccount(account);
        }

        return employee;
    }

    // Validation cơ bản
    private void validateEmployeeDTO(EmployeesDTO employeeDTO) {
        if (employeeDTO.getFirstname() == null || employeeDTO.getFirstname().trim().isEmpty()) {
            throw new RuntimeException("Tên không được để trống");
        }
        if (employeeDTO.getLastname() == null || employeeDTO.getLastname().trim().isEmpty()) {
            throw new RuntimeException("Họ không được để trống");
        }
        if (employeeDTO.getEmail() == null || !employeeDTO.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new RuntimeException("Email không hợp lệ");
        }
        if (employeeDTO.getPhone() != null && !employeeDTO.getPhone().matches("\\d{10,11}")) {
            throw new RuntimeException("Số điện thoại không hợp lệ (phải có 10-11 chữ số)");
        }
    }
    @Service
    public class AccountsService {
        @Autowired
        private AccountsRepository accountsRepository;
        public Accounts createAccount(Accounts account) {
            return accountsRepository.save(account);
        }
    }


}