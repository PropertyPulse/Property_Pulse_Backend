package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Feedback {



        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "complaint_id") // Name of the foreign key column in the Feedback table
        private Complaint complaint; // Reference to the Complainant entity

        private String descrription; // Feedback content
        private Date date; // Date of feedback
        // Constructors, getters, and setters
    }


