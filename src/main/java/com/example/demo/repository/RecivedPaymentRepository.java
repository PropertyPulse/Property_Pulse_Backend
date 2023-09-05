package com.example.demo.repository;

import com.example.demo.entity.RecivedPayment;
import com.example.demo.entity.RecivedPaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecivedPaymentRepository extends JpaRepository<RecivedPayment, Integer> {

    List<RecivedPayment> findByType(RecivedPaymentType recivedPaymentType);
}
