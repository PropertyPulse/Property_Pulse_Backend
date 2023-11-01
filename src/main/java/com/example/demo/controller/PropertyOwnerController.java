package com.example.demo.controller;

import com.example.demo.dto.requestDto.ComplaintRequestDTO;
import com.example.demo.entity.Complaint;
import com.example.demo.entity.Feedback;
import com.example.demo.repository.ComplaintRepository;
import com.example.demo.repository.FeedbackRepository;
import com.example.demo.service.PropertyOwnerService;
import com.example.demo.service.PropertyOwnerServiceImpl;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.demo.config.JwtService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/po")

@PreAuthorize("hasRole('PROPERTYOWNER')")

public class PropertyOwnerController {



      private ComplaintRepository  complaintRepository;

      private FeedbackRepository feedbackRepository;;


      @Autowired
      private PropertyOwnerServiceImpl poservice;

        @Autowired
        private JwtService jwtService;

        @Autowired
        private UserRepository userRepository;

      @Autowired
    public PropertyOwnerController(ComplaintRepository complaintRepository, FeedbackRepository feedbackRepository) {
        this.complaintRepository = complaintRepository;
        this.feedbackRepository = feedbackRepository;
    }
    @GetMapping
    @PreAuthorize("hasAuthority('propertyowner:read')")
    public ResponseEntity<String> get(){
        // Replace the following string with an actual object you want to return as JSON
        // You can create a class representing the response data and return an instance of that class.
        String responseData = "GET :: propertyowner";
        return ResponseEntity.ok(responseData);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('propertyowner:create')")
    public String post(){
        return "POST :: propertyowner";
    }

    @PutMapping
    @PreAuthorize("hasAuthority('propertyowner:update')")
    public String put(){
        return "PUT :: propertyowner";
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('propertyowner:delete')")
    public String delete(){
        return "DELETE :: propertyowner";
    }
    @PostMapping("/addComplaint")
    @PreAuthorize("hasAuthority('propertyowner:create')")
    public ResponseEntity<?> addComplaint(@RequestBody ComplaintRequestDTO complaint) {



           poservice.addComplaint(complaint);

        return ResponseEntity.ok("Successfully added complaint");
    }

//    public List<Feedback> findFeedbackByComplainantId(Long complainantId) {
//        List<Complaint> complaints = complaintRepository.findBycomplainant_id(complainantId);
//
//        // Extract complaintIds from complaints
//        List<Long> complaintIds = complaints.streams.map(Complaint:getcomplaint_id).collect(Collectors.toList());
//
//        // Search for feedback entries with matching complaintIds
//        return feedbackRepository.findBycomplaint_id(complaintIds);
//    }

}


