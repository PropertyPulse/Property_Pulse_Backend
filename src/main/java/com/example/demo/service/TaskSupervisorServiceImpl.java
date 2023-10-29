package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestUserDetailsDto;
import com.example.demo.dto.responseDto.*;
import com.example.demo.entity.TaskSupervisor;
import com.example.demo.repository.TaskSupervisorRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@Transactional
public class TaskSupervisorServiceImpl implements TaskSupervisorService {

    private final UserRepository userRepository;
    private final TaskSupervisorRepository taskSupervisorRepository;
    public TaskSupervisorServiceImpl(UserRepository userRepository, TaskSupervisorRepository taskSupervisorRepository) {
        this.userRepository = userRepository;
        this.taskSupervisorRepository = taskSupervisorRepository;
    }

    @Override
    public ResponseTsDetailsDto getTasksupervisorDetails(RequestUserDetailsDto req) {


        ResponseTsDetailsDto responseTsdetailsDto = new ResponseTsDetailsDto();

        Optional<User> userOptional = userRepository.findByEmail(req.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            TaskSupervisor taskSupervisor = taskSupervisorRepository.findTaskSupervisorByUser(user);

//            responseTsdetails.setFirstname(taskSupervisor.getFirstname());
//            responseTsdetails.setLastname(taskSupervisor.getLastname());
        }


        return responseTsdetailsDto;
    }

}
