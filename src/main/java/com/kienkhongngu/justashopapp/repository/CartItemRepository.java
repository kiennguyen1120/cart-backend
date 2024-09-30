package com.kienkhongngu.justashopapp.repository;

import com.kienkhongngu.justashopapp.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);
}
