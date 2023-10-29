package com.example.demo.service;

import com.example.demo.dto.responseDto.ResponseNewTaskRequestDto;
import com.example.demo.dto.responseDto.ResponseTaskApprovalsDto;
import com.example.demo.entity.Property;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskRequest;
import com.example.demo.entity.TaskStatus;
import com.example.demo.exception.UserException;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TaskRequestRepository;
import com.example.demo.repository.TaskSupervisorRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class TaskRequestServiceImpl implements TaskRequestService {

    public final TaskRequestRepository taskRequestRepository;
    private final UserRepository userRepository;
    private final TaskSupervisorRepository taskSupervisorRepository;
    private final TaskRepository taskRepository;
    private final PropertyRepository propertyRepository;
    public TaskRequestServiceImpl(UserRepository userRepository, TaskSupervisorRepository taskSupervisorRepository,
                                     TaskRepository taskRepository, PropertyRepository propertyRepository, TaskRequestRepository taskRequestRepository) {
        this.userRepository = userRepository;
        this.taskSupervisorRepository = taskSupervisorRepository;
        this.taskRepository = taskRepository;
        this.propertyRepository = propertyRepository;
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


    @Override
    public List<ResponseTaskApprovalsDto> getTaskApprovals(String email) throws UserException {

        Optional<User> user = userRepository.findByEmail(email);

        List<Property> properties = propertyRepository.findByTaskSupervisorId(user.get().getTaskSupervisor().getId());

        List<Task> tasks = new ArrayList<>();
        for (Property property : properties) {
            List<Task> tasksOfTheProperty = taskRepository.findByPropertyId(property.getId());
            for (Task task: tasksOfTheProperty) tasks.add(task);
        }

        LocalDate currentDate = LocalDate.now();

        List<ResponseTaskApprovalsDto> taskApprovals = tasks.stream()
                .filter(task ->
                        task.getStartDate().isAfter(currentDate) ||
                                (task.getStartDate().equals(currentDate) && task.getStatus().equals("Start Pending")))
                .map(this::mapTaskApprovalsToDto)
                .collect(Collectors.toList());

        List<ResponseTaskApprovalsDto> sortedTaskApprovals = taskApprovals.stream()
                .sorted(Comparator.comparing(task -> task.getStartDate()))
                .collect(Collectors.toList());

        return sortedTaskApprovals;
    }

    private ResponseTaskApprovalsDto mapTaskApprovalsToDto(Task task) {

        ResponseTaskApprovalsDto dto = new ResponseTaskApprovalsDto();

        dto.setPropertyId(task.getProperty().getId());
        dto.setLocation(task.getProperty().getLocation());
        dto.setTaskId(task.getId());
        dto.setTask(task.getTask());
        dto.setManpowerCompanyRequestStatus(task.getManpowerCompanyRequestStatus());
        dto.setStartDate(task.getStartDate());

        return dto;
    }
}
