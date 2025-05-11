package org.example.btl_java.service;

import org.example.btl_java.model.Cart;
import org.example.btl_java.model.Products;
import org.example.btl_java.repository.CartRepository;
import org.example.btl_java.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductsRepository productRepository;

    public Cart addToCart(Cart cart) {
        // Kiểm tra sản phẩm đã có trong giỏ chưa
        List<Cart> existingItems = cartRepository.findByAccountIdAndProductId(
                cart.getAccountId(),
                cart.getProductId()
        );

        if (!existingItems.isEmpty()) {
            // Cập nhật số lượng nếu đã có
            Cart existingItem = existingItems.get(0);
            existingItem.setQuantity(existingItem.getQuantity() + cart.getQuantity());
            existingItem.setAddedAt(LocalDateTime.now());
            return cartRepository.save(existingItem);
        } else {
            // Thêm mới nếu chưa có
            cart.setAddedAt(LocalDateTime.now());
            return cartRepository.save(cart);
        }
    }

    public List<Cart> getCartByAccountId(Integer accountId) {
        return cartRepository.findByAccountId(accountId);
    }

    public void removeFromCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    public Cart updateCartItem(Long cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    public List<Cart> getCartByAccountIdAndProductId(Integer accountId, Integer productId) {
        return cartRepository.findByAccountIdAndProductId(accountId, productId);
    }

    public Products getProductById(Integer productId) {
        return productRepository.findById(productId).orElse(null);
    }
}