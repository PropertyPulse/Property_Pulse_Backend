package com.example.demo.auth;

import com.example.demo.exception.UserException;
import com.example.demo.service.PropertyOwnerService;
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

    public AuthenticationController(AuthenticationService service, PropertyOwnerService poservice) {
        this.service = service;
        this.poservice = poservice;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request
    ) throws UserException {

//        poservice.addPropertyOwner(request);

        return ResponseEntity.ok(service.register(request));
    }


//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponse> authenticate(
//            @RequestBody @Valid AuthenticationRequest request
//    ) throws UserException {
//        return ResponseEntity.ok(service.authenticate(request));
//    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse response = service.authenticate(request);
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
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        AuthenticationResponse refresresponse = service.refreshToken(request,response);
        return ResponseEntity.ok(refresresponse);
    }

}
