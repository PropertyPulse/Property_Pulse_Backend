package com.example.demo.repository;

import com.example.demo.entity.TaskSupervisor;
import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TaskSupervisorRepository extends JpaRepository<TaskSupervisor, Integer> {

    TaskSupervisor findTaskSupervisorByUser(User user);



}
