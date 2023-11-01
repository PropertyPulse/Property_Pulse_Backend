package com.example.demo.repository;

import com.example.demo.entity.ScheduleTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledTaskRepository extends JpaRepository<ScheduleTask, Integer> {

}
