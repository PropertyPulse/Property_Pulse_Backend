package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestTaskListDto;
import com.example.demo.dto.responseDto.ResponsePropertiesToBeManagedDto;
import com.example.demo.dto.responseDto.ResponseTaskListDto;
import com.example.demo.entity.*;
import com.example.demo.dto.responseDto.*;
import com.example.demo.entity.Property;
import com.example.demo.entity.Task;
import com.example.demo.exception.UserException;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.util.*;

import java.util.stream.Collectors;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
  
    public TaskServiceImpl(UserRepository userRepository, TaskRepository taskRepository, PropertyRepository propertyRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public List<RequestTaskListDto> getTaskList(Integer propertyId){

        List<Task> tasks = taskRepository.findByPropertyId(propertyId);

        List<RequestTaskListDto> requestTaskListDto = tasks.stream()
                .filter(task -> task.getProperty().getId().equals(propertyId))
                .map(this::mapTaskList)
                .collect(Collectors.toList());

        return requestTaskListDto;
    }

    private RequestTaskListDto mapTaskList(Task t) {

        RequestTaskListDto dto = new RequestTaskListDto();

        dto.setPropertyId(t.getProperty().getId());
        dto.setTaskName(t.getTask());
        dto.setTaskId(t.getId());
        return dto;
    }

    @Override
    @Transactional
    public Map<LocalDate, List<ResponseUpcomingTasksDto>> getUpcomingTasks(String email) throws UserException {

        Optional<User> user = userRepository.findByEmail(email);

        List<Property> properties = propertyRepository.findByTaskSupervisorId(user.get().getTaskSupervisor().getId());

        List<Task> upcomingTasks = new ArrayList<>();
        for (Property property : properties) {
            List<Task> tasks = taskRepository.findByPropertyId(property.getId());
            for (Task task: tasks) upcomingTasks.add(task);
        }

        LocalDate currentDate = LocalDate.now();

        List<ResponseUpcomingTasksDto> responseUpcomingTasksDto = upcomingTasks.stream()
                .filter(task ->
                    task.getStartDate().isAfter(currentDate) ||
                    (task.getStartDate().equals(currentDate) && task.getStatus().equals("Start Pending")))
                .map(this::mapTaskToDto)
                .collect(Collectors.toList());

        responseUpcomingTasksDto.sort(Comparator.comparing(ResponseUpcomingTasksDto::getStartDate, Comparator.reverseOrder()));

        Map<LocalDate, List<ResponseUpcomingTasksDto>> tasksGroupedByDate = responseUpcomingTasksDto.stream()
                .collect(Collectors.groupingBy(dto -> dto.getStartDate()));

        Map<LocalDate, List<ResponseUpcomingTasksDto>> sortedTasks = tasksGroupedByDate.entrySet()
                .stream()
                .sorted(Map.Entry.<LocalDate, List<ResponseUpcomingTasksDto>>comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        return sortedTasks;
    }

    private ResponseUpcomingTasksDto mapTaskToDto(Task task) {

        ResponseUpcomingTasksDto dto = new ResponseUpcomingTasksDto();

        dto.setTaskId(task.getId());
        dto.setPropertyId(task.getProperty().getId());
        dto.setTask(task.getTask());
        dto.setStartDate(task.getStartDate());
        dto.setRequestStatus(task.getManpowerCompanyRequestStatus());
        dto.setLocation(task.getProperty().getLocation());
        dto.setTaskStatus(task.getStatus());

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

        List<ResponseOngoingTasksDto> ongoingTasks = tasks.stream()
                .filter(task -> task.getStatus().equals("Ongoing") )
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
        dto.setTaskStatus(task.getStatus());

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

        List<ResponseCompletedTasksDto> completedTasks = tasks.stream()
                .filter(task -> task.getStatus().equals("Completed"))
                .map(this::mapCompletedTaskToDto)
                .collect(Collectors.toList());

        completedTasks.sort(Comparator.comparing(ResponseCompletedTasksDto::getEndDate, Comparator.reverseOrder()));

        Map<LocalDate, List<ResponseCompletedTasksDto>> tasksGroupedByDate = completedTasks.stream()
                .collect(Collectors.groupingBy(dto -> dto.getEndDate()));

        Map<LocalDate, List<ResponseCompletedTasksDto>> sortedTasks = tasksGroupedByDate.entrySet()
                .stream()
                .sorted(Map.Entry.<LocalDate, List<ResponseCompletedTasksDto>>comparingByKey().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        return sortedTasks;

    }

    private ResponseCompletedTasksDto mapCompletedTaskToDto(Task task) {

        ResponseCompletedTasksDto dto = new ResponseCompletedTasksDto();

        dto.setPropertyId(task.getProperty().getId());
        dto.setTaskId(task.getId());
        dto.setTask(task.getTask());
        dto.setStartDate(task.getStartDate().toString());
        dto.setEndDate(task.getEndDate());
        dto.setTaskStatus(task.getStatus());

        return dto;
    }

    @Override
    public Boolean startTask(int taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            Task startingtask = task.get();
            startingtask.setStatus("Ongoing");
            taskRepository.save(startingtask);
            return true;
        }
        return false;
    }

    @Override
    public Boolean endTask(int taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            Task endingTask = task.get();
            endingTask.setStatus("Completed");
            taskRepository.save(endingTask);
            return true;
        }
        return false;
    }

    @Override
    public Boolean setTaskList(List<ResponseTaskListDto> responseTaskListDtoList) {
        boolean atLeastOneTaskUpdated = false;

        for (ResponseTaskListDto responseTaskListDto : responseTaskListDtoList) {
            Optional<Task> taskOptional = taskRepository.findById(responseTaskListDto.getTaskId());

            if (taskOptional.isPresent()) {
                Task updatingTask = taskOptional.get();
                updatingTask.setCost(responseTaskListDto.getCost());
                updatingTask.setTimeInDays(responseTaskListDto.getTimeInDays());
                updatingTask.setNoOfWorkers(responseTaskListDto.getNoOfWorkers());
                taskRepository.save(updatingTask);
                atLeastOneTaskUpdated = true;
            }
        }

        return atLeastOneTaskUpdated;
    }


}
