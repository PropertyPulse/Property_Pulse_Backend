package com.example.demo.repository;

import com.example.demo.entity.TaskRequest;
import com.example.demo.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRequestRepository extends JpaRepository<TaskRequest, Integer> {
    // You can add custom query methods here if needed

    List<TaskRequest> findAllByStatus(TaskStatus status);
}
