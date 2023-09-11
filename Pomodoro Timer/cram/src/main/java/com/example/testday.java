package com.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class testday {
    public static void main(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
    }
}
