package com.example.demo.repository;
import com.example.demo.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

    List<Property> findByTaskSupervisorId(Integer integer);
    List<Property> findBytaskSupervisor_id(Integer taskSupervisorId);

    List<Property> findByTaskSupervisorIdAndAssignStage(Integer taskSupervisorId, String assign_stage);
}
