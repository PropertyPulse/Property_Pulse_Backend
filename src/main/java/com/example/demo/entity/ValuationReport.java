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

@Table(name = "valuation_reports")
@Entity
public class ValuationReport {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long report_id;


    @Column(nullable = true)
    private    String fileName;



    @Column(nullable  =true)
    private String pdfPath;


    @Column(nullable = false,columnDefinition = "VARCHAR(20) DEFAULT 'pending'")
    private String  status;


    @Column
    private LocalDate requestedDate;


    @Column(nullable = true)
    private LocalDate submittedDate;

    @OneToOne
    @JoinColumn(name = "property_id")
    private Property property;


    public Long getId() {

        return this.report_id;
    }
}
