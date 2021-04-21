package com.example.demo.core.application.dto;

import com.example.demo.core.database.entity.Authority;
import com.example.demo.core.database.entity.Gender;
import com.example.demo.core.database.entity.Specialization;
import com.example.demo.core.domain.model.Hospital;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserDto {

    @NotNull
    private long id;

    @NotNull
    private String passport;

    @NotNull
    private Gender gender;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotNull
    private Date birthday;

    @Email
    @Pattern(regexp = "@gmail.com$")
    private String email;

    @NotNull
    private Set<Authority> authority;

    private List<Specialization> specializations;

    @NotNull
    private Hospital hospital;
}
