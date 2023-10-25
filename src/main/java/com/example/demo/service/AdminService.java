package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestAddNewInternalUserDto;
import com.example.demo.dto.responseDto.ResponseViewUsersDto;
import com.example.demo.exception.UserException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public interface AdminService {

    String addTopManager(RequestAddNewInternalUserDto req) throws UserException;
    String addFinanceManager(RequestAddNewInternalUserDto req) throws UserException;
    String addTaskSupervisor(RequestAddNewInternalUserDto req) throws UserException;
    String addValuationExpert(RequestAddNewInternalUserDto req) throws UserException;

    List<ResponseViewUsersDto> viewUsers() throws UserException;
}
