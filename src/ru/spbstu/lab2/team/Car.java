package ru.spbstu.lab2.team;

public class Car {
    private final Driver driver;
    private final String team;

    public Car(Driver driver, String team) {
        this.driver = driver;
        this.team = team;
    }

    @Override
    public String toString() {
        return "болид " + team + " (" + driver + ")";
    }
}
