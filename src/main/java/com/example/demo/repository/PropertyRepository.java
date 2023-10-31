package com.example.demo.repository;
import com.example.demo.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

    List<Object> findAllById(Long propertyId);
    @Query("SELECT p FROM Property p WHERE p.isDeleted = false AND p.acceptedStatus = false")
    List<Property> findByIsDeletedFalseAndAcceptedStatusFalse();

    List<Property> findByAcceptedStatus(boolean b);

    @Query("SELECT p FROM Property p WHERE  p.acceptedStatus = true AND  p.taskSupervisor IS NOT NULL")
    List<Property> findByAcceptedStatusTrueAndTaskSupervisorIsNotNull();
    List<Property> findByIsDeleted(boolean isDeleted);



//    List<Property> findByAccepted_Status(boolean b);


    List<Property> findByTaskSupervisorId(Integer integer);
    List<Property> findBytaskSupervisor_id(Integer taskSupervisorId);

    List<Property> findByTaskSupervisorIdAndAssignStage(Integer task_supervisor_id, String assign_stage);

    List<Property> findByPropertyOwnerId(Integer propertyOwner);
   Optional<Property> findById (Integer pid);

    Optional<Property> findById(Long propertyId);

}
