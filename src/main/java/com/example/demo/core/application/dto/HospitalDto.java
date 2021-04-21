package com.example.demo.core.application.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class HospitalDto {
    @NotNull
    private long id;

    @NotEmpty
    private String codeHospital;

    @NotEmpty
    private String name;

    @NotEmpty
    private String town;

    @NotEmpty
    private String region;

}
