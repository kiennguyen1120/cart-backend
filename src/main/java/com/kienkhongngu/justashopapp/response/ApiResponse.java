package com.kienkhongngu.justashopapp.response;


import com.kienkhongngu.justashopapp.dto.ImageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiResponse {
    private String message;
    private Object data;

}
