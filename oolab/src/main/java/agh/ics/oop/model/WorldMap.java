package agh.ics.oop.model;

import java.util.List;

public interface WorldMap {
    void placeAnimal(Animal animal);
    void removeAnimal(Animal animal);

    boolean placeGrass(Grass grass);
    void removeGrass(Grass grass);

    boolean isWaterAt(Vector2d position);

    void move(Animal animal);

    List<Animal> animalsAt(Vector2d position);

    Grass grassAt(Vector2d position);

    Boundary getBoundary();

    WorldElement objectAt(Vector2d position);
}
