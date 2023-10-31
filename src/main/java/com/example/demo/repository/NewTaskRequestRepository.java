package com.example.demo.repository;

import com.example.demo.entity.NewTaskRequestPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewTaskRequestRepository extends JpaRepository<NewTaskRequestPO, Integer> {

}
