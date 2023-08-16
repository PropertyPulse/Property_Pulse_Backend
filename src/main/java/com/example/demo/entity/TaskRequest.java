package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task_request")
public class TaskRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String location;
    private String task;
    private Double estimatedprice;

    private LocalDate scheduleDate;  // No need to initialize here
    private String details;


    private String manpowerCompany_feedback;
    private LocalDate manpowerCompanytask_startDate;
    private Integer manpowerCompanytask_requiredDate;

    private Integer assignedEmployeeId;




//    PENDING ,    ACCEPTED,    DECLINED,    COMPLETED
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @PrePersist
    public void prePersist() {
        if (scheduleDate == null) {
            scheduleDate = LocalDate.now();  // Set the default value to current date
        }
    }


    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "task_supervisor_id")
    private TaskSupervisor taskSupervisor;



}
