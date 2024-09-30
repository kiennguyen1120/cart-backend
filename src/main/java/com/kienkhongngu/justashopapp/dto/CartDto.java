package com.kienkhongngu.justashopapp.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDto {
    private Set<CartItemDto> items;
    private BigDecimal totalAmount;
}
