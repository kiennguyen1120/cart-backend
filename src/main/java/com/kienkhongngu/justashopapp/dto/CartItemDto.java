package com.kienkhongngu.justashopapp.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDto product;
}
