package com.company.entities;

import java.io.Serializable;

public class Education extends Event implements Serializable {
    protected String direction;
    protected String organizer;

    public Education(int id, String fullDate, int freePlaces, String direction, String organizer){
        super(id,fullDate,freePlaces);
        this.direction = direction;
        this.organizer = organizer;
    }

    public Education(String fullDate, int freePlaces, String direction, String organizer){
        super(fullDate,freePlaces);
        this.direction = direction;
        this.organizer = organizer;
    }

    public Education() {
        super();
    }

    public String getDirection() {
        return direction;
    }
    public String getOrganizer() {
        return organizer;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

}
