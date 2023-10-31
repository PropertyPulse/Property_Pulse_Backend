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
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ScheduledTaskRepository scheduledTaskRepository;

    public TaskServiceImpl(TaskRepository taskRepository, ScheduledTaskRepository scheduledTaskRepository) {
        this.taskRepository = taskRepository;
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
}
