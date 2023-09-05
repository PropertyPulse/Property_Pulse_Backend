package com.example.demo.repository;

import com.example.demo.entity.TaskEquipmentPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskEquipmentPaymentRepository extends JpaRepository<TaskEquipmentPayment, Integer> {

    List<TaskEquipmentPayment> findByIsPaidFalse();
}
