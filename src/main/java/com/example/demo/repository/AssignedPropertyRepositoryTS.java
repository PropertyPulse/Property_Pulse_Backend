package com.example.demo.repository;

import com.example.demo.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignedPropertyRepositoryTS extends JpaRepository <Property, Integer> {
//    List<Property>
}