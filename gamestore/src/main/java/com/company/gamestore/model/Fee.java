package com.company.gamestore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "fee")
public class Fee implements Serializable {
    // note backend product_type name is set to 50
    @Id
    @Column(name = "product_type")
    @NotNull(message = "fee cannot be null")
    @Size(max = 50, message = "product type must be 50 chars or less")
    private String productType;

    @Column(name = "fee")
    @NotNull(message = "fee cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "fee must be positive")
    @Digits(integer = 6, fraction = 2, message = "Price must have at most 6 integer digits and 2 fraction digits")
    private BigDecimal fee;

    public Fee(){}

    public Fee(String productType, BigDecimal fee){
        this.productType = productType;
        this.fee = fee;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fee)) return false;
        Fee fee1 = (Fee) o;
        return Objects.equals(productType, fee1.productType) && Objects.equals(fee, fee1.fee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productType, fee);
    }

    @Override
    public String toString() {
        return "Fee{" +
                "productType='" + productType + '\'' +
                ", fee=" + fee +
                '}';
    }
}
