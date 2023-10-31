package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestAddNewTaskRequestDto;
import com.example.demo.exception.UserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface NewTaskRequestPOService {
    String addNewTaskRequest(RequestAddNewTaskRequestDto req) throws UserException;
}
