package com.company.entities;

import java.io.Serializable;

public class MasterClass extends Education implements Serializable {
    private String master;

    public MasterClass(int id, String fullDate, int freePlaces, String direction, String organizer, String master) {
        super(id, fullDate, freePlaces, direction, organizer);
        this.master = master;
    }

    public MasterClass(String fullDate, int freePlaces, String direction, String organizer, String master) {
        super(fullDate, freePlaces, direction, organizer);
        this.master = master;
    }

    public MasterClass() {}

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    @Override
    public String toString() {
        return "MasterClass{" +
                "id=" + id +
                ", fullDate='" + fullDate + '\'' +
                ", freePlaces=" + freePlaces +
                ", direction='" + direction + '\'' +
                ", organizer='" + organizer + '\'' +
                ", master='" + master + '\'' +
                '}';
    }
}
