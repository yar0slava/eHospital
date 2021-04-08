package com.example.demo.core.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BankAccount {

    private long id;
    private String name;
    private BigDecimal balance;
}
