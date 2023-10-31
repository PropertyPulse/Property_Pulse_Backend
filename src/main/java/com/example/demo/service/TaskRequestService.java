package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestAddContactPersonDto;
import com.example.demo.dto.responseDto.ResponseNewTaskRequestDto;
import com.example.demo.dto.responseDto.ResponseTaskApprovalsDto;
import com.example.demo.exception.UserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface TaskRequestService {

    List<ResponseNewTaskRequestDto> getAllNewTaskRequests();

    List<ResponseTaskApprovalsDto> getTaskApprovals(String email) throws UserException;

    Boolean updateManpowerCompanyResponse(int taskId, String requestStatus);

    Boolean addContactPerson(RequestAddContactPersonDto req);
}
