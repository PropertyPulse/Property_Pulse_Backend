package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestUserDetailsDto;
import com.example.demo.dto.responseDto.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public interface TaskSupervisorService {

    ResponseTsDetailsDto getTasksupervisorDetails(RequestUserDetailsDto req);

}
