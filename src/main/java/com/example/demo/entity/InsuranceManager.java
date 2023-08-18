package com.example.demo.entity;

import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "insurance_manager")
public class InsuranceManager {

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

