package com.company.gamestore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "tshirt")
public class Tshirt implements Serializable {

    @Id
    @Column(name = "tshirt_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tshirtId;
    @NotEmpty(message = "You must supply a value for size.")
    @Size(max = 20, message = "Tshirt size must be 20 chars or less")
    private String size;
    @NotEmpty(message = "You must supply a value for color.")
    @Size(max = 20, message = "Tshirt color must be 20 chars or less")
    private String color;
    @NotEmpty(message = "You must supply a value for description.")
    @Size(max = 255, message = "Tshirt description must be 255 chars or less")
    private String description;
    @NotNull(message = "Price must not be null")
    @Digits(integer = 5, fraction = 2, message = "Price must have at most 3 integer digits and 2 fraction digits")
    private double price;
    @Positive(message = "quantity must be a positive integer")
    private int quantity;

    public int getTshirtId() {
        return tshirtId;
    }

    public void setTshirtId(int tshirtId) {
        this.tshirtId = tshirtId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tshirt tshirt = (Tshirt) o;
        return tshirtId == tshirt.tshirtId && Double.compare(tshirt.price, price) == 0 && quantity == tshirt.quantity && Objects.equals(size, tshirt.size) && Objects.equals(color, tshirt.color) && Objects.equals(description, tshirt.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tshirtId, size, color, description, price, quantity);
    }

    @Override
    public String toString() {
        return "Tshirt{" +
                "tshirtId=" + tshirtId +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}