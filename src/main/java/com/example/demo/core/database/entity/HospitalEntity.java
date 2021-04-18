package com.example.demo.core.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "hospital")
public class HospitalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "codeHospital", unique = true)
    private String codeHospital;

    @Column(name = "name")
    private String name;

    @Column(name = "town")
    private String town;

    @Column(name = "region")
    private String region;


    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "hospital")
    //@JsonIgnoreProperties("hospital")
    private List<UserEntity> users;
}
