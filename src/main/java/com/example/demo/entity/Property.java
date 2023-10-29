package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "property")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "property_type")
public  class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<TaskRequest> taskRequests = new ArrayList<>();


    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)

    private List<RecivedPayment> payablePayemnts = new ArrayList<>();



   @Column(name = "accepted_status", columnDefinition = "boolean default false")
    private  boolean acceptedStatus;
//    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
//    private List<ReceivablePayment> receivablePayments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_owner_id")
    private PropertyOwner propertyOwner;

    private LocalDate accepted_date;
    private LocalDate returned_date;
    private LocalDate registered_date;

    @NotBlank(message = "Address is required")
    private String address;
    @Enumerated(EnumType.STRING)
    private PropertyType type;

    @NotBlank(message = "Location is required")
    private String location;
//    documents should be implemented

    @Column(nullable = false)
    private String district;
    private String duration;



    @Column(name = "want_insurance")
    private Boolean wantInsurance;

    // @Column(name = "property_owner")
    // private Integer propertyOwnerId;
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<TaskEquipmentPayment> equipmentPayments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_supervisor_id")
    private TaskSupervisor taskSupervisor;


    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> files;


    @OneToOne
    @JoinColumn(name = "valuation_report_id")
    private ValuationReport valuationReport;


}
