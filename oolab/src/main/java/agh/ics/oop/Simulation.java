package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.presenter.SimulationPresenter;

import java.io.FileNotFoundException;

public class Simulation {
    private final SimulationPresenter presenter;
    private final GlobeMap map;
    private boolean running;


    int NUMBER_OF_GRASS_GROWING_DAILY;
    int MAP_REFRESH_TIME_MS;
    int WATER_CHANGE_DAYS;

    public Simulation(SimulationPresenter presenter, GlobeMap worldMap, Parameters parameters) {
        this.presenter = presenter;
        this.map = worldMap;
        this.NUMBER_OF_GRASS_GROWING_DAILY = parameters.NUMBER_OF_GRASS_GROWING_DAILY;
        this.MAP_REFRESH_TIME_MS = parameters.MAP_REFRESH_TIME_MS;
        this.WATER_CHANGE_DAYS = parameters.WATER_CHANGE_DAYS;
        this.running = true;
    }

    public void startStopSimulation() {
        running = !running;
    }

    public void run() {


        int simulationDay = 1;

        while(map.numberOfAnimalsAlive() > 0) {
            if (!running) {
                try {
                    Thread.sleep(MAP_REFRESH_TIME_MS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Simulation interrupted: " + e.getMessage());
                }
                continue;
            }
            map.removeDeadAnimals();
            map.moveAllAnimals();
            map.animalsEatGrass();
            map.reproducing();
            if (simulationDay % WATER_CHANGE_DAYS == 0) map.updateWater();
            map.placeGrasses(NUMBER_OF_GRASS_GROWING_DAILY);
            map.animalAging();
            presenter.mapChanged("Day: " + simulationDay + " " + map.returnAllStatistics());

            try {
                Thread.sleep(MAP_REFRESH_TIME_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Simulation interrupted: " + e.getMessage());
            }

            System.out.println(simulationDay);
            simulationDay++;
        }



        System.out.println("Simulation finished");
    }

    public void runAsync() {
        Thread thread = new Thread(() -> {
            try {
                run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();

    }
}
