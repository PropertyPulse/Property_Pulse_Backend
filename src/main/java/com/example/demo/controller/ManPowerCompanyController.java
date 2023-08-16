package com.example.demo.controller;

import com.example.demo.dto.requestDto.RequestAssignDto;
import com.example.demo.dto.requestDto.RequestEmployeeDetailsDto;
import com.example.demo.dto.requestDto.RequestUpdateEmployeeDetailsDto;
import com.example.demo.dto.requestDto.RequestUserdetails;
import com.example.demo.dto.responseDto.ResponseEmployeeDetailsDto;
import com.example.demo.dto.responseDto.ResponseUpdateEmployeeDetailsDto;
import com.example.demo.exception.UserException;
import com.example.demo.service.ManPowerCompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mpc")

@PreAuthorize("hasRole('MPC')")
public class ManPowerCompanyController {

    private final ManPowerCompanyService manPowerCompanyService;

    public ManPowerCompanyController(ManPowerCompanyService manPowerCompanyService) {
        this.manPowerCompanyService = manPowerCompanyService;
    }

    //    trial controller
    @PreAuthorize("hasAuthority('mpc:create')")
    @PostMapping
    public ResponseEntity<String> get(){
        // Replace the following string with an actual object you want to return as JSON
        // You can create a class representing the response data and return an instance of that class.
        String responseData = "GET :: ADMIN";
        return ResponseEntity.ok(responseData);

    }

    @PreAuthorize("hasAuthority('mpc:create')")
    @PostMapping("/addemployee")
    public ResponseEntity<ResponseEmployeeDetailsDto> addEmployee(@RequestBody RequestEmployeeDetailsDto req) throws UserException {
        // Replace the following string with an actual object you want to return as JSON
        // You can create a class representing the response data and return an instance of that class.


        return ResponseEntity.ok(manPowerCompanyService.addEmployee(req));
    }

//    @GetMapping("/getAll")
//    public ResponseEntity<List<BookResponseDto>> getBooks() {
//        List<BookResponseDto> bookResponseDtos = bookService.getBooks();
//        return new ResponseEntity<>(bookResponseDtos, HttpStatus.OK);
//    }


    @PreAuthorize("hasAuthority('mpc:create')")
    @PostMapping("/getallemployees")
    public ResponseEntity<List<ResponseEmployeeDetailsDto>> getallEmployees(@RequestBody RequestUserdetails req) throws UserException {
        return manPowerCompanyService.getallEmployees(req);
    }


    @PreAuthorize("hasAuthority('mpc:update')")
    @PutMapping("/updateemployee/{employeeId}")
    public ResponseEntity<ResponseUpdateEmployeeDetailsDto> updateEmployee(
            @PathVariable Integer employeeId,
            @RequestBody RequestUpdateEmployeeDetailsDto req) throws UserException {
        // Call a service method to update the employee using the provided employeeId and request data
        // Replace the following string with an actual object you want to return as JSON
        // You can create a class representing the response data and return an instance of that class.

        return ResponseEntity.ok(manPowerCompanyService.updateEmployee(employeeId, req));
    }

    @PreAuthorize("hasAuthority('mpc:delete')")
    @DeleteMapping("/deleteemployee/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer employeeId) throws UserException {
        // Call a service method to delete the employee using the provided employeeId
        // Replace the following string with a suitable response message
        String responseMessage = manPowerCompanyService.deleteEmployee(employeeId);

        return ResponseEntity.ok(responseMessage);
    }


    @GetMapping("/getallavailableemployees")
    public ResponseEntity<List<ResponseEmployeeDetailsDto>> getallAvailableEmployees() throws UserException {
        return manPowerCompanyService.getallavailableEmployees();
    }


    @PostMapping("/assignemployee")
    public ResponseEntity<String> assignEmployee(@RequestBody RequestAssignDto req) throws UserException {
        return manPowerCompanyService.assignEmployee(req);
    }








}
