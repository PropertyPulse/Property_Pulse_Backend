package com.example.demo.controller;

import com.example.demo.dto.requestDto.RequestMakeComplaintDto;
import com.example.demo.exception.UserException;
import com.example.demo.service.MakeComplaintService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('PROPERTYOWNER')")
@RestController
@RequestMapping("/api/v1/complaint")
public class ComplaintController {
    private final MakeComplaintService makeComplaintService;

    public ComplaintController(MakeComplaintService makeComplaintService) {
        this.makeComplaintService = makeComplaintService;
    }

    @PostMapping("/makeNewComplaint")
    @PreAuthorize("hasAuthority('propertyowner:create')")
    public ResponseEntity<String> makeNewComplaint(@RequestBody RequestMakeComplaintDto req) throws UserException {
        return ResponseEntity.ok(makeComplaintService.makeNewComplaint(req));
    }
}
