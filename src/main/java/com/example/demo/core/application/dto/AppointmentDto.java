package com.example.demo.core.application.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AppointmentDto {

    private long id;

    @NotNull
    private LocalDateTime dateTime;

    private Long patientId;

    @NotNull
    private Long doctorId;
}
