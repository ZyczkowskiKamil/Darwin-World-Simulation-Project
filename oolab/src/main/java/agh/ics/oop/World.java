package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.MapVisualizer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;



public class World {
    public World(){
    }

    public static void main(String[] args) throws FileNotFoundException { //do zmiany po UI
        run();
    }
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
    public static void run() throws FileNotFoundException {         //do zmiany po UI
        Simulation simulation = new Simulation();
        simulation.run();
//        GlobeMap map = new GlobeMap(MAP_WIDTH,MAP_HEIGHT, GRASS_START_AMOUNT);
//        MapVisualizer mapVisualizer = new MapVisualizer(map);
//
//        System.out.println(mapVisualizer.draw());
//
//        map.moveAllAnimals();
//
//        map.placeAnimal(new Animal(new Vector2d(2,2), new Genes(), -10));
//        map.placeAnimal(new Animal(new Vector2d(2,2), new Genes(), -10));
//        System.out.println(mapVisualizer.draw());
//        System.out.println(map.numberOfAnimalsAlive());
//        map.animalAging();
//        map.reproducing();
//        map.removeDeadAnimals();
//        map.animalsEatGrass();
//
//        System.out.println(mapVisualizer.draw());
    }
}
