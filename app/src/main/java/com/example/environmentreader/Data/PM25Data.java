package com.example.environmentreader.Data;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class PM25Data {

    @Id
    long id;

    private String south;
    private String east;
    private String north;
    private String west;
    private String central;
    private String national;
    private String pm25time;

    public PM25Data() {
    }

    public PM25Data(String south, String north, String east, String west, String central, String national, String pm25time) {
        this.south = south;
        this.east = east;
        this.north = north;
        this.west = west;
        this.central = central;
        this.national = national;
        this.pm25time = pm25time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSouth() {
        return south;
    }

    public void setSouth(String south) {
        this.south = south;
    }

    public String getEast() {
        return east;
    }

    public void setEast(String east) {
        this.east = east;
    }

    public String getNorth() {
        return north;
    }

    public void setNorth(String north) {
        this.north = north;
    }

    public String getWest() {
        return west;
    }

    public void setWest(String west) {
        this.west = west;
    }

    public String getCentral() {
        return central;
    }

    public void setCentral(String central) {
        this.central = central;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public String getPm25time() {
        return pm25time;
    }

    public void setPm25time (String pm25time) {
        this.pm25time = pm25time;
    }

}