package com.example.demo.entity;

import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "task_supervisor")
public class TaskSupervisor {

    @Id
    @Column(name = "id")
    private Integer id;


    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;


    private String address;
    private String nearestTown;
    private String district;
    private String contactNo;
    private String nic;
    private LocalDate dob;
    private Gender gender;



    @OneToMany(mappedBy = "taskSupervisor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskRequest> taskRequests = new ArrayList<>();

//    @OneToMany(mappedBy = "taskSupervisor", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ReceivablePayment> receivablePayments = new ArrayList<>();




    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    @JsonManagedReference
    private  List<Property> properties;
}
