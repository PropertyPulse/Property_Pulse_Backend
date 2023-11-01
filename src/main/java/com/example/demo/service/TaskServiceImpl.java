package com.example.demo.service;
import com.example.demo.dto.requestDto.RequestAddNewScheduledTaskDto;
import com.example.demo.dto.responseDto.ResponseAddNewScheduledTask;
import com.example.demo.dto.responseDto.ResponseOngoingTasksDto;
import com.example.demo.entity.ScheduleTask;
import com.example.demo.entity.Task;
import com.example.demo.exception.UserException;
import com.example.demo.repository.ScheduledTaskRepository;
import com.example.demo.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import com.example.demo.dto.responseDto.ResponsePropertiesToBeManagedDto;
import com.example.demo.dto.responseDto.ResponseTaskListDto;
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
    private final ScheduledTaskRepository scheduledTaskRepository;
  
    public TaskServiceImpl(UserRepository userRepository, TaskRepository taskRepository, PropertyRepository propertyRepository, ScheduledTaskRepository scheduledTaskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.propertyRepository = propertyRepository;
        this.scheduledTaskRepository = scheduledTaskRepository;
    }
  
     @Override
    public ResponseOngoingTasksDto getTaskById(Integer id) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        Task task = taskOptional.get();

        ResponseOngoingTasksDto responseDto = convertToDto(task);

        return responseDto;
    }

    private ResponseOngoingTasksDto convertToDto(Task task) {
        ResponseOngoingTasksDto dto = new ResponseOngoingTasksDto();

        dto.setTask(task.getTask());
        dto.setStartedDate(task.getStartDate());
        dto.setEndingDate(task.getEndDate());
        dto.setTaskStatus(task.getStatus());
        dto.setPropertyId(task.getProperty().getId());
    }

    @Override
    public List<ResponseTaskListDto> getTaskList(Integer propertyId){

        List<Task> tasks = taskRepository.findByPropertyId(propertyId);


        List<ResponseTaskListDto> responseTaskListDtos = tasks.stream()
                .filter(task -> task.getProperty().getId().equals(propertyId))
                .map(this::mapTaskList)
                .collect(Collectors.toList());

        return responseTaskListDtos;
    }

    private ResponseTaskListDto mapTaskList(Task t) {

        ResponseTaskListDto dto = new ResponseTaskListDto();

        dto.setCost(t.getCost());
        dto.setPropertyId(t.getProperty().getId());
        dto.setTimeInDays(t.getTimeInDays());
        dto.setEstimatedPrice(t.getEstimatedPrice());
        dto.setTaskName(t.getTask());
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
        dto.setAddress(task.getProperty().getAddress());
        dto.setTaskStatus(task.getStatus());

        return dto;
    }

    @Override
    @Transactional
    public String addNewScheduledTask(RequestAddNewScheduledTaskDto req) throws UserException {
        ScheduleTask task = new ScheduleTask();
        task.setTask(req.getTask());

        var savedTask = scheduledTaskRepository.save(task);
        return "Schedule task added successfully";
    }

    @Override
    public List<ResponseAddNewScheduledTask> getAllScheduledTasks() {
        List<ScheduleTask> scheduleTasks = scheduledTaskRepository.findAll();

        return scheduleTasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ResponseAddNewScheduledTask convertToDto(ScheduleTask scheduledTask) {
        ResponseAddNewScheduledTask dto = new ResponseAddNewScheduledTask();

        dto.setTask(scheduledTask.getTask());
        dto.setId(scheduledTask.getId());

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
        dto.setAddress(task.getProperty().getAddress());
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

}
