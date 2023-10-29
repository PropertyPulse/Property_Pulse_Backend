package com.example.demo.service;

import com.example.demo.controller.GeocodeController;
import com.example.demo.entity.Property;
import com.example.demo.entity.TaskSupervisor;
import com.example.demo.entity.ValuationReport;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.repository.TaskSupervisorRepository;
import com.example.demo.repository.ValuationReportRepository;
import lombok.Builder;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;


@Service
@Builder
public class TopManagerServiceImpl  implements  TopManagerService {


    public TaskSupervisorRepository taskSupervisorRepository;

    public PropertyRepository propertyRepository;

    public ValuationReportRepository valuationReportRepository;


    @Autowired
    public TopManagerServiceImpl(TaskSupervisorRepository taskSupervisorRepository, PropertyRepository propertyRepository, ValuationReportRepository valuationReportRepository) {
        this.taskSupervisorRepository = taskSupervisorRepository;
        this.propertyRepository = propertyRepository;
        this.valuationReportRepository = valuationReportRepository;

    }

    @Override
    public List<TaskSupervisor> SelectedSupervisors(String address) throws IOException {
        Map<Integer, Double> availableTaskSupervisors = new LinkedHashMap<Integer,Double>();
        TaskSupervisor taskSupervisor = null;
        String nearestTown = "";
        double Distance = 0.0;
        double WeightedSum = 0.0;
        List<TaskSupervisor> taskSupervisors = new ArrayList<>();
        for (Map.Entry<Integer, Double> mapElement : availableTaskSupervisors.entrySet()) {
            taskSupervisor = taskSupervisorRepository.getReferenceById(mapElement.getKey());
            nearestTown = taskSupervisor.getNearestTown();
            Distance = LocationServiceImpl.calculateHaversineDistance(GeocodeController.GeocodeResult(address).getLatitude(), GeocodeController.GeocodeResult(address).getLongitude(),
            GeocodeController.GeocodeResult(nearestTown).getLatitude(), GeocodeController.GeocodeResult(nearestTown).getLongitude());
            WeightedSum =  mapElement.getValue()*3/(Distance*2);
            availableTaskSupervisors.put(mapElement.getKey(), WeightedSum);



        }


        List<Map.Entry<Integer,Double> > list
                = new ArrayList<Map.Entry<Integer,Double> >(
                availableTaskSupervisors.entrySet());


        Collections.sort(
                list,
                (entry1, entry2) -> {

                    // Subtracting the entries
                    return (int) (entry1.getValue() - entry2.getValue());
                });


        for (Map.Entry<Integer,Double> entry : list) {

            taskSupervisor = taskSupervisorRepository.getReferenceById(entry.getKey());
            taskSupervisors.add(taskSupervisor);
        }
        return taskSupervisors;
    }

    @Override
    public void requestValuationandAcceptProperty(Long propertyId) throws ResourceNotFoundException {

        Property property = propertyRepository.findById(Math.toIntExact(propertyId))
                .orElseThrow(() -> new ResourceNotFoundException("Property not found"));
        ValuationReport valuationReport = new ValuationReport();
        valuationReport.setStatus("accepted");
        valuationReport.setRequestedDate(LocalDate.now());
        valuationReport.setProperty(property);

       property.setAccepted_date(LocalDate.now());
       property.setValuationReport(valuationReport);
//       property.setAccepted_status(true);
        valuationReportRepository.save(valuationReport);
        propertyRepository.save(property);
        
        



    }

    @Override
    public List<Property> NewManagementRequests() {

//        List<Property> properties = propertyRepository.findByAccepted_Status(false);
//        return properties;


        return null;
    }

    @Override
    public void AssignTaskSupervisor(Long propertyId, Long taskSupervisorId) throws ResourceNotFoundException {

            Property property = propertyRepository.findById(Math.toIntExact(propertyId))
                    .orElseThrow(() -> new ResourceNotFoundException("Property not found" + propertyId));
            TaskSupervisor taskSupervisor = taskSupervisorRepository.findById(Math.toIntExact(taskSupervisorId))
                    .orElseThrow(() -> new ResourceNotFoundException("Task Supervisor not found" + taskSupervisorId));
            property.setTaskSupervisor(taskSupervisor);
            if(taskSupervisor.getProperties() == null)
            {
                List<Property> properties = new ArrayList<>();
                properties.add(property);
                taskSupervisor.setProperties(properties);
            }
            else
            {
                taskSupervisor.getProperties().add(property);
            }

            taskSupervisorRepository.save(taskSupervisor);
            propertyRepository.save(property);
    }


}
