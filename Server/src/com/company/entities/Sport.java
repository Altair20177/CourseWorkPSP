package com.company.entities;

import java.io.Serializable;

public class Sport extends Infocommunication implements Serializable {
    private String kindOfSport;
    private String name;

    public Sport(String fullDate, int freePlaces, int price, String kindOfSport, String name) {
        super(fullDate, freePlaces, price);
        this.kindOfSport = kindOfSport;
        this.name = name;
    }

    public Sport(int id, String fullDate, int freePlaces, int price, String kindOfSport, String name) {
        super(id, fullDate, freePlaces, price);
        this.kindOfSport = kindOfSport;
        this.name = name;
    }

    public Sport() {}

    public String getKindOfSport() {
        return kindOfSport;
    }

    public void setKindOfSport(String kindOfSport) {
        this.kindOfSport = kindOfSport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Sport{" +
                "id=" + id +
                ", fullDate='" + fullDate + '\'' +
                ", freePlaces=" + freePlaces +
                ", price=" + price +
                ", kindOfSport='" + kindOfSport + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
