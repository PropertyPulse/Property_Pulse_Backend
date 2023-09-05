package com.example.demo.entity;

import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "manpower_company")
public class ManPowerCompany {
//    name,company location , operational location,contact
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private String name;
    private String companyLocation;
    private String operationalLocation;
    private String contactnumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToMany(mappedBy = "manPowerCompany", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Employee> employees;



}
