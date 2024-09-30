package com.kienkhongngu.justashopapp.dto;

import com.kienkhongngu.justashopapp.entity.Category;
import lombok.Data;


import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int quantity;
    private Category category;

    private List<ImageDto> images;

}
