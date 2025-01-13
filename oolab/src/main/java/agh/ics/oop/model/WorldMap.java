package agh.ics.oop.model;

import java.util.List;

public interface WorldMap {
    void placeAnimal(Animal animal);
    void removeAnimal(Animal animal);

    boolean placeGrass(Grass grass);
    void removeGrass(Grass grass);

    boolean isWaterAt(Vector2d position);

    /**
     * Moves an animal (if it is present on the map) according to specified direction.
     * If the move is not possible, this method has no effect.
     */
    void move(Animal animal);

    /**
     * Return an animal at a given position.
     *
     * @param position The position of the animal.
     * @return animal or null if the position is not occupied.
     */

    List<Animal> animalsAt(Vector2d position);

    Grass grassAt(Vector2d position);

    Boundary getBoundary();

    WorldElement objectAt(Vector2d position);
}
