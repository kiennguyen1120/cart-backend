package com.kienkhongngu.justashopapp.service.cart;

import com.kienkhongngu.justashopapp.entity.Cart;
import com.kienkhongngu.justashopapp.exception.ResourceNotFoundException;
import com.kienkhongngu.justashopapp.repository.CartItemRepository;
import com.kienkhongngu.justashopapp.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;


@Service
@RequiredArgsConstructor
public class CartService implements ICartService{

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long id) {
       return  cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found by id: " + id));


    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        cartItemRepository.deleteAllByCartId(id);
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        return getCart(id).getTotalAmount();
    }

    @Override
    public Long initializeNewCart() {
        Cart newCart = new Cart();
        return cartRepository.save(newCart).getId();
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
