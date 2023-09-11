package com.example.demo.controller;

import com.example.demo.dto.responseDto.ValuationReportDTO;
import com.example.demo.exception.InvalidStatusException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.ValuationReport;

import com.example.demo.service.ValuationExpertService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tm")

@PreAuthorize("hasRole('TOPMANAGER')")
public class TopManagerController {


    private final ValuationExpertService valuationExpertService;

    // Constructor for injecting ValuationExpertService
    public TopManagerController(ValuationExpertService valuationExpertService) {
        this.valuationExpertService = valuationExpertService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('topmanager:read')")
    public ResponseEntity<String> get(){
        // Replace the following string with an actual object you want to return as JSON
        // You can create a class representing the response data and return an instance of that class.
        String responseData = "GET :: CUSTOMER";
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/requestValuationReport")

    @PreAuthorize("hasAuthority('topmanager:create')")
    public ResponseEntity<String>  requestReport(
            @RequestParam("propertyId") Long propertyId,
                                                  @RequestParam("requestedDate") String requestedDate
    ) throws Exception{

        ValuationReport report  =  valuationExpertService.requestReport(propertyId,requestedDate);

        return ResponseEntity.ok("A valuation report  Request was successfully created");

    }


    @PreAuthorize("hasAuthority('topmanager:read')")
    @GetMapping("/api/reports/{type}")
    public ResponseEntity<List<ValuationReportDTO>> getReportsByType(
            @PathVariable String type
    ) throws Exception {


        if(!type.equalsIgnoreCase("pending") && !type.equalsIgnoreCase("submitted"))
        {
            throw new InvalidStatusException("Invalid status: " + type);
        }
        // Fetch reports based on 'type' (e.g., 'pending' or 'submitted')
        List<ValuationReportDTO> reports = valuationExpertService.findByStatus(type);


        if (!reports.isEmpty()) {
            return ResponseEntity.ok(reports); // Return 200 OK with the list of reports
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if no reports found
        }
    }



}
