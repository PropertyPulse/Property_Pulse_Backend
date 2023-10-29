package com.example.demo.service;

import com.example.demo.dto.responseDto.ResponseValuationDTO;
import com.example.demo.exception.ResourceNotFoundException;

import java.util.List;

public interface ValuationReportService {

    List<ResponseValuationDTO> getValuationReports(String status) throws ResourceNotFoundException;
}
