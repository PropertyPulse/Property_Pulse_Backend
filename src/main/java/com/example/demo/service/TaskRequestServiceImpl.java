package com.example.demo.service;

import com.example.demo.dto.responseDto.ResponseNewTaskRequestDto;
import com.example.demo.entity.TaskRequest;
import com.example.demo.entity.TaskStatus;
import com.example.demo.repository.TaskRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class TaskRequestServiceImpl implements TaskRequestService {

    public final TaskRequestRepository taskRequestRepository;

    public TaskRequestServiceImpl(TaskRequestRepository taskRequestRepository) {
        this.taskRequestRepository = taskRequestRepository;
    }

    @Override
    public List<ResponseNewTaskRequestDto> getAllNewTaskRequests() {
        List<TaskRequest> newTaskRequests = taskRequestRepository.findAllByStatus(TaskStatus.PENDING);

        List<ResponseNewTaskRequestDto> responseDtos = newTaskRequests
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return responseDtos;
    }

    private ResponseNewTaskRequestDto convertToDto(TaskRequest taskRequest) {
        ResponseNewTaskRequestDto responseDto = new ResponseNewTaskRequestDto();
        responseDto.setId(taskRequest.getId());
        responseDto.setPropertyId(taskRequest.getProperty().getId());
        responseDto.setLocation(taskRequest.getLocation());
        responseDto.setTask(taskRequest.getTask());
        responseDto.setEstimatedPrice(taskRequest.getEstimatedprice());
        responseDto.setScheduleDate(taskRequest.getScheduleDate());
//        responseDto.setMoreInfo(taskRequest.getTaskSupervisor().getContactNo());
        responseDto.setTaskStatus(taskRequest.getStatus());


        return responseDto;
    }
}
