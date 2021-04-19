package com.example.demo.core.application.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class AddAppointmentRangeDto {

    @NotNull
    private Long doctorId;

    @NotNull
    private LocalDateTime from;

    @NotNull
    private LocalDateTime to;
}
