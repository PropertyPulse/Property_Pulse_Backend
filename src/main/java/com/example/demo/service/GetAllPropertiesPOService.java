package com.example.demo.service;

import com.example.demo.dto.responseDto.ResponseGetAllPropertiesByUserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface GetAllPropertiesPOService {
    List<ResponseGetAllPropertiesByUserDto> getAllPropertiesPO(String email);
}
