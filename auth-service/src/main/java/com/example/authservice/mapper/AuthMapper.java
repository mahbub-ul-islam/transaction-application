package com.example.authservice.mapper;

import com.example.authservice.dto.request.RegisterRequestDto;
import com.example.authservice.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthMapper {

    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    UserEntity toEntity(RegisterRequestDto registerRequestDto);

    RegisterRequestDto toDto(UserEntity userEntity);
}
