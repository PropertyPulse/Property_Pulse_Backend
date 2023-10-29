package com.example.demo.entity;

import com.example.demo.user.User;
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
    private String contactNo;
    private String nic;
    private LocalDate dob;
    private Gender gender;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Feedback> feedbackList = new ArrayList<>();

}

