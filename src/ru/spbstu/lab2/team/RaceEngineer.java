package ru.spbstu.lab2.team;

import ru.spbstu.lab2.Repeat;

import java.util.Arrays;

public class RaceEngineer {
    private final String name;

    public RaceEngineer(String name) {
        this.name = name;
    }

    // публичные методы

    @Repeat(2)
    public void radio(Driver driver, String message) {
        System.out.println(name + " по радио для " + driver + ": " + message);
    }

    public void boxBox(Car car) {
        System.out.println(name + ": " + car + ", box box!");
    }

    public void lapTime(int lap, double seconds) {
        System.out.printf("%s: круг %d за %.3f с%n", name, lap, seconds);
    }

    // защищенные методы

    @Repeat(2)
    protected void pitStop(Car car, Tyre tyre, double seconds) {
        System.out.printf("%s: пит-стоп, %s, ставим %s, %.1f с%n", name, car, tyre, seconds);
    }

    @Repeat(1)
    protected void tyrePlan(Tyre[] tyres, int[] laps) {
        System.out.println(name + ": план на гонку " + Arrays.toString(tyres) + ", круги " + Arrays.toString(laps));
    }

    protected void checkWeather(boolean rain, int temperature) {
        System.out.println(name + ": дождь " + rain + ", температура трассы " + temperature);
    }

    // приватные методы

    @Repeat(3)
    private void gap(Driver ahead, long millis, char sector) {
        System.out.println(name + ": отставание от " + ahead + " " + millis + " мс, сектор " + sector);
    }

    @Repeat(1)
    private void readTelemetry(Telemetry telemetry, boolean drs) {
        // объект абстрактного класса создать нельзя, поэтому сюда может прийти null
        if (telemetry == null) {
            System.out.println(name + ": телеметрия недоступна, DRS " + drs);
            return;
        }
        System.out.println(name + ": скорость " + telemetry.getSpeed() + ", DRS " + drs);
    }

    private void teamOrder(Driver first, Driver second) {
        System.out.println(name + ": " + second + ", пропусти " + first);
    }
}
