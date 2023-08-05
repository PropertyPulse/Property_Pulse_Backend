package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/po")

@PreAuthorize("hasRole('PROPERTYOWNER')")

public class PropertyOwnerController {

    @GetMapping
    @PreAuthorize("hasAuthority('propertyowner:read')")
    public ResponseEntity<String> get(){
        // Replace the following string with an actual object you want to return as JSON
        // You can create a class representing the response data and return an instance of that class.
        String responseData = "GET :: propertyowner";
        return ResponseEntity.ok(responseData);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('propertyowner:create')")
    public String post(){
        return "POST :: propertyowner";
    }

    @PutMapping
    @PreAuthorize("hasAuthority('propertyowner:update')")
    public String put(){
        return "PUT :: propertyowner";
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('propertyowner:delete')")
    public String delete(){
        return "DELETE :: propertyowner";
    }

}
