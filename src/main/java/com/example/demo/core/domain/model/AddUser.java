package com.example.demo.core.domain.model;

import com.example.demo.core.database.entity.Authority;
import com.example.demo.core.database.entity.Gender;

import com.example.demo.core.database.entity.Qualification;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class AddUser {

    private Gender gender;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String password;
    private Set<Authority> authority;
    private Hospital hospital;
    private List<Qualification> qualifications;
}
