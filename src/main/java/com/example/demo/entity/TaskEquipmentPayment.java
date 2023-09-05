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
@Table(name = "Task_Equipment_Payment")
public class TaskEquipmentPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id") // This should match the name of the property ID column in your database
    private Property property;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskSupervisor_id") // This should match the name of the property ID column in your database
    private TaskSupervisor taskSupervisor;

    private Double amount;

    private String description;

    private LocalDate date;

    private boolean isPaid;


}
