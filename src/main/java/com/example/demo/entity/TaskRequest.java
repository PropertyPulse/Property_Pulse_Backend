package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "new_task_request")
public class TaskRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String propertyId;
    private String location;
    private String task;
    private Double price;

    private LocalDate scheduleDate;  // No need to initialize here
    private String details;

    private TaskStatus status;

    @PrePersist
    public void prePersist() {
        if (scheduleDate == null) {
            scheduleDate = LocalDate.now();  // Set the default value to current date
        }
    }


}
