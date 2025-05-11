package org.example.btl_java.service;

import org.example.btl_java.DTO.SuppliersDTO;
import org.example.btl_java.model.Suppliers;
import org.example.btl_java.repository.SuppliersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuppliersService {
    @Autowired
    private SuppliersRepository suppliersRepository;

    public List<SuppliersDTO> getAllSuppliers() {
        return suppliersRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public SuppliersDTO getSupplierById(Integer id) {
        Suppliers supplier = suppliersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        return toDTO(supplier);
    }

    public SuppliersDTO createSupplier(SuppliersDTO supplierDTO) {
        validateSupplierDTO(supplierDTO);
        Suppliers supplier = toEntity(supplierDTO);
        Suppliers savedSupplier = suppliersRepository.save(supplier);
        return toDTO(savedSupplier);
    }

    public SuppliersDTO updateSupplier(Integer id, SuppliersDTO supplierDTO) {
        validateSupplierDTO(supplierDTO);
        Suppliers supplier = suppliersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        supplier.setSupplierName(supplierDTO.getSupplierName());
        supplier.setAddress(supplierDTO.getAddress());
        supplier.setPhone(supplierDTO.getPhone());
        supplier.setEmail(supplierDTO.getEmail());

        Suppliers updatedSupplier = suppliersRepository.save(supplier);
        return toDTO(updatedSupplier);
    }

    public void deleteSupplier(Integer id) {
        Suppliers supplier = suppliersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        if (supplier.getProducts() != null && !supplier.getProducts().isEmpty()) {
            throw new RuntimeException("Cannot delete supplier with products.");
        }
        suppliersRepository.delete(supplier);
    }

    // Ánh xạ Suppliers -> SuppliersDTO
    private SuppliersDTO toDTO(Suppliers supplier) {
        return new SuppliersDTO(
                supplier.getSupplierId(),
                supplier.getSupplierName(),
                supplier.getAddress(),
                supplier.getPhone(),
                supplier.getEmail()
        );
    }

    // Ánh xạ SuppliersDTO -> Suppliers
    private Suppliers toEntity(SuppliersDTO supplierDTO) {
        Suppliers supplier = new Suppliers();
        supplier.setSupplierName(supplierDTO.getSupplierName());
        supplier.setAddress(supplierDTO.getAddress());
        supplier.setPhone(supplierDTO.getPhone());
        supplier.setEmail(supplierDTO.getEmail());
        return supplier;
    }

    // Validation cơ bản
    private void validateSupplierDTO(SuppliersDTO supplierDTO) {
        if (supplierDTO.getSupplierName() == null || supplierDTO.getSupplierName().trim().isEmpty()) {
            throw new RuntimeException("Tên nhà cung cấp không được để trống");
        }
        if (supplierDTO.getEmail() != null && !supplierDTO.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new RuntimeException("Email không hợp lệ");
        }
        if (supplierDTO.getPhone() != null && !supplierDTO.getPhone().matches("\\d{10,11}")) {
            throw new RuntimeException("Số điện thoại không hợp lệ (phải có 10-11 chữ số)");
        }
    }
}