package com.example.demo.core.database.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "bank_account")
public class BankAccountEntity {

    @Id
    private long id;
    private String name;
    private BigDecimal balance;
}
