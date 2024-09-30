package com.kienkhongngu.justashopapp.repository;

import com.kienkhongngu.justashopapp.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
