package com.example.demo.repository;


import com.example.demo.entity.ValuationReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface ValuationReportRepository extends JpaRepository<ValuationReport,Long> {
    Optional<ValuationReport> findByproperty_Id(Long propertyId);

    List<ValuationReport> findByStatus(String status);
}


