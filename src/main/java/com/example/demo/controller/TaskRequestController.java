package com.example.demo.controller;

import com.example.demo.dto.requestDto.RequestUpdateRequestStatusDto;
import com.example.demo.dto.responseDto.*;
import com.example.demo.exception.UserException;
import com.example.demo.service.TaskRequestService;
import org.springframework.http.HttpStatus;
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
        List<ResponseTaskApprovalsDto> taskApprovals = taskRequestService.getTaskApprovals(email);
        return ResponseEntity.ok(taskApprovals);
    }

    @PutMapping("/task-approvals/update-mc-status")
    @PreAuthorize("hasAuthority('tasksupervisor:update')")
    public ResponseEntity<String> updateRequestStatus(@RequestBody RequestUpdateRequestStatusDto req){

        try{

            Boolean isStatusUpdated = taskRequestService.updateManpowerCompanyResponse(req.getTaskId(), req.getRequestStatus());
            if (isStatusUpdated) {
                return ResponseEntity.ok("Status successfully updated");
            }else {
                return ResponseEntity.badRequest().body("Failed to update the request status at the moment");
            }

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error occurred");
        }
    }

}
