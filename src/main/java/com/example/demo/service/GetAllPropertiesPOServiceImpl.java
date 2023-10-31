package com.example.demo.service;

import com.example.demo.dto.responseDto.ResponseGetAllPropertiesByUserDto;
import com.example.demo.entity.Property;
import com.example.demo.repository.GetPropertiesRepository;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GetAllPropertiesPOServiceImpl implements GetAllPropertiesPOService {
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final GetPropertiesRepository getPropertiesRepository;

    public GetAllPropertiesPOServiceImpl(PropertyRepository propertyRepository, UserRepository userRepository, GetPropertiesRepository getPropertiesRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
        this.getPropertiesRepository = getPropertiesRepository;
    }

    @Override
    public List<ResponseGetAllPropertiesByUserDto> getAllPropertiesPO(String email) {
        Optional<User> propertyOwner = userRepository.findByEmail(email);
        List<ResponseGetAllPropertiesByUserDto> propertyList = new ArrayList<>();

        List<Property> allProperties = propertyRepository.findByPropertyOwnerId(propertyOwner.get().getId());
        for (Property p : allProperties) {
            System.out.println(p.getType());
            ResponseGetAllPropertiesByUserDto dto = new ResponseGetAllPropertiesByUserDto();

            dto.setPropertyId(p.getId());
            dto.setAddress(p.getAddress());
            dto.setPropertyType(p.getType().toString());
            dto.setDistrict(p.getDistrict());

            propertyList.add(dto);
        }

        return propertyList;
    }
}
