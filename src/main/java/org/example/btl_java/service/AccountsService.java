package org.example.btl_java.service;

import org.example.btl_java.DTO.AccountsDTO;
import org.example.btl_java.model.Accounts;
import org.example.btl_java.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountsService {
    @Autowired
    private AccountsRepository accountsRepository;

    public Accounts createAccount(Accounts account) {
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dữ liệu tài khoản không hợp lệ");
        }
        if (accountsRepository.existsByEmail(account.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email đã tồn tại");
        }
        return accountsRepository.save(account);
    }

    public List<AccountsDTO> getAllAccounts() {
        List<Accounts> accounts = accountsRepository.findAll();
        if (accounts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không có tài khoản nào được tìm thấy");
        }
        return accounts.stream()
                .map(this::toDTOWithPassword)
                .collect(Collectors.toList());
    }

    public AccountsDTO getAccountById(Integer id) {
        if (id == null || id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID không hợp lệ");
        }
        Accounts account = accountsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tài khoản không được tìm thấy với ID: " + id));
        return toDTOWithPassword(account);
    }

    public AccountsDTO updateAccount(Integer id, AccountsDTO accountDTO) {
        if (id == null || id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID không hợp lệ");
        }
        if (accountDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dữ liệu tài khoản không hợp lệ");
        }

        Accounts existingAccount = accountsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tài khoản không được tìm thấy với ID: " + id));

        // Kiểm tra email nếu thay đổi
        if (!existingAccount.getEmail().equals(accountDTO.getEmail()) &&
                accountsRepository.existsByEmail(accountDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email đã tồn tại");
        }

        // Cập nhật thông tin
        existingAccount.setAccountName(accountDTO.getAccountName());
        existingAccount.setEmail(accountDTO.getEmail());
        if (accountDTO.getPassword() != null && !accountDTO.getPassword().isEmpty()) {
            existingAccount.setPassword(accountDTO.getPassword());
        }
        existingAccount.setRole(accountDTO.getRole());

        Accounts updatedAccount = accountsRepository.save(existingAccount);
        return toDTOWithPassword(updatedAccount);
    }

    public void deleteAccount(Integer id) {
        if (id == null || id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID không hợp lệ");
        }
        if (!accountsRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tài khoản không được tìm thấy với ID: " + id);
        }
        accountsRepository.deleteById(id);
    }
    private AccountsDTO toDTOWithPassword(Accounts account) {
        return new AccountsDTO(
                account.getAccountId(),
                account.getAccountName(),
                account.getPassword(), // giữ lại password
                account.getEmail(),
                account.getRole(),
                account.getCreatedAt()
        );
    }

    public AccountsDTO getAccountByEmail(String email) {
        Accounts account = accountsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return toDTOWithPassword(account);
    }

}