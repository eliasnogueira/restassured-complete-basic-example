package com.eliasnogueira.credit.model;

import java.math.BigDecimal;

public class SimulationBuilder {

    private String name;
    private String cpf;
    private String email;
    private BigDecimal amount;
    private Integer installments;
    private Boolean insurance;

    public SimulationBuilder name(String name) {
        this.name = name;
        return this;
    }

    public SimulationBuilder cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public SimulationBuilder email(String email) {
        this.email = email;
        return this;
    }

    public SimulationBuilder amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public SimulationBuilder installments(Integer installments) {
        this.installments = installments;
        return this;
    }

    public SimulationBuilder insurance(Boolean insurance) {
        this.insurance = insurance;
        return this;
    }

    public Simulation build() {
        return new Simulation(name, cpf, email, amount, installments, insurance);
    }
}