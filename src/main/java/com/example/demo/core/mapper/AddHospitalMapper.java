package com.example.demo.core.mapper;

import com.example.demo.core.application.dto.AddHospitalDto;
import com.example.demo.core.application.dto.AddUserDto;
import com.example.demo.core.database.entity.HospitalEntity;
import com.example.demo.core.database.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AddHospitalMapper {

    private final ModelMapper mapper;

    public AddHospitalMapper(ModelMapper modelMapper){
        this.mapper = modelMapper;
    }

    public HospitalEntity toEntity(AddHospitalDto hospitalDto){
        HospitalEntity hospitalEntity = mapper.map(hospitalDto, HospitalEntity.class);
        return hospitalEntity;
    }

}
