package com.example.demo.repository;

import com.example.demo.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository   extends JpaRepository<Feedback, Integer> {



}
