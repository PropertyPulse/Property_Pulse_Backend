package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
    private String task;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Float cost;
    private String ManpowerCompany;
    private String ManpowerCompanyRequestStatus;
    private String contactPersonName;
    private String contactPersonTelNo;
    private Integer estimatedPrice;
    private Integer noOfWorkers;
    private Integer timeInDays;

}
