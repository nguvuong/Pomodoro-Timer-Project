package com.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
    private int index;
    private Integer currSeconds = 0;
    private LocalDateTime now;
    private String Name;
    private String timeStudying;

    public String getTimeStudying() {
        return timeStudying;
    }

    public void setTimeStudying(String timeStudying) {
        this.timeStudying = timeStudying;
    }

    public Task(String name, int index) {
        this.now = LocalDateTime.now();
        this.Name = name;
    }

    public void setCurrSeconds(Integer currSeconds) {
        this.currSeconds = currSeconds;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getCurrSeconds() {
        return currSeconds;
    }

    public String getName() {
        return Name;
    }

    public static DateTimeFormatter getDtf() {
        return dtf;
    }

    public LocalDateTime getNow() {
        return now;
    }

    @Override
    public String toString() {
        return "Date: " + dtf.format(now) + " Task ";
    }

}
