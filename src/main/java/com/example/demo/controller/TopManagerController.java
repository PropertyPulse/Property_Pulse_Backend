package com.example.demo.controller;

import com.example.demo.dto.requestDto.RequestFeedbackDTO;
import com.example.demo.entity.TaskSupervisor;
import com.example.demo.repository.TaskSupervisorRepository;
import com.example.demo.service.FeedbackService;
import com.example.demo.service.TaskSupervisorServiceImpl;
import com.example.demo.service.TopManagerService;
import com.example.demo.service.TopManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tm")

@PreAuthorize("hasRole('TOPMANAGER')")
public class TopManagerController {

    private final FeedbackService feedbackService;
//    private  final GeocodeController    geocodeController;

    private   final TaskSupervisorRepository taskSupervisorRepository;

    private final TopManagerService topManagerService;

    @Autowired
    public TopManagerController(FeedbackService feedbackService, GeocodeController geocodeController, TaskSupervisorRepository taskSupervisorRepository, TopManagerServiceImpl topManagerService) {
        this.feedbackService = feedbackService;
//        this.geocodeController = geocodeController;
        this.taskSupervisorRepository = taskSupervisorRepository;

        this.topManagerService =  topManagerService;
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('topmanager:read')")
    public ResponseEntity<String> get(){
        // Replace the following string with an actual object you want to return as JSON
        // You can create a class representing the response data and return an instance of that class.
        String responseData = "GET :: CUSTOMER";
        return ResponseEntity.ok(responseData);
    }


    @PostMapping("/give-a-feedback")
    @PreAuthorize("hasAuthority('topmanager:create')")
    public ResponseEntity<String> giveAFeedback(@RequestBody RequestFeedbackDTO requestFeedbackDTO){

        try {
            feedbackService.addFeedback(requestFeedbackDTO);
            return ResponseEntity.ok("Feedback registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error registering feedback: " + e.getMessage());
        }
    }
    @GetMapping("/select-a-task-supervisor")
    @PreAuthorize("hasAuthority('topmanager:read')")
    public ResponseEntity<List<TaskSupervisor>> SelectedTaskSupervisor(@RequestBody String address ) throws IOException {


        if(address != null)
        {
            List<TaskSupervisor> taskSupervisor = topManagerService.SelectedSupervisors(address);
            System.out.println("No issues at all");
            return ResponseEntity.ok(taskSupervisor);
        }

        return ResponseEntity.ok(null);




    }



}