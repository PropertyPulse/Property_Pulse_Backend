package com.example.demo.auth;

import com.example.demo.dto.requestDto.RequestPasswordResetDto;
import com.example.demo.exception.UserException;
import com.example.demo.service.PropertyOwnerService;
import com.example.demo.service.PropertyOwnerServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthenticationController {

    private final AuthenticationService service;

    private final PropertyOwnerService poservice;

    public AuthenticationController(AuthenticationService service, PropertyOwnerServiceImpl poservice) {
        this.service = service;
        this.poservice = poservice;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request
    ) throws UserException {

//        poservice.addPropertyOwner(request);
               System.out.println(request);
        return ResponseEntity.ok(service.register(request));
    }


//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponse> authenticate(
//            @RequestBody @Valid AuthenticationRequest request
//    ) throws UserException {
//        return ResponseEntity.ok(service.authenticate(request));
//    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(HttpServletResponse httpresponse, @RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse response = service.authenticate(httpresponse, request);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException | UserException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthenticationResponse(e.getMessage()));
        }
    }


//    @PostMapping("/refresh-token")
//    public void refreshToken(
//        HttpServletRequest request,
//        HttpServletResponse response
//    ) throws IOException {
//       service.refreshToken(request,response);
//    }
    @GetMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        System.out.println("refresh token");
        service.refreshToken(request,response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody RequestPasswordResetDto request) throws UserException {
        service.resetPassword(request.getEmail());
        return ResponseEntity.ok("Password reset successfully.");
    }
}
