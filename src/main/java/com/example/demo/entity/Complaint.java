package com.example.demo.entity;

import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Complaint")
public class Complaint  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_id")
    private  long complaint_id;


    @ManyToOne
    @JoinColumn(name = "complainant_id")
    private  User complainant;

    @OneToOne
    @JoinColumn(name = "category_id")
    private ComplaintCategory category;
    @Column(name = "description", length = 1000)
    private String description;


    @Column(name = "reason", length = 500)
    private String reason;

    private String title;
//
//    @OneToOne( fetch = FetchType.LAZY)
//    @JoinColumn(name = "feedback")
//    private Feedback feedback;

}
