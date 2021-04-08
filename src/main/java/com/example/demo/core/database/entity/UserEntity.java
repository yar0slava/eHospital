package com.example.demo.core.database.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "demo_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email", unique=true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "authority")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Authority> authority;

    @OneToOne
    private HospitalEntity hospital;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BankAccountEntity> bankAccount;
}
