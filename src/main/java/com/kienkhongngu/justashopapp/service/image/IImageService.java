package com.kienkhongngu.justashopapp.service.image;

import com.kienkhongngu.justashopapp.dto.ImageDto;
import com.kienkhongngu.justashopapp.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
