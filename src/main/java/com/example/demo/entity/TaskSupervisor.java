package com.example.demo.entity;

import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasksupervisor")
public class TaskSupervisor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @NotNull(message = "Firstname is required")
    private String firstname;
    @NotNull(message = "Lastname is required")
    private String lastname;

    private String contactno;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;



    @OneToMany(mappedBy = "taskSupervisor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskRequest> taskRequests = new ArrayList<>();

    @OneToMany(mappedBy = "taskSupervisor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReceivablePayment> receivablePayments = new ArrayList<>();


}
