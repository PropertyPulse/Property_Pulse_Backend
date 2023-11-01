package com.example.demo.repository;

<<<<<<< HEAD
import com.example.demo.entity.ValuationReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ValuationReportRepository extends JpaRepository<ValuationReport, Long> {


    List<ValuationReport> findByStatus(String status);
}
=======
import com.example.demo.entity.FileData;
import com.example.demo.entity.ValuationReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValuationReportRepository extends JpaRepository<ValuationReport,Integer> {
    Optional<ValuationReport> findByName(String fileName);
}
>>>>>>> 08e059e0b3488495ea4b4fd00fd857982977c05b
