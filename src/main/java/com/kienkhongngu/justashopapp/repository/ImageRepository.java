package com.kienkhongngu.justashopapp.repository;

import com.kienkhongngu.justashopapp.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);
}
