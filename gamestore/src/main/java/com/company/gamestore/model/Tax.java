package com.company.gamestore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "tax")
public class Tax implements Serializable {

    @Id
    @Column(name = "state")
    @Size(max = 2, message = "State abbreviation cannot be longer than two characters")
    private String state;

    @Column(name = "rate")
    @NotNull(message = "Tax rate cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "tax rate must be positive")
    @Digits(integer = 6, fraction = 2, message = "Price must have at most 6 integer digits and 2 fraction digits")
    private BigDecimal rate;

    public Tax(){}

    public Tax(String state, BigDecimal rate){
        this.state = state;
        this.rate = rate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public String getState() {
        return state;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tax)) return false;
        Tax tax = (Tax) o;
        return Objects.equals(state, tax.state) && Objects.equals(rate, tax.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, rate);
    }

    @Override
    public String toString() {
        return "Tax{" +
                "state='" + state + '\'' +
                ", rate=" + rate +
                '}';
    }
}
