package com.example.demo.service;

import com.example.demo.dto.responseDto.ResponseAssignedPropertiesTSDto;
import com.example.demo.entity.*;
import com.example.demo.repository.AssignedPropertyRepositoryTS;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AssignedPropertiesTSServiceImpl implements AssignedPropertiesTSService {
    private final AssignedPropertyRepositoryTS assignedPropertyRepositoryTS;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    public AssignedPropertiesTSServiceImpl(AssignedPropertyRepositoryTS assignedPropertyRepositoryTS, UserRepository userRepository, PropertyRepository propertyRepository) {
        this.assignedPropertyRepositoryTS = assignedPropertyRepositoryTS;
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
    }


    @Override
    public List<ResponseAssignedPropertiesTSDto> assignedPropertiesTS(String email) {
        Optional<User> taskSupervisor = userRepository.findByEmail(email);
        List<ResponseAssignedPropertiesTSDto> e = new ArrayList<>();

        List<Property> assignedProperties = propertyRepository.findBytaskSupervisor_id(taskSupervisor.get().getId());
        for (Property p : assignedProperties) {
            System.out.println(p.getType());
            ResponseAssignedPropertiesTSDto dto = new ResponseAssignedPropertiesTSDto();

            dto.setPropertyId(p.getId());
            dto.setPropertyOwner(p.getPropertyOwner().getUser().getFirstname());
            dto.setPropertyType(p.getType().toString());

            e.add(dto);
        }
//        List<ResponseAssignedPropertiesTSDto> responseAssignedPropertiesTSDtos = assignedProperties.stream()
//                .map(this::mapUserToDto)
//                .collect(Collectors.toList());
        return e;
    }

//    private ResponseAssignedPropertiesTSDto mapUserToDto (Property property) {
//        ResponseAssignedPropertiesTSDto dto = new ResponseAssignedPropertiesTSDto();
//
//        dto.setPropertyId(property.getId());
//        dto.setPropertyOwner(property.getPropertyOwner().getUser().getFirstname());
//        dto.setPropertyType(property.getType().toString());
//        return dto;
//    }
}