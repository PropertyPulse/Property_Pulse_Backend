package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestAddNewPropertyDto;
import com.example.demo.dto.responseDto.ResponseAddNewPropertyDto;
import com.example.demo.dto.responseDto.ResponseGetAllPropertiesByUserDto;
import com.example.demo.entity.Property;
import com.example.demo.entity.PropertyOwner;
import com.example.demo.exception.UserException;
import com.example.demo.repository.PropertyOwnerRepository;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyOwnerRepository propertyOwnerRepository;
    private final UserRepository userRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository, UserRepository userRepository, PropertyOwnerRepository propertyOwnerRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
        this.propertyOwnerRepository = propertyOwnerRepository;
    }

    @Override
    @Transactional
    public ResponseAddNewPropertyDto addNewProperty(RequestAddNewPropertyDto req) throws UserException {
        ResponseAddNewPropertyDto responseAddNewPropertyDto = new ResponseAddNewPropertyDto();

        var user = userRepository.findByEmail(req.getProperty_owner_email());

        PropertyOwner propertyOwner = propertyOwnerRepository.findById(user.get().getId()).orElse(null);

        // var propertyOwner = userRepository.findByEmail(req.getProperty_owner_email().toString());
        System.out.println(req.getChecklist());
        Property property = new Property();
        property.setType(req.getType());
        property.setAddress(req.getAddress());
        property.setDistrict(req.getDistrict());
        property.setLocation(req.getLocation());
        property.setDuration(req.getDuration());
        property.setStories(req.getStories());
        property.setBathrooms(req.getBathrooms());
        property.setBedrooms(req.getBedrooms());
        property.setDiningRooms(req.getDining_rooms());
        property.setLivingRooms(req.getLiving_rooms());
        property.setHaveSpecialRooms(req.getHave_special_rooms());
        property.setSpecialRooms(req.getSpecial_rooms());
        property.setLandSize(req.getLand_size());
        property.setHaveCrops(req.getHave_crops());
        property.setCrops(req.getCrops());
        property.setSpecialFacts(req.getSpecial_facts());
        property.setRegisteredStatus("PENDING");
        property.setRegistered_date(LocalDate.now());
        property.setWantInsurance(req.getWant_insurance());
        property.setPropertyOwner(propertyOwner);
        property.setChecklist(req.getChecklist());
        // property.setPropertyOwner(user.get().getId());

        var savedProperty = propertyRepository.save(property);

        responseAddNewPropertyDto.setId(savedProperty.getId());

        return responseAddNewPropertyDto;
    }


    @Override
    public List<ResponseGetAllPropertiesByUserDto> getAllPropertiesByUser(String email) {
        Optional<User> propertyOwner = userRepository.findByEmail(email);

        List<Property> properties = propertyRepository.findByPropertyOwnerId(propertyOwner.get().getPropertyOwner().getId());

        List<ResponseGetAllPropertiesByUserDto> responseDtos = properties
                .stream()
                .map(this::convertToDtos)
                .collect(Collectors.toList());

        return responseDtos;
    }

    private ResponseGetAllPropertiesByUserDto convertToDtos(Property property) {
        ResponseGetAllPropertiesByUserDto responseDto = new ResponseGetAllPropertiesByUserDto();

        responseDto.setPropertyId(property.getId());
        responseDto.setPropertyType(property.getType().toString());
        responseDto.setAddress(property.getAddress());
        responseDto.setDistrict(property.getDistrict());
        responseDto.setTaskSupervisor(property.getTaskSupervisor());
        responseDto.setRegisteredStatus(property.getRegisteredStatus());
        responseDto.setRegisteredDate(property.getRegistered_date());
        responseDto.setDuration(property.getDuration());

        return responseDto;
    }


    @Override
    public ResponseAddNewPropertyDto getPropertyById(Integer id) {
        Optional<Property> propertyOptional = propertyRepository.findById(id);

        Property property = propertyOptional.get();

        ResponseAddNewPropertyDto responseDto = convertToDto(property);

        return responseDto;
    }

    private ResponseAddNewPropertyDto convertToDto(Property property) {
        ResponseAddNewPropertyDto dto = new ResponseAddNewPropertyDto();

        dto.setType(property.getType());
        dto.setDuration(property.getDuration());
        dto.setAddress(property.getAddress());
        dto.setDistrict(property.getDistrict());
        dto.setAccepted_date(property.getAccepted_date());
        dto.setProperty_owner(property.getPropertyOwner().getId());
        dto.setRegistered_date(property.getRegistered_date());

        return dto;
    }

}
