package com.example.demo.dto.responseDto;

import com.example.demo.entity.Property;
import com.example.demo.entity.PropertyType;
import com.example.demo.entity.ValuationReport;
import lombok.Data;

@Data
public class ValuationReportDTO {



    private int  propertyID;
    private String status;

    private String location;


    private PropertyType propertyType;

    private String pdfpath;
    public ValuationReportDTO(ValuationReport valuationReport,Property property)
    {

        this.propertyID =   property.getId();
        this.status  =  valuationReport.getStatus();
        this.location    =  property.getLocation();
        this.propertyType  =   property.getType();
        this.pdfpath  =  valuationReport.getPdfPath();

    }
}
