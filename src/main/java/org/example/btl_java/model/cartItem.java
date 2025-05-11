package org.example.btl_java.model;

import jakarta.persistence.*;

@Entity
public class cartItem {


        @Id
        @GeneratedValue
        private Long id;

        @ManyToOne
        @JoinColumn(name = "account_id")
        private Accounts account;

        @ManyToOne
        @JoinColumn(name = "product_id")
        private Products product;

        private int quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Accounts getAccount() {
        return account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
