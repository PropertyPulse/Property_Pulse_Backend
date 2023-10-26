package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestUserDetailsDto;
import com.example.demo.dto.responseDto.*;
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
import java.util.*;
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
    public Map<LocalDate, List<ResponseUpcomingTasksDto>> getUpcomingTasks(String email) throws UserException {

        Optional<User> user = userRepository.findByEmail(email);

        List<Property> properties = propertyRepository.findByTaskSupervisorId(user.get().getTaskSupervisor().getId());

        List<Task> upcomingTasks = new ArrayList<>();
        for (Property property : properties) {
            List<Task> tasks = taskRepository.findByPropertyId(property.getId());
            for (Task task: tasks) upcomingTasks.add(task);
        }

//        LocalDate currentDate = LocalDate.now();
//        Map<LocalDate, List<ResponseUpcomingTasksDto>> responseUpcomingTasksDto = upcomingTasks.stream()
//                .filter(task ->
//                    task.getStartDate().isAfter(currentDate) ||
//                    (task.getStartDate().equals(currentDate) && task.getStatus().equals("Start Pending")) ||
//                    task.getStatus().equals("Start Pending"))
//                .map(this::mapTaskToDto)
//                .collect(Collectors.groupingBy(dto -> dto.getStartDate()));


        List<ResponseUpcomingTasksDto> responseUpcomingTasksDto = upcomingTasks.stream()
                .filter(task -> task.getStatus().equals("Start Pending"))
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
