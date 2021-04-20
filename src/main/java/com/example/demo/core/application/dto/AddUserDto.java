package com.example.demo.core.application.dto;

import com.example.demo.core.database.entity.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class AddUserDto {

    @NotNull
    private String passport;

    @Email
    @Pattern(regexp = "\\w*@gmail.com\\b")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private Gender gender;

    @NotNull
    private Date birthday;

    @NotNull
    private String phone;

    private String hospitalCode;

    @NotNull
    private Set<String> authority;

    @NotNull
    private Set<String> specializations;
}
