package com.company.entities;

import java.io.Serializable;

public class Tourism extends Infocommunication implements Serializable {
    private int duration;
    private String attraction;

    public Tourism(){
        super();
    }

    public Tourism(String fullDate, int freePlaces, int price, int duration, String attraction) {
        super(fullDate, freePlaces, price);
        this.duration = duration;
        this.attraction = attraction;
    }

    public Tourism(int id, String fullDate, int freePlaces, int price, int duration, String attraction) {
        super(id, fullDate, freePlaces, price);
        this.duration = duration;
        this.attraction = attraction;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAttraction() {
        return attraction;
    }

    public void setAttraction(String attraction) {
        this.attraction = attraction;
    }

    @Override
    public String toString() {
        return "Tourism{" +
                "id=" + id +
                ", fullDate='" + fullDate + '\'' +
                ", freePlaces=" + freePlaces +
                ", price=" + price +
                ", duration=" + duration +
                ", attraction='" + attraction + '\'' +
                '}';
    }
}
