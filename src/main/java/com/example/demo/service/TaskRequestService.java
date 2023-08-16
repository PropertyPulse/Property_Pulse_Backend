package com.example.demo.service;

import com.example.demo.dto.responseDto.ResponseNewTaskRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskRequestService {

    List<ResponseNewTaskRequestDto> getAllNewTaskRequests();



}
