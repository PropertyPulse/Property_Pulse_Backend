package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Valuation_Report")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValuationReport {

    @Id
    @Column(name = "id")
    private Integer id;


    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Property property;

    private String name;
    private String type;
    private String filePath;
}