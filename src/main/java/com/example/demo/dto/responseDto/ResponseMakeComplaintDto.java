package com.example.demo.dto.responseDto;

import com.example.demo.entity.ComplaintCategory;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseMakeComplaintDto {
    private String title;
    private String type;
    private String description;
    private LocalDate complainedDate;
    private String status;
    private String userEmail;
}
