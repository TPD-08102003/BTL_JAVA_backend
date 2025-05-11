package org.example.btl_java.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Suppliers")
public class Suppliers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplierId;

    @Column(nullable = false, length = 200)
    private String supplierName;

    @Column(length = 200)
    private String address;

    @Column(unique = true, length = 20)
    private String phone;

    @Column(unique = true, length = 50)
    private String email;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private Set<Products> products;

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Set<Products> getProducts() {
        return products;
    }

    public void setProducts(Set<Products> products) {
        this.products = products;
    }
}
