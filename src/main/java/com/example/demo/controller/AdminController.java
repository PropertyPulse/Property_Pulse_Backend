package com.example.demo.controller;

import com.example.demo.dto.requestDto.RequestAddNewInternalUserDto;
import com.example.demo.dto.responseDto.ResponseViewUsersDto;
import com.example.demo.exception.UserException;
import com.example.demo.service.AdminService;
import com.example.demo.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")

@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private final AdminService adminService;

    @Autowired
    private final EmailSenderService emailSenderService;


    @Autowired
    public AdminController(AdminService adminService, EmailSenderService emailSenderService) {
        this.adminService = adminService;
        this.emailSenderService = emailSenderService;
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
    @GetMapping("/viewUsers")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<ResponseViewUsersDto>> viewUsers() throws UserException{
        return ResponseEntity.ok(adminService.viewUsers());
    }

    @GetMapping("/testmail")
    public ResponseEntity<String> testmail() throws UserException{

        emailSenderService.sendSimpleEmail("janith.shashika@gmail.com",
                "This is email body",
                "This is email subject");

        return ResponseEntity.ok("mailsent");
    }





}
