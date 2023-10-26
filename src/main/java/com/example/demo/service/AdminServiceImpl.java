package com.example.demo.service;
import com.example.demo.auth.AuthenticationService;
import com.example.demo.config.*;
import com.example.demo.dto.requestDto.RequestAddNewInternalUserDto;
import com.example.demo.dto.responseDto.ResponseViewUsersDto;
import com.example.demo.entity.*;
import com.example.demo.exception.UserException;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationService  authenticationService;

    public AdminServiceImpl(PasswordEncoder encoder, UserRepository userRepository, JwtService jwtService, AuthenticationService authenticationService) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Override
    @Transactional
    public String addTopManager(RequestAddNewInternalUserDto req) throws UserException {
        if(userRepository.findByEmail(req.getEmail()).isPresent()){
            throw new UserException("User with this email already exists");
        }

        User user = new User();
        user.setFirstname(req.getFirstName());
        user.setLastname(req.getLastName());
        user.setRole(Role.TOPMANAGER);
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));

        TopManager topManager = new TopManager();
        topManager.setUser(user);
        topManager.setDob(req.getDob());
        topManager.setNic(req.getNic());
        topManager.setContactNo(req.getContactNo());
        topManager.setDistrict(req.getDistrict());
        topManager.setAddress(req.getAddress());
        topManager.setGender(req.getGender());

        user.setTopManager(topManager);

        var saveduser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        authenticationService.saveUserToken(saveduser, jwtToken);

        return "User added successfully";

    }

    @Override
    @Transactional
    public String addFinanceManager(RequestAddNewInternalUserDto req) throws UserException {
        if(userRepository.findByEmail(req.getEmail()).isPresent()){
            throw new UserException("User with this email already exists");
        }

        User user = new User();
        user.setFirstname(req.getFirstName());
        user.setLastname(req.getLastName());
        user.setRole(Role.FINANCIALMANAGER);
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));

        FinancialManager financialmanager = new FinancialManager();
        financialmanager.setUser(user);
        financialmanager.setDob(req.getDob());
        financialmanager.setNic(req.getNic());
        financialmanager.setContactNo(req.getContactNo());
        financialmanager.setDistrict(req.getDistrict());
        financialmanager.setAddress(req.getAddress());
        financialmanager.setGender(req.getGender());

        user.setFinancialManager(financialmanager);

        var saveduser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        authenticationService.saveUserToken(saveduser, jwtToken);
//        return AuthenticationResponse.builder()
//                .accessToken(jwtToken)
//                .refreshToken(refreshToken)
//                .firstname(saveduser.getFirstname())
//                .lastname(saveduser.getLastname())
//                .build();

        return "User added successfully";

    }

    @Override
    @Transactional
    public String addTaskSupervisor(RequestAddNewInternalUserDto req) throws UserException {

        if(userRepository.findByEmail(req.getEmail()).isPresent()){
            throw new UserException("User with this email already exists");
        }

        User user = new User();
        user.setFirstname(req.getFirstName());
        user.setLastname(req.getLastName());
        user.setRole(Role.TASKSUPERVISOR);
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));

        TaskSupervisor tasksupervisor = new TaskSupervisor();
        tasksupervisor.setUser(user);
        tasksupervisor.setDob(req.getDob());
        tasksupervisor.setNic(req.getNic());
        tasksupervisor.setContactNo(req.getContactNo());
        tasksupervisor.setDistrict(req.getDistrict());
        tasksupervisor.setNearestTown(req.getNearestTown());
        tasksupervisor.setAddress(req.getAddress());
        tasksupervisor.setGender(req.getGender());

        user.setTaskSupervisor(tasksupervisor);

        var saveduser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        authenticationService.saveUserToken(saveduser, jwtToken);

        return "User added successfully";

    }

    @Override
    @Transactional
    public String addValuationExpert(RequestAddNewInternalUserDto req) throws UserException {
        if(userRepository.findByEmail(req.getEmail()).isPresent()){
            throw new UserException("User with this email already exists");
        }

        User user = new User();
        user.setFirstname(req.getFirstName());
        user.setLastname(req.getLastName());
        user.setRole(Role.VE);
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));

        ValuationExpert valuationExpert = new ValuationExpert();
        valuationExpert.setUser(user);
        valuationExpert.setDob(req.getDob());
        valuationExpert.setNic(req.getNic());
        valuationExpert.setContactNo(req.getContactNo());
        valuationExpert.setDistrict(req.getDistrict());
        valuationExpert.setNearestTown(req.getNearestTown());
        valuationExpert.setAddress(req.getAddress());
        valuationExpert.setGender(req.getGender());

        user.setValuationExpert(valuationExpert);

        var saveduser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        authenticationService.saveUserToken(saveduser, jwtToken);

        return "User added successfully";
    }

    @Override
    @Transactional
    public List<ResponseViewUsersDto> viewUsers() throws UserException {
        List<User> users = userRepository.findAll();
        List<ResponseViewUsersDto> responseViewUsersDtos = users.stream()
                .map(this::mapUserToDto)
                .collect(Collectors.toList());
        return responseViewUsersDtos;
    }

    private ResponseViewUsersDto mapUserToDto (User user) {
        ResponseViewUsersDto dto = new ResponseViewUsersDto();

        dto.setUserId(user.getId());
        dto.setUserName(user.getFirstname());
        dto.setEmail(user.getEmail());
        dto.setUserRole(user.getRole());

        switch (user.getRole()) {
            case TASKSUPERVISOR:
                TaskSupervisor taskSupervisor = user.getTaskSupervisor();
                if (taskSupervisor != null) {
                    dto.setContactNo(taskSupervisor.getContactNo());
                }
                break;
            case FINANCIALMANAGER:
                FinancialManager financialManager = user.getFinancialManager();
                if (financialManager != null) {
                    dto.setContactNo(financialManager.getContactNo());
                }
            case TOPMANAGER:
                TopManager topManager = user.getTopManager();
                if (topManager != null) {
                    dto.setContactNo(topManager.getContactNo());
                }
            case PROPERTYOWNER:
                PropertyOwner propertyOwner = user.getPropertyOwner();
                if (propertyOwner != null) {
                    dto.setContactNo(propertyOwner.getTelephone());
                }
            case VE:
                ValuationExpert valuationExpert = user.getValuationExpert();
                if (valuationExpert != null) {
                    dto.setContactNo(valuationExpert.getContactNo());
                }
                break;
            default:
                dto.setContactNo("N/A");
                break;
        }

        return dto;
    }

//    @Override
//    @Transactional
//    public String addInsurancemanager(RequestAddnewInternalUserDto req) throws UserException {
//        return null;
//    }
}
