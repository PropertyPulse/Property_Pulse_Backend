package com.example.demo.service;

import com.example.demo.dto.responseDto.ResponseValuationDTO;
import com.example.demo.entity.Property;
import com.example.demo.entity.PropertyOwner;
import com.example.demo.entity.PropertyType;
import com.example.demo.entity.ValuationReport;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.repository.ValuationReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class ValuationReportServiceImpl implements ValuationReportService
{


    private  final ValuationReportRepository valuationReportRepository;
    private  List<ResponseValuationDTO> responseValuationDTOList;

    private PropertyRepository propertyRepository;
    private  List<ValuationReport> valuationReportList;

    @Autowired
    public ValuationReportServiceImpl(ValuationReportRepository valuationReportRepository, PropertyRepository propertyRepository) {
        this.valuationReportRepository = valuationReportRepository;
        this.propertyRepository = propertyRepository;
    }


    @Override
    public List<ResponseValuationDTO> getValuationReports(String status) throws ResourceNotFoundException {


        if(valuationReportRepository.findByStatus(status).isEmpty())
        {
            throw new ResourceNotFoundException("No Valuation Reports Found");
        }
        else
        {   responseValuationDTOList =  new ArrayList<>();
            Property property;
            PropertyOwner propertyOwner;
            valuationReportList = valuationReportRepository.findByStatus(status);
            for(ValuationReport valuationReport : valuationReportList)
            {
                ResponseValuationDTO responseValuationDTO = new ResponseValuationDTO();
                property = propertyRepository.findById(valuationReport.getProperty().getId()).get();
                propertyOwner = property.getPropertyOwner();

                responseValuationDTO.setPropertyId(Integer.valueOf(property.getId()));
                responseValuationDTO.setType(property.getType());
                responseValuationDTO.setLocation(property.getLocation());
                responseValuationDTO.setTaskList(property.getTaskRequests());
                responseValuationDTO.setContact(propertyOwner.getTelephone());
                responseValuationDTO.setStatus(valuationReport.getStatus());
                responseValuationDTO.setFileLink(valuationReport.getPdfPath());
                responseValuationDTO.setDistrict(property.getDistrict());
                responseValuationDTO.setSpecialFacts(property.getSpecialFacts());
                responseValuationDTO.setPropertyownername(propertyOwner.getUser().getFirstname() +" "+propertyOwner.getUser().getLastname());

                if(property.getType() == PropertyType.LAND)
                {
                    responseValuationDTO.setHaveCrops(property.getHaveCrops());
                    responseValuationDTO.setCrops(property.getCrops());
                    responseValuationDTO.setLandSize(property.getLandSize());
                    responseValuationDTO.setAddress(property.getAddress());



                }
                else if(property.getType() == PropertyType.HOME)
                {

                }
                responseValuationDTOList.add(responseValuationDTO);
            }
        }

       return  responseValuationDTOList;
    };

}
