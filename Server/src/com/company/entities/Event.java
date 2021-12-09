package com.company.entities;


import java.io.Serializable;

public class Event implements Serializable {
    protected int id;
    protected String fullDate;
    protected int freePlaces;

    public Event(int id, String fullDate, int freePlaces) {
        this.id = id;
        this.fullDate = fullDate;
        this.freePlaces = freePlaces;
    }

    public Event(String fullDate, int freePlaces) {
        this.fullDate = fullDate;
        this.freePlaces = freePlaces;
    }

    public Event() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullDate() {
        return fullDate;
    }

    public void setFullDate(String fullDate) {
        this.fullDate = fullDate;
    }

    public int getFreePlaces() {
        return freePlaces;
    }

    public void setFreePlaces(int freePlaces) {
        this.freePlaces = freePlaces;
    }
}
