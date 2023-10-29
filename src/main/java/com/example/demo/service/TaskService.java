package com.example.demo.service;

import com.example.demo.dto.responseDto.ResponsePropertiesToBeManagedDto;
import com.example.demo.dto.responseDto.ResponseTaskListDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public interface TaskService {
    List<ResponseTaskListDto> getTaskList (Integer propertyId);


}
