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
@Table(name = "top_manager")
public class TopManager {
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


}

