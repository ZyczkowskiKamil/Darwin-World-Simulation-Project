package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.MapVisualizer;

import java.io.FileNotFoundException;

public class Simulation {
    static Parameters parameters;

    static {
        try {
            parameters = new Parameters();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static int MAP_WIDTH = parameters.MAP_WIDTH;
    static int MAP_HEIGHT = parameters.MAP_HEIGHT;
    static int GRASS_START_AMOUNT = parameters.GRASS_START_AMOUNT;
    static int NUMBER_OF_GRASS_GROWING_DAILY = parameters.NUMBER_OF_GRASS_GROWING_DAILY;
    GlobeMap map = new GlobeMap(MAP_WIDTH, MAP_HEIGHT, GRASS_START_AMOUNT);
    MapVisualizer mapVisualizer = new MapVisualizer(map);
    public void run() {     //do zmiany po UI
        System.out.println(mapVisualizer.draw());

        while(map.numberOfAnimalsAlive() > 0) {
            map.removeDeadAnimals();
            map.moveAllAnimals();
            map.animalsEatGrass();
            map.reproducing();
            map.placeGrasses(NUMBER_OF_GRASS_GROWING_DAILY);
            map.animalAging();
            System.out.println(mapVisualizer.draw());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Restore the interrupted status
                System.err.println("Simulation interrupted: " + e.getMessage());
            }
        }
        System.out.println("Simulation finished");
    }
}
