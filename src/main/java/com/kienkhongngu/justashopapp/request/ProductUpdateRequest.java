package com.kienkhongngu.justashopapp.request;

import com.kienkhongngu.justashopapp.entity.Category;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class ProductUpdateRequest {
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int quantity;
    private Category category;
}
