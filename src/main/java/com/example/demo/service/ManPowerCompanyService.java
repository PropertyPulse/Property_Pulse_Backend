package com.example.demo.service;


import com.example.demo.auth.RegisterRequest;
import com.example.demo.dto.requestDto.RequestEmployeeDetailsDto;
import com.example.demo.dto.requestDto.RequestUpdateEmployeeDetailsDto;
import com.example.demo.dto.requestDto.RequestUserdetails;
import com.example.demo.dto.responseDto.ResponseEmployeeDetailsDto;
import com.example.demo.dto.responseDto.ResponsePo;
import com.example.demo.dto.responseDto.ResponseUpdateEmployeeDetailsDto;
import com.example.demo.exception.UserException;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public interface ManPowerCompanyService {

    ResponseEmployeeDetailsDto addEmployee(RequestEmployeeDetailsDto req) throws UserException;

    ResponseEntity<List<ResponseEmployeeDetailsDto>> getallEmployees(RequestUserdetails req) throws UserException;

    ResponseUpdateEmployeeDetailsDto updateEmployee(Integer employeeId, RequestUpdateEmployeeDetailsDto req) throws UserException;

    String deleteEmployee(Integer employeeId) throws UserException;
}
