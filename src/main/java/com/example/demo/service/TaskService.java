package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestAddNewScheduledTaskDto;
import com.example.demo.dto.responseDto.ResponseAddNewScheduledTask;
import com.example.demo.dto.responseDto.ResponseOngoingTasksDto;
import com.example.demo.exception.UserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface TaskService {
    ResponseOngoingTasksDto getTaskById(Integer id);

    String addNewScheduledTask(RequestAddNewScheduledTaskDto req) throws UserException;

    List<ResponseAddNewScheduledTask> getAllScheduledTasks();
}
