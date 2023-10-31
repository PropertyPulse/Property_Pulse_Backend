package com.example.demo.service;

import com.example.demo.auth.RegisterRequest;
import com.example.demo.dto.responseDto.ResponseOngoingTasksDto;
import com.example.demo.dto.responseDto.ResponsePo;
import com.example.demo.entity.Property;
import com.example.demo.entity.PropertyOwner;
import com.example.demo.entity.Task;
import com.example.demo.exception.UserException;
import com.example.demo.repository.PropertyOwnerRepository;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PropertyOwnerServiceImpl implements PropertyOwnerService {
    private final PropertyOwnerRepository propertyOwnerRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final TaskRepository taskRepository;

    public PropertyOwnerServiceImpl(PropertyOwnerRepository propertyOwnerRepository, UserRepository userRepository, PropertyRepository propertyRepository, TaskRepository taskRepository) {
        this.propertyOwnerRepository = propertyOwnerRepository;
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public ResponsePo addPropertyOwner(RegisterRequest reqreg) {
        PropertyOwner propertyOwner = reqreg.getPropertyOwner();

        // Perform necessary operations with the propertyOwner object
        // For example, you can save it to the database using the repository

        PropertyOwner savedPropertyOwner = propertyOwnerRepository.save(propertyOwner);

        // Create a ResponsePo object if needed
        ResponsePo response = new ResponsePo();
        // Populate the response object with relevant data if required

        // Return the response object
        return response;
    }

    @Override
    @Transactional
    public List<ResponseOngoingTasksDto> getOngoingTasks(String email) throws UserException {
        Optional<User> user = userRepository.findByEmail(email);
        List<Property> properties = propertyRepository.findByPropertyOwnerId(user.get().getId());

        List<Task> tasks = new ArrayList<>();
        for (Property property : properties) {
            List<Task> taskListOfProperty = taskRepository.findByPropertyId(property.getId());
            for (Task task : taskListOfProperty) tasks.add(task);
        }

        List<ResponseOngoingTasksDto> ongoingTasks = tasks
                .stream()
                .filter(task -> task.getStatus().equals("Ongoing"))
                .map(this::mapOngoingTaskToDto)
                .collect(Collectors.toList());

        return ongoingTasks;
    }

    private ResponseOngoingTasksDto mapOngoingTaskToDto(Task task) {
        ResponseOngoingTasksDto dto = new ResponseOngoingTasksDto();

        dto.setTaskId(task.getId());
        dto.setPropertyId(task.getProperty().getId());
        dto.setTaskStatus(task.getStatus());
        dto.setTask(task.getTask());
        dto.setStartedDate(task.getStartDate());
        dto.setEndingDate(task.getEndDate());

        return dto;
    }


}
