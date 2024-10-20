package com.example.authservice.service.impl;

import com.example.authservice.dto.request.LoginRequestDto;
import com.example.authservice.dto.request.RegisterRequestDto;
import com.example.authservice.dto.response.CommonResponseDto;
import com.example.authservice.dto.response.LoginResponseDto;
import com.example.authservice.entity.UserEntity;
import com.example.authservice.exception.errors.auth.AccountDisabledException;
import com.example.authservice.exception.errors.auth.AccountLockedException;
import com.example.authservice.exception.errors.auth.AuthenticationFailedException;
import com.example.authservice.exception.errors.auth.InvalidCredentialsException;
import com.example.authservice.exception.errors.resource.EntityAlreadyExistsException;
import com.example.authservice.mapper.AuthMapper;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.service.AuthService;
import com.example.authservice.utils.JwtTokens;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokens jwtTokens;
    private final AuthenticationManager authenticationManager;
    private final AuthMapper authMapper = AuthMapper.INSTANCE;

    @Override
    public CommonResponseDto register(RegisterRequestDto registerRequestDto) {

        if (userRepository.existsByUsername(registerRequestDto.getUsername())) {
            throw new EntityAlreadyExistsException("Username already exist by: " + registerRequestDto.getUsername());
        }
        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new EntityAlreadyExistsException("Email already exist by: " + registerRequestDto.getEmail());
        }

        UserEntity userEntity = authMapper.toEntity(registerRequestDto);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
        return new CommonResponseDto(true, "User Register successfully.");
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto;
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getUsername(),
                            loginRequestDto.getPassword()));

            if (authenticate.isAuthenticated()) {
                String token = this.generateToken(loginRequestDto.getUsername());
                loginResponseDto = new LoginResponseDto( true, "Login Success", token);
            } else {
                throw new InvalidCredentialsException("Username or Password incorrect");
            }
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException("Username or Password incorrect");
        } catch (DisabledException ex) {
            throw new AccountDisabledException("Account is disabled. Please contact support.");
        } catch (LockedException ex) {
            throw new AccountLockedException("Account is locked. Please contact support.");
        } catch (AuthenticationException ex) {
            throw new AuthenticationFailedException("Authentication failed: " + ex.getMessage());
        }
        return loginResponseDto;
    }

    @Override
    public CommonResponseDto validateToken(String token) {
        CommonResponseDto commonResponseDto;
        if (jwtTokens.validateToken(token)) {
            commonResponseDto = new CommonResponseDto(true, "Token validated");
        } else {
            commonResponseDto = new CommonResponseDto(false, "Token invalid");
        }
        return commonResponseDto;
    }

    private String generateToken(String username) {
        return jwtTokens.generateToken(username);
    }
}
