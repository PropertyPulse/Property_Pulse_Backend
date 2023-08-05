package com.example.demo.service;

import com.example.demo.auth.RegisterRequest;
import com.example.demo.dto.requestDto.RequestPo;
import com.example.demo.dto.responseDto.ResponsePo;
import com.example.demo.entity.PropertyOwner;
import com.example.demo.repository.PropertyOwnerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PropertyOwnerServiceImpl implements PropertyOwnerService {
    private final PropertyOwnerRepository propertyOwnerRepository;

    public PropertyOwnerServiceImpl(PropertyOwnerRepository propertyOwnerRepository) {
        this.propertyOwnerRepository = propertyOwnerRepository;
    }

    @Override
    public ResponsePo addPropertyOwner(RegisterRequest reqreg) {
        PropertyOwner propertyOwner = reqreg.getPropertyOwner();

        // Perform necessary operations with the propertyOwner object
        // For example, you can save it to the database using the repository

        PropertyOwner savedPropertyOwner = propertyOwnerRepository.save(propertyOwner);

        // Create a ResponsePo object if needed
        ResponsePo response = new ResponsePo();
        // Populate the response object with relevant data if required

        // Return the response object
        return response;
    }


}
