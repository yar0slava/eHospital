package com.example.demo.core.mapper;

import com.example.demo.core.application.dto.AppointmentDto;
import com.example.demo.core.database.entity.AppointmentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AppointmentMapper {

    private final ModelMapper mapper;

    public AppointmentMapper(ModelMapper modelMapper){
        this.mapper = modelMapper;
    }

    public AppointmentDto toDto(AppointmentEntity appointmentEntity){
        return Objects.isNull(appointmentEntity) ? null : mapper.map(appointmentEntity, AppointmentDto.class);
    }

    public AppointmentEntity toEntity(AppointmentDto appointmentDto){
        return Objects.isNull(appointmentDto) ? null : mapper.map(appointmentDto, AppointmentEntity.class);
    }
}
