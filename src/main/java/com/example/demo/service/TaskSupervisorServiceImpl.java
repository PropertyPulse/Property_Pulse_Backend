package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestUserdetails;
import com.example.demo.dto.responseDto.ResponseTsdetails;
import com.example.demo.entity.TaskSupervisor;
import com.example.demo.repository.TaskSupervisorRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public ResponseTsdetails getTasksupervisorDetails(RequestUserdetails req) {


        ResponseTsdetails responseTsdetails = new ResponseTsdetails();

        Optional<User> userOptional = userRepository.findByEmail(req.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            TaskSupervisor taskSupervisor = taskSupervisorRepository.findTaskSupervisorByUser(user);
//
//            responseTsdetails.setFirstname(taskSupervisor.getFirstname());
//            responseTsdetails.setLastname(taskSupervisor.getLastname());
        }


        return responseTsdetails;
    }
}
