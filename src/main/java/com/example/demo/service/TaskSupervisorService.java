package com.example.demo.service;

import com.example.demo.auth.RegisterRequest;
import com.example.demo.dto.requestDto.RequestUserdetails;
import com.example.demo.dto.responseDto.ResponsePo;
import com.example.demo.dto.responseDto.ResponseTsdetails;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public interface TaskSupervisorService {

    public ResponseTsdetails getTasksupervisorDetails(RequestUserdetails req);



}
