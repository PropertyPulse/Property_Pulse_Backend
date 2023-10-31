package com.example.demo.entity;

import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "property_owner")
public class PropertyOwner  {

    @Id
    @Column(name = "id")
    private Integer id;


    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @NotNull(message = "Address is required")
    private String address;
    @NotNull(message = "nic is required")
    private String nic;

    @Pattern(regexp = "^\\d{10}$", message = "invalid mobile number entered")
<<<<<<< HEAD
    private String Telephone;
=======
    private String telephone;
>>>>>>> 7ce746af198b1f9077a84d673befbd1ef13e1fcd
    private String district;
    private String gender;

        @OneToMany(mappedBy = "propertyOwner")
        @JsonManagedReference
    private  List<Property> properties;






}
