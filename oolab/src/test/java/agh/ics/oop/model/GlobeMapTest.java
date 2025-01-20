package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class GlobeMapTest {

    static Parameters parameters;
    static {
        try {
            parameters = new Parameters();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    GlobeMap map = new GlobeMap(parameters);

    int grassAmount = map.getGrassNumber();
    int animalAmount = map.numberOfAnimalsAlive();


    @Test
    void placeInitialAnimals() {
        assertEquals(animalAmount, map.numberOfAnimalsAlive());
    }

    @Test
    void placeGrasses() {
        assertEquals(grassAmount, map.getGrassNumber());
    }

    @Test
    void animalsEatGrass() {
        map.animalsEatGrass();

        assertEquals(animalAmount, map.numberOfAnimalsAlive());
        assertEquals(grassAmount, map.getGrassNumber());
    }

    @Test
    void removeDeadAnimals() {
        map.removeDeadAnimals();

        assertEquals(animalAmount, map.numberOfAnimalsAlive());
    }

    @Test
    void numberOfAnimalsAlive() {
        assertEquals(animalAmount, map.numberOfAnimalsAlive());
    }


    @Test
    void getFreePlacesNumber() {
        assertEquals(parameters.MAP_WIDTH * parameters.MAP_HEIGHT - map.getWaterNumber() - map.getGrassNumber() - map.numberOfAnimalsAlive(),
                map.getFreePlacesNumber());
    }
}