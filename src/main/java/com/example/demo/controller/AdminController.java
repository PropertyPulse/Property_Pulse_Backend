package com.example.demo.controller;

import com.example.demo.dto.requestDto.RequestAddNewInternalUserDto;
import com.example.demo.exception.UserException;
import com.example.demo.service.AdminService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")

@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<String> get(){
        String responseData = "GET :: ADMIN";
        return ResponseEntity.ok(responseData);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public String post(){
        return "POST :: ADMIN";
    }



    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public String put(){
        return "PUT :: ADMIN";
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    public String delete(){
        return "DELETE :: ADMIN";
    }


    @PostMapping("/addTopManager")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<String> addTopManager(@RequestBody RequestAddNewInternalUserDto req ) throws UserException {
        return ResponseEntity.ok(adminService.addTopManager(req));
    }


    @PostMapping("/addFinanceManager")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<String> addFinanceManager(@RequestBody RequestAddNewInternalUserDto req ) throws UserException {
        return ResponseEntity.ok(adminService.addFinanceManager(req));
    }
    @PostMapping("/addTaskSupervisor")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<String> addTaskSupervisor(@RequestBody RequestAddNewInternalUserDto req ) throws UserException {
        return ResponseEntity.ok(adminService.addTaskSupervisor(req));
    }
    @PostMapping("/addValuationExpert")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<String> addValuationExpert(@RequestBody RequestAddNewInternalUserDto req ) throws UserException {
        return ResponseEntity.ok(adminService.addValuationExpert(req));
    }






}
