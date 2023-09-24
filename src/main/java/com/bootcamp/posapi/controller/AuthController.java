package com.bootcamp.posapi.controller;

import com.bootcamp.posapi.model.AuthRequest;
import com.bootcamp.posapi.model.AuthResponse;
import com.bootcamp.posapi.model.RegisterRequest;
import com.bootcamp.posapi.model.ResponseModel;
import com.bootcamp.posapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseModel> authenticate(@RequestBody AuthRequest request){
        Optional<AuthResponse> result = authService.authenticate(request);
        if(result.isPresent()){
            return ResponseEntity.ok()
                    .body(new ResponseModel(200, "SUCCESS", result));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseModel(400, "FAILED","User not found"));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseModel> register(@RequestBody RegisterRequest request){
        Optional<AuthResponse> result = authService.register(request);

        if(result.isPresent()){
            return ResponseEntity.ok()
                    .body(new ResponseModel(200,"SUCCESS", result));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseModel(400,"FAILED", "Register is failed"));
    }

}
