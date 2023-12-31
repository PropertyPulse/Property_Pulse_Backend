package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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



    @ElementCollection
    @CollectionTable(name = "property_checklist") // Specify the name of the table for the collection
    @Column(name = "checklist_item") // Specify the column name for the strings
    private List<String> checklist;



    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<TaskRequest> taskRequests = new ArrayList<>();


    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)

    private List<RecivedPayment> payablePayemnts = new ArrayList<>();



   @Column(name = "accepted_status", columnDefinition = "boolean default false")
    private  boolean acceptedStatus;
//    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
//    private List<ReceivablePayment> receivablePayments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_supervisor_id")
    private TaskSupervisor taskSupervisor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_owner_id")
    @JsonBackReference
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

    @Column (name = "assign_stage")
    private String assignStage;

    @Column(nullable = false)
    private String district;
    private String duration;
    private Integer stories;
    private Integer bedrooms;

    @Column(name = "living_rooms")
    private Integer livingRooms;

    private Integer bathrooms;

    @Column(name = "dining_rooms")
    private Integer diningRooms;

    @Column(name = "have_special_rooms")
    private String haveSpecialRooms;

    @Column(name = "special_rooms")
    private String specialRooms;

    @Column(name = "land_size")
    private Double landSize;

    @Column(name = "have_crops")
    private String haveCrops;

    private String crops;

    @Column(name = "special_facts")
    private String specialFacts;

    @Column(name = "registered_status")
    private String registeredStatus;

    @Column(name = "visit_status")
    private String visitStatus;


    @Column (name = "price_list_status")
    private String priceListStatus;

    @Column (name = "legal_contract_status")
    private String legalContractStatus;

//    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL)
//    @PrimaryKeyJoinColumn
//    private FileData document;
//
//    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL)
//    @PrimaryKeyJoinColumn
//    private ImageData imageData;






    @Column(name = "want_insurance")
    private Boolean wantInsurance;




    // @Column(name = "property_owner")
    // private Integer propertyOwnerId;
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<TaskEquipmentPayment> equipmentPayments = new ArrayList<>();




    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> files;


    @OneToOne
    @JoinColumn(name = "valuation_report_id")
    private ValuationReport valuationReport;

//    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL)
//    @PrimaryKeyJoinColumn
//    private ValuationReport valuationReport;


//    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<File> files = new HashSet<>();

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private FileData document;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ImageData imageData;


}
