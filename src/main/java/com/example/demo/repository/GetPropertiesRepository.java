package com.example.demo.repository;

import com.example.demo.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GetPropertiesRepository extends JpaRepository<Property, Integer> {
}
