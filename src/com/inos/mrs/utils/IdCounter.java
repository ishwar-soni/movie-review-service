package com.inos.mrs.utils;

public class IdCounter {
    private static Integer idCounter = 0;

    public static Integer getId() {
        return idCounter++;
    }
}
