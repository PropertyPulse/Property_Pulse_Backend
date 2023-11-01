package com.example.demo.service;

import com.example.demo.entity.Property;
import com.example.demo.entity.TaskSupervisor;
import com.example.demo.exception.ResourceNotFoundException;


import java.io.IOException;
import java.util.List;

public interface TopManagerService {

    List<TaskSupervisor> SelectedSupervisors(String address) throws IOException;

    void requestValuationandAcceptProperty(Integer propertyId) throws ResourceNotFoundException;


    List<Property> NewManagementRequests();



    void AssignTaskSupervisor(Long propertyId, Long taskSupervisorId) throws ResourceNotFoundException;
}
