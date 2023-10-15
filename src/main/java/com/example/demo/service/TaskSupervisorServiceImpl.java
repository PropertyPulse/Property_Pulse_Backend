package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestUserDetailsDto;
import com.example.demo.dto.responseDto.ResponseCompletedTasksDto;
import com.example.demo.dto.responseDto.ResponseOngoingTasksDto;
import com.example.demo.dto.responseDto.ResponseTsDetailsDto;
import com.example.demo.dto.responseDto.ResponseUpcomingTasksDto;
import com.example.demo.entity.Property;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskSupervisor;
import com.example.demo.exception.UserException;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TaskSupervisorRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskSupervisorServiceImpl implements TaskSupervisorService {

    private final UserRepository userRepository;
    private final TaskSupervisorRepository taskSupervisorRepository;
    private final TaskRepository taskRepository;
    private final PropertyRepository propertyRepository;
    public TaskSupervisorServiceImpl(UserRepository userRepository, TaskSupervisorRepository taskSupervisorRepository,
                                     TaskRepository taskRepository, PropertyRepository propertyRepository) {
        this.userRepository = userRepository;
        this.taskSupervisorRepository = taskSupervisorRepository;
        this.taskRepository = taskRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public ResponseTsDetailsDto getTasksupervisorDetails(RequestUserDetailsDto req) {


        ResponseTsDetailsDto responseTsdetailsDto = new ResponseTsDetailsDto();

        Optional<User> userOptional = userRepository.findByEmail(req.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            TaskSupervisor taskSupervisor = taskSupervisorRepository.findTaskSupervisorByUser(user);

//            responseTsdetails.setFirstname(taskSupervisor.getFirstname());
//            responseTsdetails.setLastname(taskSupervisor.getLastname());
        }


        return responseTsdetailsDto;
    }

    @Override
    @Transactional
    public List<ResponseUpcomingTasksDto> getUpcomingTasks(String email) throws UserException {

        Optional<User> user = userRepository.findByEmail(email);

        List<Property> properties = propertyRepository.findByTaskSupervisorId(user.get().getTaskSupervisor().getId());

        List<Task> upcomingTasks = new ArrayList<>();
        for (Property property : properties) {
            List<Task> tasks = taskRepository.findByPropertyId(property.getId());
            for (Task task: tasks) upcomingTasks.add(task);
        }

        LocalDate currentDate = LocalDate.now();
        List<ResponseUpcomingTasksDto> responseUpcomingTasksDto = upcomingTasks.stream()
                .filter(task -> task.getStartDate() != null && task.getStartDate().isAfter(currentDate))
                .map(this::mapTaskToDto)
                .collect(Collectors.toList());

        return responseUpcomingTasksDto;
    }

    private ResponseUpcomingTasksDto mapTaskToDto(Task task) {

        ResponseUpcomingTasksDto dto = new ResponseUpcomingTasksDto();

        dto.setTaskId(task.getId());
        dto.setPropertyId(task.getProperty().getId());
        dto.setTask(task.getTask());
        dto.setRequestStatus(task.getManpowerCompanyRequestStatus());
        dto.setLocation(task.getProperty().getLocation());

        return dto;
    }

    @Override
    @Transactional
    public List<ResponseOngoingTasksDto> getOngoingTasks(String email) throws UserException {

        Optional<User> user = userRepository.findByEmail(email);

        List<Property> properties = propertyRepository.findByTaskSupervisorId(user.get().getTaskSupervisor().getId());

        List<Task> tasks = new ArrayList<>();
        for (Property property : properties) {
            List<Task> tasksOfTheProperty = taskRepository.findByPropertyId(property.getId());
            for (Task task: tasksOfTheProperty) tasks.add(task);
        }

        LocalDate currentDate = LocalDate.now();

        List<ResponseOngoingTasksDto> ongoingTasks = tasks.stream()
                .filter(task -> (task.getStartDate().isBefore(currentDate) && task.getEndDate().isAfter(currentDate))
                        || task.getStartDate().equals(currentDate)
                        || task.getEndDate().equals(currentDate))
                .map(this::mapOngoingTaskToDto)
                .collect(Collectors.toList());

        return ongoingTasks;

    }

    private ResponseOngoingTasksDto mapOngoingTaskToDto(Task task) {

        ResponseOngoingTasksDto dto = new ResponseOngoingTasksDto();

        dto.setTaskId(task.getId());
        dto.setPropertyId(task.getProperty().getId());
        dto.setManpowerCompany(task.getManpowerCompany());
        dto.setLocation(task.getProperty().getLocation());

        return dto;
    }

    @Override
    @Transactional
    public Map<LocalDate, List<ResponseCompletedTasksDto>> getCompletedTasks(String email) throws UserException {

        Optional<User> user = userRepository.findByEmail(email);

        List<Property> properties = propertyRepository.findByTaskSupervisorId(user.get().getTaskSupervisor().getId());

        List<Task> tasks = new ArrayList<>();
        for (Property property : properties) {
            List<Task> tasksOfTheProperty = taskRepository.findByPropertyId(property.getId());
            for (Task task: tasksOfTheProperty) tasks.add(task);
        }

        LocalDate currentDate = LocalDate.now();

        List<ResponseCompletedTasksDto> completedTasks = tasks.stream()
                .filter(task -> task.getEndDate().isBefore(currentDate))
                .map(this::mapCompletedTaskToDto)
                .collect(Collectors.toList());

        Map<LocalDate, List<ResponseCompletedTasksDto>> tasksGroupedByDate = completedTasks.stream()
                .collect(Collectors.groupingBy(dto -> dto.getEndDate()));

        return tasksGroupedByDate;

    }

    private ResponseCompletedTasksDto mapCompletedTaskToDto(Task task) {

        ResponseCompletedTasksDto dto = new ResponseCompletedTasksDto();

        dto.setPropertyId(task.getProperty().getId());
        dto.setTaskId(task.getId());
        dto.setTask(task.getTask());
        dto.setStartDate(task.getStartDate());
        dto.setEndDate(task.getEndDate());

        return dto;
    }

}
