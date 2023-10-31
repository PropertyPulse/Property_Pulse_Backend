package com.example.demo.controller;

import com.example.demo.dto.requestDto.RequestAddNewScheduledTaskDto;
import com.example.demo.dto.responseDto.*;
import com.example.demo.entity.TaskRequest;
import com.example.demo.exception.UserException;
import com.example.demo.service.PropertyOwnerService;
import com.example.demo.service.TaskService;
import com.example.demo.service.TaskSupervisorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@PreAuthorize("hasAnyRole('TASKSUPERVISOR','MPC','PROPERTYOWNER)")
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskSupervisorService taskSupervisorService;
    private final PropertyOwnerService propertyOwnerService;
    private final TaskService taskService;

    public TaskController(TaskSupervisorService taskSupervisorService, PropertyOwnerService propertyOwnerService, TaskService taskService) {
        this.taskSupervisorService = taskSupervisorService;
        this.propertyOwnerService = propertyOwnerService;
        this.taskService = taskService;
    }


    //
    @PostMapping("/addnewtask")
    public ResponseEntity<String> addNewTask(@RequestBody TaskRequest taskRequest) {
        return ResponseEntity.ok("New task added");
    }

    //Task Supervisor
    @GetMapping("/upcoming-tasks")
    @PreAuthorize("hasAuthority('tasksupervisor:read')")
    public ResponseEntity<Map<LocalDate, List<ResponseUpcomingTasksDto>>> getUpcomingTasks(@RequestParam("email") String email) throws UserException {
        Map<LocalDate, List<ResponseUpcomingTasksDto>> upcomingTasks = taskSupervisorService.getUpcomingTasks(email);
        return ResponseEntity.ok(upcomingTasks);
    }

    @GetMapping("/ongoing-tasks")
    @PreAuthorize("hasAuthority('tasksupervisor:read')")
    public ResponseEntity<List<ResponseOngoingTasksDto>> getOngoingTasks(@RequestParam("email") String email) throws UserException {
        List<ResponseOngoingTasksDto> ongoingTasks = taskSupervisorService.getOngoingTasks(email);
        return ResponseEntity.ok(ongoingTasks);
    }

    @GetMapping("/completed-tasks")
    @PreAuthorize("hasAuthority('tasksupervisor:read')")
    public ResponseEntity<Map<LocalDate, List<ResponseCompletedTasksDto>>> getCompletedTasks(@RequestParam("email") String email) throws UserException {
        Map<LocalDate, List<ResponseCompletedTasksDto>> completedTasks = taskSupervisorService.getCompletedTasks(email);
        return ResponseEntity.ok(completedTasks);
    }

    @GetMapping("/ongoing-tasks-po")
    @PreAuthorize("hasAuthority('propertyowner:read')")
    public ResponseEntity<List<ResponseOngoingTasksDto>> getOngoingTasksPO(@RequestParam("email") String email) throws UserException {
        List<ResponseOngoingTasksDto> ongoingTasks = propertyOwnerService.getOngoingTasks(email);
        return ResponseEntity.ok(ongoingTasks);
    }

    @GetMapping("/get-task-by-id")
    @PreAuthorize("hasAuthority('propertyowner:read')")
    public ResponseEntity<ResponseOngoingTasksDto> getTaskById(@RequestParam("id") Integer id) throws UserException {
        ResponseOngoingTasksDto task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping("/add-new-scheduled-task")
    @PreAuthorize("hasAuthority('propertyowner:create')")
    public ResponseEntity<String> addNewScheduledTask(@RequestBody RequestAddNewScheduledTaskDto req) throws UserException {
        return ResponseEntity.ok(taskService.addNewScheduledTask(req));
    }

    @GetMapping("/get-all-scheduled-tasks")
    @PreAuthorize("hasAuthority('propertyowner:read')")
    public ResponseEntity<List<ResponseAddNewScheduledTask>> getAllScheduledTasks() throws UserException {
        List<ResponseAddNewScheduledTask> scheduledTasks = taskService.getAllScheduledTasks();
        return ResponseEntity.ok(scheduledTasks);
    }

}
