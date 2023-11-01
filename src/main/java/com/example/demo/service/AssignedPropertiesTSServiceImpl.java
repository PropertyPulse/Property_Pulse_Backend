package com.example.demo.service;
import com.example.demo.dto.responseDto.ResponseAssignedPropertiesTSDto;
import com.example.demo.entity.*;
import com.example.demo.repository.AssignedPropertyRepositoryTS;
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

        List<Property> properties = propertyRepository.findBytaskSupervisor_id(taskSupervisor.get().getId());

        List<ResponseAssignedPropertiesTSDto> assignedProperties = properties.stream()
                .filter(property -> property.getAssignStage().equals("ToBeAssigned") && property.getVisitStatus().equals("true"))
                .map(this::mapPropertyToDto)
                .collect(Collectors.toList());

        return assignedProperties;
    }

    private ResponseAssignedPropertiesTSDto mapPropertyToDto (Property p) {
        ResponseAssignedPropertiesTSDto dto = new ResponseAssignedPropertiesTSDto();

        dto.setPropertyId(p.getId());
        dto.setPropertyOwner(p.getPropertyOwner().getUser().getFirstname());
        dto.setPropertyType(p.getType().toString());
        dto.setAddress(p.getAddress());
        return dto;
    }
}