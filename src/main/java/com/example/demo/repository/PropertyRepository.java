package com.example.demo.repository;

import com.example.demo.entity.Property;
import com.example.demo.entity.PropertyOwner;
import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
}
