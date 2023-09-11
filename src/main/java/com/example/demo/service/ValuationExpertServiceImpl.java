package com.example.demo.service;

import com.example.demo.dto.responseDto.ValuationReportDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.repository.ValuationReportRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service

public class ValuationExpertServiceImpl   implements  ValuationExpertService {


    private String uploadDir = "D:/VEReports";


    private ValuationReportRepository valuationReportRepository;
    private PropertyRepository propertyRepository;


    public ValuationExpertServiceImpl(ValuationReportRepository valuationReportRepository, PropertyRepository propertyRepository) {
        {
            this.valuationReportRepository = valuationReportRepository;
            this.propertyRepository = propertyRepository;
        }


    }

    @Override
    public ValuationReport saveReport(MultipartFile file, Long PropertyId, String RequestedDate) throws Exception {

        Path filePath = Paths.get(uploadDir).resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), filePath);
        Optional<ValuationReport> optionalReport = valuationReportRepository.findByproperty_Id(PropertyId);
        if (optionalReport.isPresent()) {
            ValuationReport valuationReport = optionalReport.get();
            valuationReport.setPdfPath(filePath.toString()); // Set the file path
            valuationReport.setStatus("submitted"); // Update the status
            valuationReport.setSubmittedDate(LocalDate.now()); // Set the submitted date
            return valuationReportRepository.save(valuationReport);
        } else {
            throw new Exception("Valuation report not found for the specified property ID");
        }
    }

    @Override
    public ValuationReport requestReport(Long PropertyId, String RequestedDate) throws Exception {

        ValuationReport valuationReport = new ValuationReport();
        valuationReport.setFileName(null);
        valuationReport.setPdfPath(null);
        valuationReport.setStatus("pending");
        valuationReport.setRequestedDate(LocalDate.now());
        var property = propertyRepository.findById(PropertyId);
        valuationReport.setProperty(property.get());
// submittedDate is nullable, so you can leave it as null
        valuationReport.setSubmittedDate(null);

        return valuationReportRepository.save(valuationReport);


    }


    @Override
    public List<ValuationReportDTO> findByStatus(String status) throws Exception {
        List<ValuationReportDTO> reportDTOs = new ArrayList<>();
        List<ValuationReport> reportList = valuationReportRepository.findByStatus(status);
        if (!reportList.isEmpty()) {
            for (ValuationReport valuationReport : reportList) {
                Property property = valuationReport.getProperty();
                if (property != null) {
                    ValuationReportDTO valuationReportDTO = new ValuationReportDTO(valuationReport, property);
                    reportDTOs.add(valuationReportDTO);
                }

            }


        }
        return reportDTOs;

    }

}

