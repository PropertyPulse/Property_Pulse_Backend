package com.example.demo.dto.responseDto;

import com.example.demo.entity.Property;
import com.example.demo.entity.RecivedPaymentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReceivedPaymentDto {
    private Integer id;

    private Integer propertyId;

    private Double amount;
    private String description;

    private LocalDate receiveddate;

}
