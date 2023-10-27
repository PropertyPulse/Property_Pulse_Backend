package com.example.demo.dto.requestDto;

import com.example.demo.entity.ComplaintCategory;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestMakeComplaintDto {
    private String title;
    private String type;
    private String description;
    private LocalDate complainedDate;
    private String status;
    private String userEmail;
}
