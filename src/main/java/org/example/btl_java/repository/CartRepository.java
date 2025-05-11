package org.example.btl_java.repository;

import org.example.btl_java.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByAccountId(Integer accountId);
    List<Cart> findByAccountIdAndProductId(Integer accountId, Integer productId);
    void deleteByAccountId(Integer accountId);
}