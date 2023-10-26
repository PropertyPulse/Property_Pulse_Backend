package com.example.demo.controller;

import com.example.demo.dto.responseDto.*;
import com.example.demo.exception.UserException;
import com.example.demo.service.TaskRequestService;
import com.example.demo.service.TaskSupervisorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAnyRole('TASKSUPERVISOR','MPC')")
@RestController
@RequestMapping("/api/v1/task-requests")
public class TaskRequestController {

    private final TaskRequestService taskRequestService;
    private final TaskSupervisorService taskSupervisorService;

    public TaskRequestController(TaskRequestService taskRequestService, TaskSupervisorService taskSupervisorService) {
        this.taskRequestService = taskRequestService;
        this.taskSupervisorService = taskSupervisorService;
    }

    @GetMapping("/get")
    public ResponseEntity<String> getTaskRequests() {
        return ResponseEntity.ok("Task requests");
    }


    @GetMapping("/getAllnewtaskrequests")
    public ResponseEntity<List<ResponseNewTaskRequestDto>> getAllnewTaskRequests() {
        List<ResponseNewTaskRequestDto> responseDtos = taskRequestService.getAllNewTaskRequests();
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/task-approvals")
    @PreAuthorize("hasAuthority('tasksupervisor:read')")
    public ResponseEntity<List<ResponseTaskApprovalsDto>> getTaskApprovals(@RequestParam("email") String email) throws UserException {
        List<ResponseTaskApprovalsDto> taskApprovals = taskSupervisorService.getTaskApprovals(email);
        return ResponseEntity.ok(taskApprovals);
    }

}
