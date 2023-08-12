package com.example.demo.repository;

import com.example.demo.entity.TaskRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRequestRepository extends JpaRepository<TaskRequest, Long> {
    // You can add custom query methods here if needed
}
