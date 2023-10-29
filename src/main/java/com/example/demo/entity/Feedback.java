package com.example.demo.entity;

import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.dnd.MouseDragGestureRecognizer;
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

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "complaint_id")
        private Complaint complaint; // Reference to the Complaint entity

        private String feedbackContent; // Feedback content

        @Column(name = "created_at")
        private Date createdAt; // Date of feedback

        @Column(name = "status")
        private String status; // Feedback status

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "top_manager_id")
        private TopManager topManager; // Reference to the User entity

        // Constructors, getters, and setters
}


