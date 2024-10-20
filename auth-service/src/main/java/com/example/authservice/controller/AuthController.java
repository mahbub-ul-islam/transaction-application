package com.example.authservice.controller;

import com.example.authservice.dto.request.LoginRequestDto;
import com.example.authservice.dto.request.RegisterRequestDto;
import com.example.authservice.dto.response.CommonResponseDto;
import com.example.authservice.dto.response.LoginResponseDto;
import com.example.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authorization API")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    @Operation(summary = "Register User")
    public ResponseEntity<CommonResponseDto> registerUser(@RequestBody RegisterRequestDto registerRequestDto) {
        log.info("Save user request: {}", registerRequestDto);
        CommonResponseDto commonResponseDto = authService.register(registerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseDto);
    }

    @PostMapping("/login")
    @Operation(summary = "Login User")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        log.info("Login user request: {}", loginRequestDto);
        LoginResponseDto loginResponseDto = this.authService.login(loginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDto);
    }

    @GetMapping("/validate")
    @Operation(summary = "Validate Token")
    public ResponseEntity<CommonResponseDto> validateToken(@RequestParam("token") String token) {
        log.info("Token validate request: {}", token);
        CommonResponseDto commonResponseDto = authService.validateToken(token);
        log.info("Token validate response: {}", commonResponseDto);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponseDto);
    }
}
