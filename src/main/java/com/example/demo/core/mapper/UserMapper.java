package com.example.demo.core.mapper;

import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.database.entity.UserEntity;
import com.example.demo.core.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper {

    private final ModelMapper mapper;

    public UserMapper(ModelMapper modelMapper){
        this.mapper = modelMapper;
    }

    public UserDto toDto(UserEntity userEntity){
        return Objects.isNull(userEntity) ? null : mapper.map(userEntity, UserDto.class);
    }

    public User toModel(UserEntity userEntity){
        return Objects.isNull(userEntity) ? null : mapper.map(userEntity, User.class);
    }
}
