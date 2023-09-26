package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @Column(name = "id")
    private Integer id;


    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

}
