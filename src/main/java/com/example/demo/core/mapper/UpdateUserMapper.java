package com.example.demo.core.mapper;

import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.database.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class UpdateUserMapper {
    private final ModelMapper mapper;

    public UpdateUserMapper(ModelMapper modelMapper){
        this.mapper = modelMapper;
    }

    public UserEntity toEntity(UserDto userDto){
        return mapper.map(userDto, UserEntity.class);
    }
}
