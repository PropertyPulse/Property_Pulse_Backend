package com.example.demo.controller;

import com.example.demo.dto.responseDto.ResponseNewTaskRequestDto;
import com.example.demo.entity.TaskRequest;
import com.example.demo.service.TaskRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAnyRole('TASKSUPERVISOR','MPC')")
@RestController
@RequestMapping("/api/v1/task-requests")
public class TaskRequestController {

    private final TaskRequestService taskRequestService;

    public TaskRequestController(TaskRequestService taskRequestService) {
        this.taskRequestService = taskRequestService;
    }

    @GetMapping("/get")
    public ResponseEntity<String> getTaskRequests() {
        // Your logic to retrieve task requests
        return ResponseEntity.ok("Task requests");
    }


    @PostMapping("/addnewtask")
    public ResponseEntity<String> addNewTask(@RequestBody TaskRequest taskRequest) {
        // Your logic to add new task
        return ResponseEntity.ok("New task added");
    }

    // Add more endpoints for updating, deleting, etc.

    @GetMapping("/getAllnewtaskrequests")
    public ResponseEntity<List<ResponseNewTaskRequestDto>> getAllnewTaskRequests() {
        List<ResponseNewTaskRequestDto> responseDtos = taskRequestService.getAllNewTaskRequests();
        return ResponseEntity.ok(responseDtos);
    }

}
