package com.example.demo.core.application.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentDto {

    private long id;

    @NotNull
    private LocalDateTime dateTime;

    private Long patientId;

    @NotNull
    private Long doctorId;
}
