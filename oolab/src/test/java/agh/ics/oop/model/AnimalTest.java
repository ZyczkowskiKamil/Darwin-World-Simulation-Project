package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
    private final static Parameters PARAMETERS;
    static {
        try {
            PARAMETERS = new Parameters();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getPositionTest() {
        Animal animal = new Animal(new Vector2d(2,2), new Genes(), 50);
        assertEquals(new Vector2d(2,2), animal.getPosition());
    }

    @Test
    void getEnergyTest() {
        Animal animal = new Animal(new Vector2d(2,2), new Genes(), 50);
        assertEquals(50, animal.getEnergy());
    }

    @Test
    void animalAddAgeTest() {
        Animal animal = new Animal(new Vector2d(2,2), new Genes(), 50);
        int ageBefore = animal.getAge();
        animal.addAge(3);
        assertEquals(ageBefore + 3, animal.getAge());
    }

    @Test
    void animalAddKidTest() {
        Animal animal = new Animal(new Vector2d(2,2), new Genes(), 50);
        int kidsBefore = animal.getKidsAmount();
        animal.addKid();
        assertEquals(kidsBefore + 1, animal.getKidsAmount());
    }

    @Test
    void animalAddEnergyTest() {
        Animal animal = new Animal(new Vector2d(2,2), new Genes(), 50);
        int energyBefore = animal.getEnergy();
        animal.addEnergy(77);
        assertEquals(energyBefore + 77, animal.getEnergy());
    }

    @Test
    void animalRemoveEnergyTest() {
        Animal animal = new Animal(new Vector2d(2,2), new Genes(), 50);
        int energyBefore = animal.getEnergy();
        animal.removeEnergy(77);
        assertEquals(energyBefore - 77, animal.getEnergy());
    }

    @Test
    void animalWinsFightTest() {
        Animal animal1 = new Animal(new Vector2d(2,2), new Genes(), 50);
        Animal animal2 = new Animal(new Vector2d(2,2), new Genes(), 55);
        Animal animal3 = new Animal(new Vector2d(2,2), new Genes(), 67);

        assertFalse(animal1.winsFight(animal2));
        assertTrue(animal3.winsFight(animal2));
        assertTrue(animal3.winsFight(animal1));
    }
}
