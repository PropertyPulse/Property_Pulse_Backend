package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestTaskListDto;
import com.example.demo.dto.requestDto.RequestAddNewScheduledTaskDto;
import com.example.demo.dto.responseDto.ResponseAddNewScheduledTask;
import com.example.demo.dto.responseDto.ResponseOngoingTasksDto;
import com.example.demo.exception.UserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dto.responseDto.ResponsePropertiesToBeManagedDto;
import java.util.List;
import com.example.demo.dto.responseDto.ResponseTaskListDto;
import com.example.demo.dto.responseDto.*;
import java.time.LocalDate;
import java.util.Map;


@Service
@Transactional
public interface TaskService {
    List<RequestTaskListDto> getTaskList (Integer propertyId);

    ResponseOngoingTasksDto getTaskById(Integer id);

    String addNewScheduledTask(RequestAddNewScheduledTaskDto req) throws UserException;

    List<ResponseAddNewScheduledTask> getAllScheduledTasks();

    List<ResponseTaskListDto> getTaskList (Integer propertyId);


    Map<LocalDate, List<ResponseUpcomingTasksDto>> getUpcomingTasks(String email) throws UserException;
    List<ResponseOngoingTasksDto> getOngoingTasks(String email) throws UserException;
    Boolean setTaskList(List<ResponseTaskListDto> responseTaskListDto) throws UserException;
    Map<LocalDate, List<ResponseCompletedTasksDto>> getCompletedTasks(String email) throws UserException;
    Boolean startTask(int taskId);
    Boolean endTask(int taskId);

}
