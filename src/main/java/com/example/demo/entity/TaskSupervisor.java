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
@Table(name = "tasksupervisor")
public class TaskSupervisor {

    @Id
    @Column(name = "id")
    private Integer id;


    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;


    private String address;
    private String district;
    private Integer phone;
    private String nic;
    private LocalDate dob;



    @OneToMany(mappedBy = "taskSupervisor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskRequest> taskRequests = new ArrayList<>();

//    @OneToMany(mappedBy = "taskSupervisor", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ReceivablePayment> receivablePayments = new ArrayList<>();


}
