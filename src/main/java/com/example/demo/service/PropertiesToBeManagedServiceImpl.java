package com.example.demo.service;


import com.example.demo.dto.responseDto.ResponsePropertiesToBeManagedDto;
import com.example.demo.entity.*;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PropertiesToBeManagedServiceImpl implements PropertiesToBeManagedService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public PropertiesToBeManagedServiceImpl (PropertyRepository propertyRepository, UserRepository userRepository, UserRepository userRepository1){
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository1;
    }

    @Override
    public List<ResponsePropertiesToBeManagedDto> propertiesToBeManaged(String email){

        Optional<User> taskSupervisor = userRepository.findByEmail(email);
//        List<ResponsePropertiesToBeManagedDto> e = new ArrayList<>();
        List<Property> properties = propertyRepository.findByTaskSupervisorId(taskSupervisor.get().getTaskSupervisor().getId());

//        for (Property property : properties) {
//            List<Task> tasks = taskRepository.findByPropertyId(property.getId());
//        }

        List<ResponsePropertiesToBeManagedDto> responsePropertiesToBeManagedDto = properties.stream()
                .filter(property -> property.getAssignStage().equals("ToBeManaged") && property.getVisitStatus().equals("false"))
                .map(this::mapPropertyToDto)
                .collect(Collectors.toList());

//        List<Property> propertiesToBeManaged = propertyRepository.findByTaskSupervisorIdAndAssignStage(
//                taskSupervisor.get().getTaskSupervisor().getId(), "ToBeManaged");
//            for (Property p : propertiesToBeManaged) {
//            System.out.println(p.getType());
//            ResponsePropertiesToBeManagedDto dto = new ResponsePropertiesToBeManagedDto();
//
//            dto.setPropertyId(p.getId());
//            dto.setPropertyOwnerName(p.getPropertyOwner().getUser().getFirstname());
//            dto.setAddress(p.getAddress());
//            dto.setPropertyType(p.getType().toString());
//            e.add(dto);
//                System.out.println("Property ID: " + p.getId());
//                System.out.println("Managing State: " + p.getAssignStage());
//                System.out.println("Task Supervisor ID: " + p.getTaskSupervisor().getId());

        return responsePropertiesToBeManagedDto;
    }

    private ResponsePropertiesToBeManagedDto mapPropertyToDto(Property p) {

        ResponsePropertiesToBeManagedDto dto = new ResponsePropertiesToBeManagedDto();

            dto.setPropertyId(p.getId());
            dto.setPropertyOwnerName(p.getPropertyOwner().getUser().getFirstname());
            dto.setAddress(p.getAddress());
            dto.setPropertyType(p.getType().toString());
            dto.setPriceListStatus(p.getPriceListStatus());
            dto.setLegalContractStatus(p.getLegalContractStatus());

        return dto;
    }

    @Override
    public List<ResponsePropertiesToBeManagedDto> propertiesToBeManagedVisited(String email){

        Optional<User> taskSupervisor = userRepository.findByEmail(email);

        List<Property> properties = propertyRepository.findByTaskSupervisorId(taskSupervisor.get().getTaskSupervisor().getId());

        List<ResponsePropertiesToBeManagedDto> responsePropertiesToBeManagedDto = properties.stream()
                .filter(property -> property.getAssignStage().equals("ToBeManaged") && property.getVisitStatus().equals("true"))
                .map(this::mapPropertyToDto)
                .collect(Collectors.toList());
        return responsePropertiesToBeManagedDto;
    }

}
