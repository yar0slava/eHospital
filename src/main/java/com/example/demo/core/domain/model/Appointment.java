package com.example.demo.core.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Appointment {

    private long id;
    private LocalDateTime dateTime;
    private Long patientId;
    private Long doctorId;
}
