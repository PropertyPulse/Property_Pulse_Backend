package com.example.demo.entity;

import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "property_owner")
public class PropertyOwner  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "Firstname is required")
    private String firstname;
    @NotNull(message = "Lastname is required")
    private String lastname;
    @NotNull(message = "Address is required")
    private String address;
    @NotNull(message = "nic is required")
    private String nic;

    @Pattern(regexp = "^\\d{10}$", message = "invalid mobile number entered")
    private String telephone;
    private String district;
    private String gender;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
