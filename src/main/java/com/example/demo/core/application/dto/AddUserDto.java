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
public class AddUserDto {

    @NotNull
    private Gender gender;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Min(18)
    @Max(100)
    @UserAgeConstraint
    private Integer age;

    @Email
    @Pattern(regexp = "\\w*@gmail.com\\b")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")
    private String password;

    @NotNull
    private Set<Authority> authority;

    @NotNull
    private Hospital hospital;

    @NotNull
    private List<BankAccount> bankAccount;
}
