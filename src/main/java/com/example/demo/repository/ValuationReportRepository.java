package com.example.demo.repository;

import com.example.demo.entity.FileData;
import com.example.demo.entity.ValuationReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValuationReportRepository extends JpaRepository<ValuationReport,Integer> {
    Optional<ValuationReport> findByName(String fileName);
}