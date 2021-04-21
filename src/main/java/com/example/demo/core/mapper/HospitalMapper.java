package com.example.demo.core.mapper;

import com.example.demo.core.application.dto.HospitalDto;
import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.database.entity.HospitalEntity;
import com.example.demo.core.database.entity.UserEntity;
import com.example.demo.core.domain.model.Hospital;
import com.example.demo.core.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class HospitalMapper {

    private final ModelMapper mapper;

    public HospitalMapper(ModelMapper modelMapper){
        this.mapper = modelMapper;
    }

    public HospitalDto toDto(HospitalEntity hospitalEntity){
        return Objects.isNull(hospitalEntity) ? null : mapper.map(hospitalEntity, HospitalDto.class);
    }

    public Hospital toModel(HospitalEntity hospitalEntity){
        return Objects.isNull(hospitalEntity) ? null : mapper.map(hospitalEntity, Hospital.class);
    }
}
