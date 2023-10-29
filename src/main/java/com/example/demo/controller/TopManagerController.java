package com.example.demo.controller;

import com.example.demo.dto.requestDto.RequestFeedbackDTO;
import com.example.demo.dto.responseDto.ResponseValuationDTO;
import com.example.demo.entity.Property;
import com.example.demo.entity.TaskSupervisor;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.service.*;
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


    private final TopManagerService topManagerService;
    private final ValuationReportService valuationReportService;

    private final PropertyRepository propertyRepository;

    @Autowired
    public TopManagerController(FeedbackService feedbackService, TopManagerServiceImpl topManagerService, ValuationReportServiceImpl valuationReportService, PropertyRepository propertyRepository) {
        this.feedbackService = feedbackService;
//        this.geocodeController = geocodeController;


        this.topManagerService = topManagerService;
        this.valuationReportService = valuationReportService;
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('topmanager:read')")
    public ResponseEntity<String> get() {
        // Replace the following string with an actual object you want to return as JSON
        // You can create a class representing the response data and return an instance of that class.
        String responseData = "GET :: CUSTOMER";
        return ResponseEntity.ok(responseData);
    }


    @PostMapping("/give-a-feedback")
    @PreAuthorize("hasAuthority('topmanager:create')")
    public ResponseEntity<String> giveAFeedback(@RequestBody RequestFeedbackDTO requestFeedbackDTO) {

        try {
            feedbackService.addFeedback(requestFeedbackDTO);
            return ResponseEntity.ok("Feedback registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error registering feedback: " + e.getMessage());
        }
    }

    @GetMapping("/select-a-task-supervisor")
    @PreAuthorize("hasAuthority('topmanager:read')")
    public ResponseEntity<List<TaskSupervisor>> SelectedTaskSupervisor(@RequestParam String address) throws IOException {


        if (address != null) {
            List<TaskSupervisor> taskSupervisor = topManagerService.SelectedSupervisors(address);
            System.out.println("No issues at all");
            return ResponseEntity.ok(taskSupervisor);
        }

        return ResponseEntity.ok(null);


    }

    @GetMapping("/valuation-reports")
    public ResponseEntity<List<ResponseValuationDTO>> getValuationReports(
            @RequestParam(name = "status") String status
    ) throws ResourceNotFoundException {
        List<ResponseValuationDTO> reports;

        if (status != null) {
            reports = valuationReportService.getValuationReports(status);
        } else {
            // Handle invalid status parameter or return an error response
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(reports);

    }


    @GetMapping("/requestValuationandAcceptProperty")
    public ResponseEntity<String> requestValuationandAcceptProperty(@RequestParam(name = "propertyId") Long propertyId) throws ResourceNotFoundException {

        if (propertyId != null) {

            topManagerService.requestValuationandAcceptProperty(propertyId);
            return ResponseEntity.ok("Valuation Requested and Property Accepted");
        } else {
            // Handle invalid status parameter or return an error response
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/newmanagmentrequest")
    public ResponseEntity<List<Property>> newmanagementrequest() {
        try {
            List<Property> properties = topManagerService.NewManagementRequests();
            System.out.println("Hello, I am inside the controller");
            System.out.println("Number of properties: " + properties.size());

            // Return the list of properties in the response body
            return ResponseEntity.ok(properties);
        } catch (Exception e) {
            // Handle any exceptions and return a bad request response
            return ResponseEntity.badRequest().build();
        }
    }
    //    @GetMapping("/viewPendingProperties")
//    public List<Property> viewPendingProperties() {
//
//           if(propertyRepository.findByAccepted_Status(false).isEmpty())
//           {
//               return null;
//           }
//           else
//           {
//               return propertyRepository.findByAccepted_Status(false);
//           }
//    }




    @PostMapping("/Assign-a-task-supervisor")
    public ResponseEntity<String> AssignTaskSupervisor(@RequestParam(name = "propertyId") Long propertyId, @RequestParam(name = "taskSupervisorName") Long taskSupervisorId) throws ResourceNotFoundException {

        if (propertyId != null && taskSupervisorId != null) {

            topManagerService.AssignTaskSupervisor(propertyId, taskSupervisorId);
            return ResponseEntity.ok("Task Supervisor Assigned");
        } else {
            // Handle invalid status parameter or return an error response
            return ResponseEntity.badRequest().build();
        }

    }
}




