package com.example.demo.core.application.dto;

import com.example.demo.core.application.validator.UserAgeConstraint;
import com.example.demo.core.database.entity.Authority;
import com.example.demo.core.database.entity.Gender;
import com.example.demo.core.domain.model.BankAccount;
import com.example.demo.core.domain.model.Hospital;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserDto {

    @NotNull
    private long id;

    private Gender gender;

    private String firstName;

    private String lastName;


    @Email
    @Pattern(regexp = "@gmail.com$")
    private String email;

    @NotNull
    private Set<Authority> authority;

    @NotNull
    private Hospital hospital;

}
