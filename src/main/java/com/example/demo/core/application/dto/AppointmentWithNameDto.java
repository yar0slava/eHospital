package com.example.demo.core.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentWithNameDto {

    private long id;

    @NotNull
    private LocalDateTime dateTime;

    private Long patientId;

    @NotNull
    private Long doctorId;

    private String name;
}
