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



    @Test
    void placeInitialAnimals() {
        assertEquals(parameters.START_ANIMAL_NUMBER, map.numberOfAnimalsAlive());
    }

    @Test
    void placeGrasses() {
        assertEquals(parameters.GRASS_START_AMOUNT, map.getGrassNumber());
    }

    @Test
    void animalsEatGrass() {
        map.animalsEatGrass();

        assertEquals(parameters.START_ANIMAL_NUMBER, map.numberOfAnimalsAlive());
        assertEquals(parameters.GRASS_START_AMOUNT, map.getGrassNumber());
    }

    @Test
    void removeDeadAnimals() {
        map.removeDeadAnimals();

        assertEquals(parameters.START_ANIMAL_NUMBER, map.numberOfAnimalsAlive());
    }

    @Test
    void numberOfAnimalsAlive() {
        assertEquals(parameters.START_ANIMAL_NUMBER, map.numberOfAnimalsAlive());
    }


    @Test
    void getFreePlacesNumber() {
        assertEquals(parameters.MAP_WIDTH * parameters.MAP_HEIGHT - map.getWaterNumber() - map.getGrassNumber() - map.numberOfAnimalsAlive(),
                map.getFreePlacesNumber());
    }
}