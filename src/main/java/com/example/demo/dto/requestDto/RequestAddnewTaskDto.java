package com.example.demo.dto.requestDto;

import com.example.demo.entity.TaskStatus;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestAddnewTaskDto {

    private String propertyId;
    private String location;
    private String task;
    private Double price;

    private LocalDate scheduleDate;  // No need to initialize here
    private String manpowercompany_feedback;
    private LocalDate manpowercompany_startDate;
    private String manpowercompany_requiredDays;

//    Assigned employee should be added





    private TaskStatus status;

    @PrePersist
    public void prePersist() {
        if (scheduleDate == null) {
            scheduleDate = LocalDate.now();  // Set the default value to current date
        }
    }

}