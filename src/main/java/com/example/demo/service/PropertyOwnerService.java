package com.example.demo.service;

import com.example.demo.auth.RegisterRequest;
import com.example.demo.dto.responseDto.ResponseOngoingTasksDto;
import com.example.demo.dto.responseDto.ResponsePo;
import com.example.demo.exception.UserException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public interface PropertyOwnerService {

    public ResponsePo addPropertyOwner(RegisterRequest req);

    List<ResponseOngoingTasksDto> getOngoingTasks(String email) throws UserException;
}
