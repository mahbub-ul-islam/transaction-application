package com.example.authservice.service;

import com.example.authservice.dto.request.LoginRequestDto;
import com.example.authservice.dto.request.RegisterRequestDto;
import com.example.authservice.dto.response.CommonResponseDto;
import com.example.authservice.dto.response.LoginResponseDto;

public interface AuthService {

    CommonResponseDto register(RegisterRequestDto registerRequestDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    CommonResponseDto validateToken(String token);
}