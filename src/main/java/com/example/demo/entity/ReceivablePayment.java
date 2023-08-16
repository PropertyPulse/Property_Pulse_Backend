package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recievable_payemnts")
public class ReceivablePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "task_supervisor_id")
    private TaskSupervisor taskSupervisor;



    private Double amount;
    private String description;


    @Enumerated(EnumType.STRING)
    private RecievablePaymentType type;
    
}
