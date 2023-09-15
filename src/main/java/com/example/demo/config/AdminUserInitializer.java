package com.example.demo.config;


import com.example.demo.auth.AuthenticationService;
import com.example.demo.entity.Admin;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AdminUserInitializer(UserRepository userRepository, PasswordEncoder encoder, JwtService jwtService, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Override
    public void run(String... args) throws Exception {

        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setFirstname("admin");
        user.setLastname("admin");
        user.setPassword(encoder.encode("admin1234"));
        user.setRole(Role.ADMIN);

        Admin userAdmin = new Admin();
        userAdmin.setUser(user);
        user.setAdmin(userAdmin);

        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            var saveduser = userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);


            authenticationService.saveUserToken(saveduser, jwtToken);
        }
    }
}