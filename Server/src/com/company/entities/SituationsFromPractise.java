package com.company.entities;

import java.io.Serializable;

public class SituationsFromPractise extends Education implements Serializable {
    private String situation;

    public SituationsFromPractise(int id, String fullDate, int freePlaces, String direction, String organizer, String situation) {
        super(id, fullDate, freePlaces, direction, organizer);
        this.situation = situation;
    }

    public SituationsFromPractise(String fullDate, int freePlaces, String direction, String organizer, String situation) {
        super(fullDate, freePlaces, direction, organizer);
        this.situation = situation;
    }

    public SituationsFromPractise() {}

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    @Override
    public String toString() {
        return "SituationsFromPractise{" +
                "id=" + id +
                ", fullDate='" + fullDate + '\'' +
                ", freePlaces=" + freePlaces +
                ", direction='" + direction + '\'' +
                ", organizer='" + organizer + '\'' +
                ", situation='" + situation + '\'' +
                '}';
    }
}
