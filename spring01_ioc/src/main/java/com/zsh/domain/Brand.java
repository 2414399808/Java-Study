package com.zsh.domain;

public class Brand {
    private String name;
    private double price;

    public Brand() {
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Brand(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
