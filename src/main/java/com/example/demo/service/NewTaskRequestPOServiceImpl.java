package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestAddNewTaskRequestDto;
import com.example.demo.dto.responseDto.ResponseAddNewTaskRequestDto;
import com.example.demo.entity.NewTaskRequestPO;
import com.example.demo.entity.Property;
import com.example.demo.exception.UserException;
import com.example.demo.repository.NewTaskRequestRepository;
import com.example.demo.repository.PropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NewTaskRequestPOServiceImpl implements NewTaskRequestPOService {
    private final NewTaskRequestRepository newTaskRequestRepository;
    private final PropertyRepository propertyRepository;

    public NewTaskRequestPOServiceImpl(NewTaskRequestRepository newTaskRequestRepository, PropertyRepository propertyRepository) {
        this.newTaskRequestRepository = newTaskRequestRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    @Transactional
    public String addNewTaskRequest(RequestAddNewTaskRequestDto req) throws UserException {
        ResponseAddNewTaskRequestDto responseAddNewTaskRequestDto = new ResponseAddNewTaskRequestDto();

        var property = propertyRepository.findById(req.getPropertyId());
        Property propertyId = propertyRepository.findById(property.get().getId()).orElse(null);

        NewTaskRequestPO taskRequest = new NewTaskRequestPO();
        taskRequest.setProperty(propertyId);
        taskRequest.setTask_description(req.getTaskDescription());
        taskRequest.setFrequency(req.getFrequency());
        taskRequest.setProposed_start_date(req.getProposedStartDate());
        taskRequest.setAccepted_status(req.getAcceptedStatus());

        var savedRequestedTask = newTaskRequestRepository.save(taskRequest);
        return "Requested task added successfully";
    }
}
