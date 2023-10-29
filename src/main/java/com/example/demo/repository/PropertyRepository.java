package com.example.demo.repository;

import com.example.demo.entity.Property;
import com.example.demo.entity.PropertyOwner;
import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    List<Object> findAllById(Long propertyId);

    List<Property> findByAcceptedStatus(boolean b);



//    List<Property> findByAccepted_Status(boolean b);
}
