package br.aps.fmu.tasklist.models;

import java.time.LocalDateTime;
import java.util.Calendar;

public class Task {

    private String title;
    private String description;
    private Calendar date;
    private boolean done;

    public Task() {
        this.title = "";
        this.description = "";
        this.date = Calendar.getInstance();
        this.done = false;
    }

    public Task(String title, String description, Calendar date, boolean done) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
