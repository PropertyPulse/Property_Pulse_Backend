package com.example.demo.service;


import com.example.demo.dto.responseDto.ResponsePropertiesToBeManagedDto;
import com.example.demo.entity.*;
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
        List<ResponsePropertiesToBeManagedDto> e = new ArrayList<>();

        List<Property> propertiesToBeManaged = propertyRepository.findByTaskSupervisorIdAndAssignStage(
                taskSupervisor.get().getId(), "ToBeManaged");
            for (Property p : propertiesToBeManaged) {
            System.out.println(p.getType());
            ResponsePropertiesToBeManagedDto dto = new ResponsePropertiesToBeManagedDto();

            dto.setPropertyId(p.getId());
            dto.setPropertyOwnerName(p.getPropertyOwner().getUser().getFirstname());
            dto.setAddress(p.getAddress());
            e.add(dto);
        }
        return e;
    }

}
