package com.example.demo.config;


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

    public AdminUserInitializer(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {

        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setPassword(encoder.encode("admin1234"));
        user.setRole(Role.ADMIN);

        Admin userAdmin = new Admin();
        userAdmin.setUser(user);
        user.setAdmin(userAdmin);

        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            userRepository.save(user);
            System.out.println("Admin user created.");
        }
    }
}
