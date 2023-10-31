package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestMakeComplaintDto;
import com.example.demo.dto.responseDto.ResponseMakeComplaintDto;
import com.example.demo.entity.NewComplaint;
import com.example.demo.entity.PropertyOwner;
import com.example.demo.exception.UserException;
import com.example.demo.repository.ComplaintRepository;
import com.example.demo.repository.PropertyOwnerRepository;
import com.example.demo.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class MakeComplaintServiceImpl implements MakeComplaintService {
    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;
    private final PropertyOwnerRepository propertyOwnerRepository;

    public MakeComplaintServiceImpl(ComplaintRepository complaintRepository, UserRepository userRepository, PropertyOwnerRepository propertyOwnerRepository) {
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
        this.propertyOwnerRepository = propertyOwnerRepository;
    }

    @Override
    @Transactional
    public String makeNewComplaint(RequestMakeComplaintDto req) throws UserException {
        ResponseMakeComplaintDto responseMakeComplaintDto = new ResponseMakeComplaintDto();

        var user = userRepository.findByEmail(req.getUserEmail());
        PropertyOwner propertyOwner = propertyOwnerRepository.findById(user.get().getId()).orElse(null);

        NewComplaint complaint = new NewComplaint();
        complaint.setTitle(req.getTitle());
        complaint.setType(req.getType());
        complaint.setDescription(req.getDescription());
        complaint.setComplained_date(LocalDate.now());
        complaint.setStatus("PENDING");
        complaint.setComplainant(propertyOwner);

        var savedComplaint = complaintRepository.save(complaint);

        return "Complaint making was successful";
    }
}
