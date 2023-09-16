package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestAddNewPropertyDto;
import com.example.demo.dto.responseDto.ResponseAddNewPropertyDto;
import com.example.demo.entity.Property;
import com.example.demo.entity.PropertyOwner;
import com.example.demo.exception.UserException;
import com.example.demo.repository.PropertyOwnerRepository;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
    public String addNewProperty(RequestAddNewPropertyDto req) throws UserException {
        ResponseAddNewPropertyDto responseAddNewPropertyDto = new ResponseAddNewPropertyDto();

        var user = userRepository.findByEmail(req.getProperty_owner_email());

        PropertyOwner propertyOwner = propertyOwnerRepository.findById(user.get().getId()).orElse(null);

        // var propertyOwner = userRepository.findByEmail(req.getProperty_owner_email().toString());

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
        // property.setPropertyOwner(user.get().getId());

        var savedProperty = propertyRepository.save(property);

        return "Property added successfully";
    }



}
