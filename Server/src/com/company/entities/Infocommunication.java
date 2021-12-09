package com.company.entities;

import java.io.Serializable;

public class Infocommunication extends Event implements Serializable {
    protected int price;

    public Infocommunication(String fullDate, int freePlaces, int price){
        super(fullDate,freePlaces);
        this.price = price;
    }

    public Infocommunication(int id, String fullDate, int freePlaces, int price) {
        super(id, fullDate, freePlaces);
        this.price = price;
    }

    public Infocommunication() {

    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
