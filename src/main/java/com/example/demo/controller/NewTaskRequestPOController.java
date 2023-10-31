package com.example.demo.controller;

import com.example.demo.dto.requestDto.RequestAddNewTaskRequestDto;
import com.example.demo.exception.UserException;
import com.example.demo.service.NewTaskRequestPOService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasRole('PROPERTYOWNER')")
@RestController
@RequestMapping("/api/v1/newTaskRequest")
public class NewTaskRequestPOController {
    public final NewTaskRequestPOService newTaskRequestPOService;

    public NewTaskRequestPOController( NewTaskRequestPOService newTaskRequestPOService) {
        this.newTaskRequestPOService = newTaskRequestPOService;
    }

    @PostMapping("/addNewRequestedTask")
    @PreAuthorize("hasAuthority('propertyowner:create')")
    public ResponseEntity<String> addNewRequestedTask(@RequestBody RequestAddNewTaskRequestDto req) throws UserException {
        return ResponseEntity.ok(newTaskRequestPOService.addNewTaskRequest(req));
    }
}
