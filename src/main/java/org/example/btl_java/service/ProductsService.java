package org.example.btl_java.service;

import org.example.btl_java.DTO.ProductsDTO;
import org.example.btl_java.model.Categories;
import org.example.btl_java.model.Products;
import org.example.btl_java.model.Suppliers;
import org.example.btl_java.repository.CategoriesRepository;
import org.example.btl_java.repository.ProductsRepository;
import org.example.btl_java.repository.SuppliersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private SuppliersRepository suppliersRepository;

    // Tạo sản phẩm
    public ProductsDTO createProduct(ProductsDTO productDTO) {
        validateProductDTO(productDTO);

        Products product = toEntity(productDTO);
        Products savedProduct = productsRepository.save(product);
        return toDTO(savedProduct);
    }

    // Cập nhật sản phẩm
    public ProductsDTO updateProduct(Integer id, ProductsDTO productDTO) {
        validateProductDTO(productDTO);

        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());

        Categories category = categoriesRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại"));
        product.setCategory(category);

        Suppliers supplier = suppliersRepository.findById(productDTO.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Nhà cung cấp không tồn tại"));
        product.setSupplier(supplier);

        Products updatedProduct = productsRepository.save(product);
        return toDTO(updatedProduct);
    }

    // Xóa sản phẩm
    public void deleteProduct(Integer id) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
        productsRepository.delete(product);
    }

    // Lấy sản phẩm theo ID
    public ProductsDTO getProductById(Integer id) {
        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
        return toDTO(product);
    }

    // Lấy tất cả sản phẩm
    public List<ProductsDTO> getAllProducts() {
        return productsRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Ánh xạ Products -> ProductsDTO
    private ProductsDTO toDTO(Products product) {
        return new ProductsDTO(
                product.getProductId(),
                product.getProductName(),
                product.getPrice(), // price giờ là BigDecimal
                product.getCategory() != null ? product.getCategory().getCategoryId() : null,
                product.getSupplier() != null ? product.getSupplier().getSupplierId() : null,
                product.getDescription(),
                product.getQuantity()
        );
    }

    // Ánh xạ ProductsDTO -> Products
    private Products toEntity(ProductsDTO productDTO) {
        Products product = new Products();
        product.setProductName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice()); // price giờ là BigDecimal
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());

        Categories category = categoriesRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại"));
        product.setCategory(category);

        Suppliers supplier = suppliersRepository.findById(productDTO.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Nhà cung cấp không tồn tại"));
        product.setSupplier(supplier);

        return product;
    }

    // Validation cơ bản
    private void validateProductDTO(ProductsDTO productDTO) {
        if (productDTO.getProductName() == null || productDTO.getProductName().trim().isEmpty()) {
            throw new RuntimeException("Tên sản phẩm không được để trống");
        }
        if (productDTO.getPrice() == null || productDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Giá sản phẩm phải lớn hơn 0");
        }
        if (productDTO.getQuantity() < 0) {
            throw new RuntimeException("Số lượng sản phẩm không được âm");
        }
        if (productDTO.getCategoryId() == null) {
            throw new RuntimeException("Danh mục không được để trống");
        }
        if (productDTO.getSupplierId() == null) {
            throw new RuntimeException("Nhà cung cấp không được để trống");
        }
    }
}