package com.example.demo.service;

import com.example.demo.entity.Property;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdatePropertyVisitStatusServiceImpl implements UpdatePropertyVisitStatusService {

    private final PropertyRepository propertyRepository;

    public UpdatePropertyVisitStatusServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public Boolean updateVisitStatus(int propertyId, boolean visitStatus) {
        Optional<Property> propertyOptional = propertyRepository.findById(propertyId);

        if (propertyOptional.isPresent()) {
            Property property = propertyOptional.get();
            property.setVisitStatus(visitStatus);
            propertyRepository.save(property); // Update the property's visit status
            return true; // Update successful
        }

        return false; // Property with the given ID not found
    }
}


