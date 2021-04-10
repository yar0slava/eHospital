package com.example.demo.core.database.entity;

import com.example.demo.core.domain.model.Hospital;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "patients")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "passport", unique=true)
    private String passport;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthday")
    private Date birthday;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "email", unique=true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "authority")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Authority> authority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", insertable = false, updatable = false)
    private HospitalEntity hospitalCode;


//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<BankAccountEntity> bankAccount;
}
