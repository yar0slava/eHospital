package com.example.demo.core.database.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hospital")
public class HospitalEntity {

    @Id
    private long id;
    private String name;
}
