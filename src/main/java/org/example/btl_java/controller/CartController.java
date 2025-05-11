package org.example.btl_java.controller;

import org.example.btl_java.model.Cart;
import org.example.btl_java.model.Products;
import org.example.btl_java.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @PostMapping
    public ResponseEntity<?> addToCart(@RequestBody Cart cart) {
        try {
            // Kiểm tra số lượng hợp lệ
            if (cart.getQuantity() <= 0) {
                return ResponseEntity.badRequest().body("Số lượng phải lớn hơn 0");
            }

            // Kiểm tra tồn tại sản phẩm
            Products product = cartService.getProductById(cart.getProductId());
            if (product == null) {
                return ResponseEntity.notFound().build();
            }

            // Kiểm tra số lượng tồn kho
            if (product.getQuantity() < cart.getQuantity()) {
                return ResponseEntity.badRequest().body("Số lượng vượt quá tồn kho");
            }

            // Xử lý thêm vào giỏ hàng
            Cart result = cartService.addToCart(cart);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<List<Cart>> getCartByAccountId(@PathVariable Integer accountId) { // Sử dụng Integer
        List<Cart> cartItems = cartService.getCartByAccountId(accountId);
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long cartId) {
        cartService.removeFromCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<Cart> updateCartItem(@PathVariable Long cartId, @RequestBody Cart cart) {
        Cart updatedCart = cartService.updateCartItem(cartId, cart.getQuantity());
        return ResponseEntity.ok(updatedCart);
    }
}