package com.example.demo.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ve")

@PreAuthorize("hasRole('VALUATIONEXPERT')")


public class ValuationExpertController {


    @PostMapping("/uploadValuationReport")
    @PreAuthorize("hasAuthority('valuationexpert:create')")
    public String uploadValuationReport(){
 return null;
    }


//    @GetMapping("/viewPendingReports")
//    @PreAuthorize("hasAuthority('valuationexpert:read')")
//    public String viewPendingReports(){
//
//    }
//
//    @GetMapping("/viewSubmittedReports")
//    @PreAuthorize("hasAuthority('valuationexpert:read')")
//    public String viewSubmittedReports(){
//
//    }

}

