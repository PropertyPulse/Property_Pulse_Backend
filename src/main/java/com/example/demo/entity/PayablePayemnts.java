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
@Table(name = "payable_payemnts")
public class PayablePayemnts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;


    private Double amount;
    private String description;
    private LocalDate duedate;

    @Enumerated(EnumType.STRING)
    private PayablePaymentType type;

}
