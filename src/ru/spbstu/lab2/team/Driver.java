package ru.spbstu.lab2.team;

public class Driver {
    private final String name;
    private final int number;

    public Driver(String name, int number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public String toString() {
        return name + " #" + number;
    }
}
