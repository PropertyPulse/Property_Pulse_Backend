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
@Table(name = "new_complaints")
public class NewComplaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_id")
    private  long complaint_id;

    @ManyToOne
    @JoinColumn(name = "complainant_id")
    private PropertyOwner complainant;

    private String type;


    @Column(name = "description", length = 1000)
    private String description;


    @Column(name = "reason", length = 500)
    private String reason;

    private String title;



    @Column(name = "issolved")
    private boolean issolved = false;

    private LocalDate complained_date;
    private String response;
    private String status;
}
