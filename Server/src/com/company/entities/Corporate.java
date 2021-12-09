package com.company.entities;

import java.io.Serializable;

public class Corporate extends Infocommunication implements Serializable {
    private String restaurant;
    private String party;

    public Corporate(String fullDate, int freePlaces, int price, String restaurant, String party) {
        super(fullDate, freePlaces, price);
        this.restaurant = restaurant;
        this.party = party;
    }

    public Corporate(int id, String fullDate, int freePlaces, int price, String restaurant, String party) {
        super(id, fullDate, freePlaces, price);
        this.restaurant = restaurant;
        this.party = party;
    }

    public Corporate() {}

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    @Override
    public String toString() {
        return "Corporate{" +
                "id=" + id +
                ", fullDate='" + fullDate + '\'' +
                ", freePlaces=" + freePlaces +
                ", price=" + price +
                ", restaurant='" + restaurant + '\'' +
                ", party='" + party + '\'' +
                '}';
    }
}
