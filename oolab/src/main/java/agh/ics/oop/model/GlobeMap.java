package agh.ics.oop.model;

import java.util.*;

public class GlobeMap implements WorldMap {
    private final Map<Vector2d, ArrayList<Animal> > animals = new HashMap<>();
    private final Map<Vector2d, Grass> grasses = new HashMap<>();
    private final Boundary boundary;
    private final Boundary equatorBoundary;

    private static final int ENERGY_ADDED_AFTER_EATING_GRASS = 3; // Jako parametr


    public GlobeMap(int width, int height, int grassesStartAmount) {
        this.boundary = new Boundary(new Vector2d(0,0), new Vector2d(width-1,height-1));

        int equatorHeight = height / 5;
        this.equatorBoundary = new Boundary(new Vector2d(0,height/2-equatorHeight/2), new Vector2d(width-1, height/2+equatorHeight/2-1));

        placeGrasses(grassesStartAmount);
        placeGrasses(grassesStartAmount);
        placeGrasses(grassesStartAmount);
    }

    private void placeGrasses(int grassesAmount) {
        List<Vector2d> availableNormalPlaces = new ArrayList<>();
        List<Vector2d> availablePreferredPlaces = new ArrayList<>();

        for (int x = boundary.BOTTOM_LEFT().getX(); x <= boundary.TOP_RIGHT().getX(); x++) {
            for (int y = boundary.BOTTOM_LEFT().getY(); y <= boundary.TOP_RIGHT().getY(); y++) {
                Vector2d position = new Vector2d(x, y);
                if (!grasses.containsKey(position)) {
                    if (equatorBoundary.isInside(position))
                        availablePreferredPlaces.add(position);
                    else
                        availableNormalPlaces.add(position);
                }
            }
        }

        int preferredPlacesToGet = 0;
        Random rand = new Random();
        for (int i = 0; i < grassesAmount; i++) {
            if (rand.nextInt(100) < 80) preferredPlacesToGet++;
        }

        Collections.shuffle(availablePreferredPlaces);
        Collections.shuffle(availableNormalPlaces);

        for (int i = 0; i < preferredPlacesToGet && i < availablePreferredPlaces.size(); i++) {
            grasses.put(availablePreferredPlaces.get(i), new Grass(availablePreferredPlaces.get(i)));
        }
        int grassesPlaced = Math.min(preferredPlacesToGet, availablePreferredPlaces.size());
        for (int i = 0; i < grassesAmount-grassesPlaced && i < availableNormalPlaces.size(); i++) {
            grasses.put(availableNormalPlaces.get(i), new Grass(availableNormalPlaces.get(i)));
        }
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

    private void animalsEatGrass() {
        for (Vector2d position : animals.keySet()) {
            if (!grasses.containsKey(position)) continue;

            ArrayList<Animal> animalList = animalsAt(position);
            Animal winningAnimal = animalList.getFirst();
            for (Animal animal : animalList) {
                if (animal.winsFight(winningAnimal)) winningAnimal = animal;
            }

            grasses.remove(position);
            winningAnimal.addEnergy(ENERGY_ADDED_AFTER_EATING_GRASS);
        }
    }

    private void removeDeadAnimals() {
        for (Vector2d position : animals.keySet()) {
            ArrayList<Animal> animalList = animalsAt(position);
            ArrayList<Animal> aliveAnimals = new ArrayList<>();

            for (Animal animal : animalList) {
                if (animal.getEnergy() > 0)
                    aliveAnimals.add(animal);
            }

            animals.put(position, aliveAnimals);
        }
    }
}
