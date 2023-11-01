package com.example.demo.entity;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("HOME")
public class Home  extends Property{
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
}
