package com.example.demo.service;
import com.example.demo.dto.responseDto.ResponsePropertiesToBeManagedDto;
import com.example.demo.dto.responseDto.ResponseTaskListDto;
import com.example.demo.entity.*;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;


    public TaskServiceImpl (TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @Override
    public List<ResponseTaskListDto> getTaskList(Integer propertyId){

        List<Task> tasks = taskRepository.findByPropertyId(propertyId);


        List<ResponseTaskListDto> responseTaskListDtos = tasks.stream()
                .filter(task -> task.getProperty().getId().equals(propertyId))
                .map(this::mapTaskToDto)
                .collect(Collectors.toList());

        return responseTaskListDtos;
    }

    private ResponseTaskListDto mapTaskToDto(Task t) {

        ResponseTaskListDto dto = new ResponseTaskListDto();

        dto.setCost(t.getCost());
        dto.setPropertyId(t.getProperty().getId());
        dto.setTimeInDays(t.getTimeInDays());
        dto.setEstimatedPrice(t.getEstimatedPrice());
        dto.setTaskName(t.getTask());

        return dto;
    }

}

