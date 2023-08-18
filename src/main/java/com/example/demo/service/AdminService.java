package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestAddnewInternalUserDto;
import com.example.demo.exception.UserException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public interface AdminService {

    String addTopmanager(RequestAddnewInternalUserDto req) throws UserException;
    String addFinancemanager(RequestAddnewInternalUserDto req) throws UserException;


    String addTaskSupervisor(RequestAddnewInternalUserDto req) throws UserException;
    String addValuationExpert(RequestAddnewInternalUserDto req) throws UserException;


//    String addInsurancemanager(RequestAddnewInternalUserDto req) throws UserException;
//    String addInsurancemanager(RequestAddnewInternalUserDto req) throws UserException;


}
