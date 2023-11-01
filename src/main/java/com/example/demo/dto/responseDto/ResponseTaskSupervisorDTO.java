package com.example.demo.dto.responseDto;




import com.example.demo.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTaskSupervisorDTO {
        private Integer id;
        private Integer userId; // This should match the user's ID
        private String address;
        private String nearestTown;
        private String district;
        private String contactNo;
        private String nic;
        private LocalDate dob;
        private Gender gender;
        private String firstName;
        private String lastName;


    }

