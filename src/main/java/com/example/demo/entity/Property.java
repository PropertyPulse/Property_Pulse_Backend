package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "property")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<TaskRequest> taskRequests = new ArrayList<>();


    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<PayablePayemnts> payablePayemnts = new ArrayList<>();

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<ReceivablePayment> receivablePayments = new ArrayList<>();




    private LocalDate accepted_date;
    private LocalDate returned_date;

    private String address;
    @Enumerated(EnumType.STRING)
    private PropertyType type;

    private String location;
//    documents should be implemented



}
