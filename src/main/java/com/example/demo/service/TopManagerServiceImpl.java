package com.example.demo.service;

import com.example.demo.controller.GeocodeController;
import com.example.demo.entity.TaskSupervisor;
import com.example.demo.repository.TaskSupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;


@Service
public class TopManagerServiceImpl  implements  TopManagerService {


    public TaskSupervisorRepository taskSupervisorRepository;



    @Autowired
    public TopManagerServiceImpl(TaskSupervisorRepository taskSupervisorRepository) {
        this.taskSupervisorRepository = taskSupervisorRepository;

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

}
