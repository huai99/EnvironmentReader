package com.example.environmentreader.Data;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class TimeLogData {

    @Id
    long id;

    private String timelog;


    public TimeLogData() {
    }

    public TimeLogData(String timelog) {
        this.timelog = timelog;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTimelog() {
        return timelog;
    }

    public void setTimelog (String timelog) {
        this.timelog = timelog;
    }

}