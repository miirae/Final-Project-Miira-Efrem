package com.company.gamestore.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "console")
public class Console implements Serializable {
    @Id
    @Column(name = "console_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int consoleId;

    @NotEmpty(message = "model cannot be null - must provide an amount")
    @Size(max = 50, message = "model can be up to 50 chars")
    private String model;

    @NotEmpty(message = "manufacturer cannot be null - must provide an amount")
    @Size(max = 50, message = "manufacturer can be up to 50 chars")
    private String manufacturer;

    @Size(max = 20, message = "memoryAmount can be up to 20 chars")
    private String memoryAmount;

    @Size(max = 20, message = "processor can be up to 20 chars")
    private String processor;

    @NotNull(message = "price cannot be null - must provide an amount")
    @Digits(integer = 5, fraction = 2, message = "price must have at most 3 integer digits and 2 fraction digits")
    private double price;

    @NotNull(message = "quantity cannot be null - must provide an amount")
    @Positive(message = "quantity must be a positive integer")
    private int quantity;

    public int getConsoleId() {
        return consoleId;
    }

    public void setConsoleId(int consoleId) {
        this.consoleId = consoleId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getmemoryAmount() {
        return memoryAmount;
    }

    public void setmemoryAmount(String memoryAmount) {
        this.memoryAmount = memoryAmount;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
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
    public String toString() {
        return "Console{" +
                "consoleId=" + consoleId +
                ", model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", memoryAmount='" + memoryAmount + '\'' +
                ", processor='" + processor + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Console console = (Console) o;
        return consoleId == console.consoleId && price == console.price && quantity == console.quantity && model.equals(console.model) && manufacturer.equals(console.manufacturer) && Objects.equals(memoryAmount, console.memoryAmount) && Objects.equals(processor, console.processor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(consoleId, model, manufacturer, memoryAmount, processor, price, quantity);
    }
}
