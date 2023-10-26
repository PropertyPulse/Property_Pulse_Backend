package com.example.demo.controller;

import com.example.demo.dto.responseDto.*;
import com.example.demo.entity.TaskRequest;
import com.example.demo.exception.UserException;
import com.example.demo.service.TaskSupervisorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@PreAuthorize("hasAnyRole('TASKSUPERVISOR','MPC')")
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskSupervisorService taskSupervisorService;

    public TaskController(TaskSupervisorService taskSupervisorService) {
        this.taskSupervisorService = taskSupervisorService;
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

}
