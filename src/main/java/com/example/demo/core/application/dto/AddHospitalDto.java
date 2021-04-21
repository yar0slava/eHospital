package com.example.demo.core.application.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AddHospitalDto {
    @NotBlank
    private String name;

    @NotBlank
    private String town;

    @NotBlank
    private String region;

}
