package com.example.demo.service;
import com.example.demo.auth.AuthenticationService;
import com.example.demo.config.*;
import com.example.demo.auth.AuthenticationResponse;
import com.example.demo.dto.requestDto.RequestAddnewInternalUserDto;
import com.example.demo.entity.FinancialManager;
import com.example.demo.entity.TaskSupervisor;
import com.example.demo.entity.TopManager;
import com.example.demo.exception.UserException;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public String addTopmanager(RequestAddnewInternalUserDto req) throws UserException {
        if(userRepository.findByEmail(req.getEmail()).isPresent()){

            throw new UserException("User with this email already exists");
        }

        User user = new User();
        user.setFirstname(req.getUsername());
        user.setLastname(" ");
        user.setRole(Role.TOPMANAGER);
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));

        TopManager topManager = new TopManager();
        topManager.setUser(user);
        topManager.setDob(req.getDob());
        topManager.setNic(req.getNic());
        topManager.setPhone(req.getPhone());
        topManager.setDistrict(req.getDistrict());
        topManager.setAddress(req.getAddress());

        user.setTopManager(topManager);

        var saveduser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);


        authenticationService.saveUserToken(saveduser, jwtToken);

        return "User added successfully";


    }

    @Override
    @Transactional
    public String addFinancemanager(RequestAddnewInternalUserDto req) throws UserException {
        if(userRepository.findByEmail(req.getEmail()).isPresent()){

            throw new UserException("User with this email already exists");
        }

        User user = new User();
        user.setFirstname(req.getUsername());
        user.setLastname(" ");
        user.setRole(Role.FINANCIALMANAGER);
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));

        FinancialManager financialmanager = new FinancialManager();
        financialmanager.setUser(user);
        financialmanager.setDob(req.getDob());
        financialmanager.setNic(req.getNic());
        financialmanager.setPhone(req.getPhone());
        financialmanager.setDistrict(req.getDistrict());
        financialmanager.setAddress(req.getAddress());

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
    public String addTaskSupervisor(RequestAddnewInternalUserDto req) throws UserException {

        if(userRepository.findByEmail(req.getEmail()).isPresent()){

            throw new UserException("User with this email already exists");
        }


        User user = new User();
        user.setFirstname(req.getUsername());
        user.setLastname(" ");
        user.setRole(Role.TASKSUPERVISOR);
        user.setEmail(req.getEmail());

        user.setPassword(encoder.encode(req.getPassword()));

        TaskSupervisor tasksupervisor = new TaskSupervisor();
        tasksupervisor.setUser(user);
        tasksupervisor.setDob(req.getDob());
        tasksupervisor.setNic(req.getNic());
        tasksupervisor.setPhone(req.getPhone());
        tasksupervisor.setDistrict(req.getDistrict());
        tasksupervisor.setAddress(req.getAddress());

        user.setTaskSupervisor(tasksupervisor);

        var saveduser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);


        authenticationService.saveUserToken(saveduser, jwtToken);

        return "User added successfully";


    }

    @Override
    @Transactional
    public String addValuationExpert(RequestAddnewInternalUserDto req) throws UserException {
        if(userRepository.findByEmail(req.getEmail()).isPresent()){

            throw new UserException("User with this email already exists");
        }

        User user = new User();
        user.setFirstname(req.getUsername());
        user.setLastname(" ");
        user.setRole(Role.VE);
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));

        TaskSupervisor tasksupervisor = new TaskSupervisor();
        tasksupervisor.setUser(user);
        tasksupervisor.setDob(req.getDob());
        tasksupervisor.setNic(req.getNic());
        tasksupervisor.setPhone(req.getPhone());
        tasksupervisor.setDistrict(req.getDistrict());
        tasksupervisor.setAddress(req.getAddress());

        user.setTaskSupervisor(tasksupervisor);

        var saveduser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);


        authenticationService.saveUserToken(saveduser, jwtToken);

        return "User added successfully";
    }

//    @Override
//    @Transactional
//    public String addInsurancemanager(RequestAddnewInternalUserDto req) throws UserException {
//        return null;
//    }
}
