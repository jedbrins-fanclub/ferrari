package dk.eamv.ferrari.scenes.car;

import java.text.DecimalFormat;

public class Car {

    private int id;
    private String model;
    private int year;
    private double price;

    public Car(int id, String model, int year, double price) {
        this.id = id;
        this.model = model;
        this.year = year;
        this.price = price;
    }

    public Car(String model, int year, double price) {
        this.model = model;
        this.year = year;
        this.price = price;
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
}
