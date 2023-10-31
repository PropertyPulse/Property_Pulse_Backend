package com.example.demo.controller;

import com.example.demo.dto.requestDto.RequestTaskStatusDto;
import com.example.demo.dto.responseDto.*;
import com.example.demo.entity.TaskRequest;
import com.example.demo.exception.UserException;
import com.example.demo.service.TaskService;
import org.springframework.http.HttpStatus;
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

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
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
        Map<LocalDate, List<ResponseUpcomingTasksDto>> upcomingTasks = taskService.getUpcomingTasks(email);
        return ResponseEntity.ok(upcomingTasks);
    }

    @GetMapping("/ongoing-tasks")
    @PreAuthorize("hasAuthority('tasksupervisor:read')")
    public ResponseEntity<List<ResponseOngoingTasksDto>> getOngoingTasks(@RequestParam("email") String email) throws UserException {
        List<ResponseOngoingTasksDto> ongoingTasks = taskService.getOngoingTasks(email);
        return ResponseEntity.ok(ongoingTasks);
    }

    @GetMapping("/completed-tasks")
    @PreAuthorize("hasAuthority('tasksupervisor:read')")
    public ResponseEntity<Map<LocalDate, List<ResponseCompletedTasksDto>>> getCompletedTasks(@RequestParam("email") String email) throws UserException {
        Map<LocalDate, List<ResponseCompletedTasksDto>> completedTasks = taskService.getCompletedTasks(email);
        return ResponseEntity.ok(completedTasks);
    }

    @PutMapping("/upcoming-tasks/start-task")
    @PreAuthorize("hasAuthority('tasksupervisor:update')")
    public ResponseEntity<String> startTask(@RequestBody RequestTaskStatusDto req){

        try{

            Boolean isTaskStarted = taskService.startTask(req.getTaskId());
            if (isTaskStarted) {
                return ResponseEntity.ok("Task Started");
            }else {
                return ResponseEntity.badRequest().body("Failed to mark the task as started at the moment");
            }

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error occurred");
        }
    }

    @PutMapping("/ongoing-tasks/end-task")
    @PreAuthorize("hasAuthority('tasksupervisor:update')")
    public ResponseEntity<String> endTask(@RequestBody RequestTaskStatusDto req){

        try{

            Boolean isTaskEnded = taskService.endTask(req.getTaskId());
            if (isTaskEnded) {
                return ResponseEntity.ok("Task marked as Ended");
            }else {
                return ResponseEntity.badRequest().body("Failed to mark the task as ended at the moment");
            }

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error occurred");
        }
    }

}
