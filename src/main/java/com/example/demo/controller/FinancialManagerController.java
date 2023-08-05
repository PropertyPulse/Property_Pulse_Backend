package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fm")

@PreAuthorize("hasRole('FINANCIALMANAGER')")
public class FinancialManagerController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('financialmanager:read')")
    public ResponseEntity<String> get(){
        // Replace the following string with an actual object you want to return as JSON
        // You can create a class representing the response data and return an instance of that class.
        String responseData = "GET :: Fm";
        return ResponseEntity.ok(responseData);
    }

}
