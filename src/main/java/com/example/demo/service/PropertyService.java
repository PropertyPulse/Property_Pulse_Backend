package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestAddNewPropertyDto;
import com.example.demo.dto.responseDto.ResponseAddNewPropertyDto;
import com.example.demo.exception.UserException;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public interface PropertyService {
    String addNewProperty(RequestAddNewPropertyDto req) throws UserException;

    // ResponseEntity<List<ResponseAddNewPropertyDto>> getAllProperties() throws  UserException;
}
