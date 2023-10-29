package com.example.demo.service;

import com.example.demo.dto.responseDto.*;
import com.example.demo.exception.UserException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public interface TaskService {

    Map<LocalDate, List<ResponseUpcomingTasksDto>> getUpcomingTasks(String email) throws UserException;
    List<ResponseOngoingTasksDto> getOngoingTasks(String email) throws UserException;
    Map<LocalDate, List<ResponseCompletedTasksDto>> getCompletedTasks(String email) throws UserException;

    Boolean startTask(int taskId);

}
