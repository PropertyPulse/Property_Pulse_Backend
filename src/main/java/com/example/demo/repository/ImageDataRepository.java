package com.example.demo.repository;

import com.example.demo.entity.FileData;
import com.example.demo.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageDataRepository extends JpaRepository<ImageData,Integer> {
    Optional<ImageData> findByName(String fileName);
}