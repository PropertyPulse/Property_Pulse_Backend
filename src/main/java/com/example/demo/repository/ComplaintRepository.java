package com.example.demo.repository;

import com.example.demo.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {


  @Query("SELECT c FROM Complaint c LEFT JOIN FETCH c.complainant WHERE c.issolved = false")
  List<Complaint> findAllWithComplainantByIssolvedFalse();




}
