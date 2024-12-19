package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobeMap implements WorldMap {
    private final Map<Vector2d, ArrayList<Animal> > animals = new HashMap<>();
    private final Map<Vector2d, Grass> grasses = new HashMap<>();
    private final Boundary boundary;




    public GlobeMap(int width, int height) {
        this.boundary = new Boundary(new Vector2d(0,0), new Vector2d(width,height));
    }

    @Override
    public Boundary getBoundary() {
        return this.boundary;
    }

    @Override
    public void placeAnimal(Animal animal) {
        ArrayList<Animal> animalList = animalsAt(animal.getPosition());
        animalList.add(animal);
        animals.put(animal.getPosition(), animalList);
    }

    @Override
    public void removeAnimal(Animal animal) {
        ArrayList<Animal> animalList = animalsAt(animal.getPosition());
        animalList.remove(animal);
        animals.put(animal.getPosition(), animalList);
    }

    @Override
    public boolean placeGrass(Grass grass) {
        if (grassAt(grass.getPosition()) == null) {
            grasses.put(grass.getPosition(), grass);
            return true;
        }
        return false;
    }

    @Override
    public void removeGrass(Grass grass) {
        grasses.remove(grass.getPosition());
    }

    @Override
    public void move(Animal animal) {
        this.removeAnimal(animal);
        animal.move(boundary);
        this.placeAnimal(animal);
    }

    @Override
    public ArrayList<Animal> animalsAt(Vector2d position) {
        if (animals.containsKey(position))
            return animals.get(position);
        return new ArrayList<>();
    }

    @Override
    public Grass grassAt(Vector2d position) {
        if (grasses.containsKey(position)) return grasses.get(position);
        return null;
    }
}
