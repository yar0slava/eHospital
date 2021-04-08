package com.example.demo.core.mapper;

import com.example.demo.core.application.dto.AddUserDto;
import com.example.demo.core.database.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AddUserMapper {

    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    public AddUserMapper(PasswordEncoder passwordEncoder, ModelMapper modelMapper){
        this.passwordEncoder = passwordEncoder;
        this.mapper = modelMapper;
    }

    public UserEntity toEntity(AddUserDto user){
        UserEntity userEntity = mapper.map(user, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        return userEntity;
    }

}
