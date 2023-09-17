package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestUserDetailsDto;
import com.example.demo.dto.responseDto.ResponseTsDetailsDto;
import com.example.demo.dto.responseDto.ResponseUpcomingTasksDto;
import com.example.demo.exception.UserException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public interface TaskSupervisorService {

    public ResponseTsDetailsDto getTasksupervisorDetails(RequestUserDetailsDto req);

    List<ResponseUpcomingTasksDto> getUpcomingTasks(String email) throws UserException;

}
