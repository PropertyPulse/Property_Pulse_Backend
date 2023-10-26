package com.example.demo.auth;

import com.example.demo.dto.requestDto.RequestPasswordResetDto;
import com.example.demo.exception.UserException;
import com.example.demo.config.JwtService;
import com.example.demo.entity.PropertyOwner;
import com.example.demo.repository.PropertyOwnerRepository;

import com.example.demo.token.Token;
import com.example.demo.token.TokenRepository;
import com.example.demo.token.TokenType;

import com.example.demo.token.*;
import com.example.demo.user.Role;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;


@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${application.security.jwt.refresh-token.expiration}")
    private Integer refreshExpiration;
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final PropertyOwnerRepository propertyOwnerRepository;

    private final PasswordResetTokenRepository passwordResetTokenRepository;


    @Transactional
    public AuthenticationResponse register(RegisterRequest request) throws UserException {

//check existing users

        if(repository.findByEmail(request.getEmail()).isPresent()){

            throw new UserException("User with this email already exists");
        }

        if(request.getFirstname() == null || request.getFirstname().equals("")){
            throw new UserException("Firstname is required");
        }
        if(request.getLastname() == null || request.getLastname().equals("")){
            throw new UserException("Lastname is required");
        }
        if(request.getPassword() == null || request.getPassword().equals("")){
            throw new UserException("Password is required");
        }



        PropertyOwner propertyOwner = request.getPropertyOwner();




        if (propertyOwner.getNic()==null || propertyOwner.getNic().equals("")) {
            throw new UserException("Nic is required");
        }


        if (propertyOwner.getAddress()==null || propertyOwner.getAddress().equals("")) {
            throw new UserException("Address is required");
        }




         String[] districts = {
                "Ampara", "Anuradhapura", "Badulla", "Batticaloa", "Colombo",
                "Galle", "Gampaha", "Hambantota", "Jaffna", "Kalutara",
                "Kandy", "Kegalle", "Kilinochchi", "Kurunegala", "Mannar",
                "Matale", "Matara", "Monaragala", "Mullaitivu", "Nuwara Eliya",
                "Polonnaruwa", "Puttalam", "Ratnapura", "Trincomalee", "Vavuniya"
        };


        if (propertyOwner.getDistrict()==null || propertyOwner.getDistrict().equals("")) {
            throw new UserException("District is required");
        }

        if (propertyOwner.getTelephone()==null || propertyOwner.getTelephone().equals("")) {
            throw new UserException("Phone number is required");
        }

        if (!Arrays.asList(districts).contains(propertyOwner.getDistrict())) {
            throw new UserException("District is not valid");
        }

        if (propertyOwner.getTelephone().length() != 10) {
            throw new UserException("Phone number is not valid");
        }



//
////        create the new user registration and save it to the database and return the token out of it
//            var user = User.builder()
//                    .firstname(request.getFirstname())
//                    .lastname(request.getLastname())
//                    .email(request.getEmail())
//                    .password(encoder.encode(request.getPassword()))
//                    .role(request.getRole())
////                    .propertyOwner(propertyOwner)
//                    .build();


        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        propertyOwner.setUser(user);
        user.setPropertyOwner(propertyOwner);
            //take the saved user into the savedUser variable
           var savedUser = repository.save(user);


//           propertyOwner.setUser(savedUser);
//        propertyOwnerRepository.save(propertyOwner);

            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);


        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .firstname(savedUser.getFirstname())
                .lastname(savedUser.getLastname())
                .build();


    }

//
//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//
//
//
//
//
//
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//
//        );
////        now user is authenticated
////        now we generate token to be sent
//        var user = repository.findByEmail(request.getEmail()).orElseThrow();
//        var jwtToken = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefreshToken(user);
////        saving the generated token
//        revokeAllUserTokens(user);
//        saveUserToken(user, jwtToken);
//
//        return AuthenticationResponse.builder()
//                .accessToken(jwtToken)
//                .refreshToken(refreshToken).build();
//
//    }

    @Transactional
    public AuthenticationResponse authenticate(HttpServletResponse response, AuthenticationRequest request) throws UserException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // Authentication successful, continue with token generation
            var user = repository.findByEmail(request.getEmail()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
//            revokeAllUserTokens(user);
            updateUserToken(user, jwtToken);
            creatCookie(response, refreshToken, refreshExpiration / 1000);
            return AuthenticationResponse.builder()
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .accessToken(jwtToken)
                    .build();
        } catch (AuthenticationException e) {
            // Authentication failed, handle the exception
            throw new UserException("Authentication failed: " + e.getMessage());
        }
    }



//    revoke all users
    private void revokeAllUserTokens(User user){
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if(validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(token -> {
            token.setRevoked(true);
           token.setExpired(true);
        });
        tokenRepository.saveAll(validUserTokens);

    }


    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void updateUserToken(User user, String jwtToken) {
        var storedToken = tokenRepository.findByUser_Id(user.getId()).orElse(null);
        if (storedToken != null) {
            storedToken.setToken(jwtToken);
            storedToken.setExpired(false);
            storedToken.setRevoked(false);
            tokenRepository.save(storedToken);
        }
    }

//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        // here we firstly, doing extracting the request (header,jwt,useremail)
//
//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String refreshToken;
//        final String userEmail;
//        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
//            return;
//        }
//        refreshToken = authHeader.substring(7);
//
//
//        //userEmail is in the jwt token , so we need to extract it from the jwt token, in order to do that we need a class called jwtService to manipulate the jwt token
//        userEmail = jwtService.extractUsername(refreshToken);
//
////        now we have extracted the useremail from the jwt token, we need to check if the user is authenticated or not
//
//
//        if (userEmail != null) {
//
//
//            var user = this.repository.findByEmail(userEmail).orElseThrow();
//
//            if (jwtService.isTokenValid(refreshToken, user)) {
//               var accessToken = jwtService.generateToken(user);
//               revokeAllUserTokens(user);
//               saveUserToken(user, accessToken);
//               var authResponse = AuthenticationResponse.builder()
//                       .accessToken(accessToken)
//                       .refreshToken(refreshToken)
//                       .build();
//               new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
//            }
//        }
//        }


    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String refreshToken = null;
        final String email;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null) {
            return;
        }
        email = jwtService.extractUsername(refreshToken);
//        now we have extracted the useremail from the jwt token, we need to check if the user is authenticated or not


        if (email != null) {
            var user = this.repository.findByEmail(email).orElseThrow();

            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
//                revokeAllUserTokens(user);
//                saveUserToken(user, accessToken);
                updateUserToken(user, accessToken);
                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .accessToken(accessToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }

    }
//    public AuthenticationResponse refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        // here we firstly, doing extracting the request (header,jwt,useremail)
//
//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String refreshToken;
//        final String userEmail;
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            throw new IOException("Refresh token is missing");
//        }
//        refreshToken = authHeader.substring(7);
//
//
//        //userEmail is in the jwt token , so we need to extract it from the jwt token, in order to do that we need a class called jwtService to manipulate the jwt token
//        userEmail = jwtService.extractUsername(refreshToken);
//
////        now we have extracted the useremail from the jwt token, we need to check if the user is authenticated or not
//
//
//        if (userEmail != null) {
//            var user = this.repository.findByEmail(userEmail).orElseThrow();
//
//            if (jwtService.isTokenValid(refreshToken, user)) {
//                var accessToken = jwtService.generateToken(user);
//                revokeAllUserTokens(user);
//                saveUserToken(user, accessToken);
//                return AuthenticationResponse.builder()
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                        .build();
//
//            }
//        }
//        return null;
//    }

    private void creatCookie(HttpServletResponse response, String refreshToken, Integer MaxAge) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);  // Make the cookie accessible only through HTTP
        refreshTokenCookie.setMaxAge(MaxAge);  // Set the cookie's expiration time in seconds
        refreshTokenCookie.setPath("/");  // Set the cookie's path to the root
        response.addCookie(refreshTokenCookie);
    }

    public void resetPassword(String email) throws UserException {
        if (repository.findByEmail(email).isEmpty()) {
            throw new UserException("User not found");
        } else {
            var user = repository.findByEmail(email).orElseThrow();
            String token = jwtService.generateToken(user);

            PasswordResetToken existingToken = user.getPwresettoken();
            if (existingToken == null) {
                // If no existing token, create a new one
                PasswordResetToken passwordResetToken = new PasswordResetToken();
                passwordResetToken.setToken(token);
                passwordResetToken.setUser(user);
                user.setPwresettoken(passwordResetToken);
                passwordResetTokenRepository.save(passwordResetToken);
            } else {
                // Update the existing token if needed
                existingToken.setToken(token);
                passwordResetTokenRepository.save(existingToken);
            }



        }
    }

}
