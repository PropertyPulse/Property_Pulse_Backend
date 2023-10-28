package com.example.demo.repository;
import com.example.demo.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {


    List<Property> findByTaskSupervisorId(Integer integer);
    List<Property> findBytaskSupervisor_id(Integer taskSupervisorId);

    List<Property> findByTaskSupervisorIdAndAssignStage(Integer taskSupervisorId, String assign_stage);

   Optional<Property> findById (Integer pid);

    Optional<Property> findById(Long propertyId);
}
