package com.company.entities;

import java.io.Serializable;

public class Training extends Education implements Serializable {
    protected String curQualific;

    public Training(){
        super();
    }

    public Training (String fullDate, int freePlaces, String direction, String organizer, String curQualific){
        super(fullDate, freePlaces, direction, organizer);
        this.curQualific = curQualific;
    }

    public Training (int id, String fullDate, int freePlaces, String direction, String organizer, String curQualific){
        super(id, fullDate, freePlaces, direction, organizer);
        this.curQualific = curQualific;
    }

    public String getCurQualific() {
        return curQualific;
    }

    public void setCurQualific(String curQualific) {
        this.curQualific = curQualific;
    }

    @Override
    public String toString() {
        return "Training:" +
                "id = " + id +
                ", fullDate=" + fullDate +
                ", freePlaces=" + freePlaces +
                ", direction=" + direction +
                ", organizer=" + organizer +
                ", current qualification=" + curQualific;
    }
}
