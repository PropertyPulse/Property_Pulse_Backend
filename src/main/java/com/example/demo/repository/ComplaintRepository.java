package com.example.demo.repository;

import com.example.demo.entity.Complaint;
import com.example.demo.entity.NewComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends JpaRepository<NewComplaint, Integer> {

}
