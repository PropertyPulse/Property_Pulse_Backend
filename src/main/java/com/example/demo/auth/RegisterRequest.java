package com.example.demo.auth;

import com.example.demo.entity.Gender;
import com.example.demo.entity.PropertyOwner;
import com.example.demo.token.Token;
import com.example.demo.user.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {



    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String address;
    private String nic;
    private String gender;
    private String telephone;
    private String district;




//    private PropertyOwner propertyOwner;

}
