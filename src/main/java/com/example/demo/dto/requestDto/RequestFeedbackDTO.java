package com.example.demo.dto.requestDto;



import lombok.Data;

import java.time.LocalDate;

@Data


public class RequestFeedbackDTO {


        private Long ComplaintId; // ID of the associated complaint
        private String FeedbackContent; // Feedback content

}
