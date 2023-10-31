package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestAddNewPropertyDto;
import com.example.demo.dto.responseDto.ResponseAddNewPropertyDto;
import com.example.demo.dto.responseDto.ResponseGetAllPropertiesByUserDto;
import com.example.demo.exception.UserException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public interface PropertyService {
    ResponseAddNewPropertyDto addNewProperty(RequestAddNewPropertyDto req) throws UserException;

    List<ResponseGetAllPropertiesByUserDto> getAllPropertiesByUser(String email);

    ResponseAddNewPropertyDto getPropertyById(Integer id);

}
