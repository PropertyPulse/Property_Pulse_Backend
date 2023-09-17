package com.example.demo.service;

import com.example.demo.dto.responseDto.ResponseAssignedPropertiesTSDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public interface AssignedPropertiesTSService {
    List<ResponseAssignedPropertiesTSDto> assignedPropertiesTS(String email);

}