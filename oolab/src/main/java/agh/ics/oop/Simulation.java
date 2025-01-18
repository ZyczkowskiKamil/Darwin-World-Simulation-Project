package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.MapVisualizer;
import agh.ics.oop.presenter.SimulationPresenter;

import java.io.FileNotFoundException;

public class Simulation {
    private final static Parameters parameters;
    private final SimulationPresenter presenter;
    private final GlobeMap map;

    private Thread thread;

    public Simulation(SimulationPresenter presenter, GlobeMap worldMap) {
        this.presenter = presenter;
        this.map = worldMap;
    }

    static {
        try {
            parameters = new Parameters();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private final static int NUMBER_OF_GRASS_GROWING_DAILY = parameters.NUMBER_OF_GRASS_GROWING_DAILY;
    private final static int MAP_REFRESH_TIME_MS = parameters.MAP_REFRESH_TIME_MS;

    private final static int WATER_CHANGE_DAYS = parameters.WATER_CHANGE_DAYS;
    
    public void run() {     //do zmiany po UI

//        System.out.println(mapVisualizer.draw());
        presenter.drawMap();

        int simulationDay = 1;

        while(map.numberOfAnimalsAlive() > 0) {
            map.removeDeadAnimals();
            map.moveAllAnimals();
            map.animalsEatGrass();
            map.reproducing();
            if (simulationDay % WATER_CHANGE_DAYS == 0) map.updateWater();
            map.placeGrasses(NUMBER_OF_GRASS_GROWING_DAILY);
            map.animalAging();
            presenter.mapChanged("Map changed");
            try {
                Thread.sleep(MAP_REFRESH_TIME_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Restore the interrupted status
                System.err.println("Simulation interrupted: " + e.getMessage());
            }

            System.out.println(simulationDay);
            simulationDay++;
        }



        System.out.println("Simulation finished");
    }

    public void runAsync() {
        thread = new Thread(() -> {
            try {
                run();
            } catch (Exception exception) {
            }
        });
        thread.start();

    }
}
