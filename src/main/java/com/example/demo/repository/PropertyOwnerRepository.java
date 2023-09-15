package com.example.demo.repository;

import com.example.demo.entity.PropertyOwner;
import com.example.demo.entity.TaskSupervisor;
import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyOwnerRepository extends JpaRepository<PropertyOwner, Integer> {
    Optional<PropertyOwner> findById(Integer id);

    TaskSupervisor findPropertyOwnerByUser(User user);

}
