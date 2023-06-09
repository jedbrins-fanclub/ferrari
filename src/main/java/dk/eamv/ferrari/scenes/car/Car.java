package dk.eamv.ferrari.scenes.car;

import java.util.ArrayList;

// Made by: Mikkel
public class Car {
    private int id;
    private String model;
    private int year;
    private double price;
    private CarStatus status;

    public Car(int id, String model, int year, double price, CarStatus status) {
        this.id = id;
        this.model = model;
        this.year = year;
        this.price = price;
        this.status = status;
    }

    // Overloaded to allow for CREATE operations. ID is removed since its autogenerated on INSERT.
    public Car(String model, int year, double price) {
        this(0, model, year, price, CarStatus.ACTIVE);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CarStatus getStatus() {
        return status;
    }

    public void setStatus(CarStatus status) {
        this.status = status;
    }

    public ArrayList<String> getProperties() {
        ArrayList<String> properties = new ArrayList<String>();
        properties.add(String.valueOf(year));
        properties.add(model);
        properties.add(String.valueOf(price));

        return properties;
    }

    @Override
    public String toString() {
        return String.format("%-15s %-5d %,.0f", model, year, price);
    }
}
