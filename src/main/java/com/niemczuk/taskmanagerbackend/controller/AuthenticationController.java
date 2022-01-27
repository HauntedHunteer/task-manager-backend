package com.niemczuk.taskmanagerbackend.controller;

import com.niemczuk.taskmanagerbackend.model.dto.authentication.AuthenticationRequest;
import com.niemczuk.taskmanagerbackend.model.dto.authentication.AuthenticationResponse;
import com.niemczuk.taskmanagerbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/authenticate")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authRequest) {
        return ResponseEntity.ok().body(authenticationService.login(authRequest));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody AuthenticationRequest authRequest) {
        authenticationService.register(authRequest);
        return ResponseEntity.ok("Registration successful");
    }
}
