package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestMakeComplaintDto;
import com.example.demo.exception.UserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface MakeComplaintService {
    String makeNewComplaint(RequestMakeComplaintDto req) throws UserException;
}
