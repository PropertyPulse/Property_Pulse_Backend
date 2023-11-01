package com.example.demo.controller;


import com.example.demo.dto.responseDto.ResponseValuationDTO;
import com.example.demo.dto.responseDto.ResponseValuationPendingDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.PropertyOwnerRepository;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.repository.ValuationReportRepository;
import com.example.demo.service.ValuationReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ve")

@PreAuthorize("hasRole('VALUATIONEXPERT')")


public class ValuationExpertController {


       private final ValuationReportService valuationReportService;


       private final PropertyRepository propertyRepository;
       private final ValuationReportRepository valuationReportRepository;

       private final PropertyOwnerRepository propertyOwnerRepository;
       @Autowired
         public ValuationExpertController(ValuationReportService valuationReportService, PropertyRepository propertyRepository, ValuationReportRepository  valuationReportRepository, PropertyOwnerRepository propertyOwnerRepository) {
              this.valuationReportService = valuationReportService;
           this.propertyRepository = propertyRepository;
           this.valuationReportRepository = valuationReportRepository;
           this.propertyOwnerRepository = propertyOwnerRepository;
       }

    @PostMapping("/uploadValuationReport")
    @PreAuthorize("hasAuthority('valuationexpert:create')")
    public String uploadValuationReport(){
 return null;
    }


    @GetMapping("/viewPendingReports")
    @PreAuthorize("hasAuthority('valuationexpert:read')")
    public ResponseEntity< List<ResponseValuationPendingDTO>> viewReports(@RequestParam String status) throws ResourceNotFoundException {

          List<ResponseValuationDTO> valuationReportList =   valuationReportService.getValuationReports(status.toLowerCase());
          List<ResponseValuationPendingDTO> responseValuationPendingDTOList = new ArrayList<>();
            if(valuationReportList.isEmpty())
            {
                throw new ResourceNotFoundException("No Valuation Reports Found");
            }
            else
            {
                for(ResponseValuationDTO responseValuationDTO : valuationReportList)
                {
                    ResponseValuationPendingDTO responseValuationPendingDTO = new ResponseValuationPendingDTO();
                    responseValuationPendingDTO.setPropertyId(responseValuationDTO.getPropertyId());
                    responseValuationPendingDTO.setType(responseValuationDTO.getType());
                    responseValuationPendingDTO.setLocation(responseValuationDTO.getLocation());
                    responseValuationPendingDTO.setContact(responseValuationDTO.getContact());
                    responseValuationPendingDTO.setFirstname(propertyOwnerRepository.findById(propertyRepository.findById(responseValuationDTO.getPropertyId()).get().getPropertyOwner().getId()).get().getUser().getFirstname());
                    responseValuationPendingDTO.setLastname(propertyOwnerRepository.findById(propertyRepository.findById(responseValuationDTO.getPropertyId()).get().getPropertyOwner().getId()).get().getUser().getLastname());
                    responseValuationPendingDTO.setRequestedDate(valuationReportRepository.findById((responseValuationDTO.getPropertyId())).get().getRequestedDate());
                    responseValuationPendingDTO.setPropertyAddress(responseValuationDTO.getAddress());

                    responseValuationPendingDTOList.add(responseValuationPendingDTO);
                }
            }

return  ResponseEntity.ok().body(responseValuationPendingDTOList);

    }
//
//    @GetMapping("/viewSubmittedReports")
//    @PreAuthorize("hasAuthority('valuationexpert:read')")
//    public String viewSubmittedReports(){
//
//    }

}

