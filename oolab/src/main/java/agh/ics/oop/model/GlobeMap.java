package agh.ics.oop.model;

import java.io.FileNotFoundException;
import java.util.*;

public class GlobeMap implements WorldMap {
    private final Map<Vector2d, ArrayList<Animal> > animals = new HashMap<>();
    private final Map<Vector2d, Grass> grasses = new HashMap<>();
    private final Boundary boundary;
    private final Boundary equatorBoundary;


    static Parameters parameters;
    static {
        try {
            parameters = new Parameters();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final int ENERGY_ADDED_AFTER_EATING_GRASS = parameters.ENERGY_ADDED_AFTER_EATING_GRASS;
    private static final int START_ANIMAL_NUMBER = parameters.START_ANIMAL_NUMBER;
    private static final int START_ANIMAL_ENERGY = parameters.START_ANIMAL_ENERGY;
    private static final int BREEDING_READY_ANIMAL_ENERGY = parameters.BREEDING_READY_ANIMAL_ENERGY;
    private static final int ENERGY_LOST_IN_REPRODUCTION = parameters.ENERGY_LOST_IN_REPRODUCTION;


    public GlobeMap(int width, int height, int grassesStartAmount) {
        this.boundary = new Boundary(new Vector2d(0,0), new Vector2d(width-1,height-1));

        int equatorHeight = height / 5;
        this.equatorBoundary = new Boundary(new Vector2d(0,height/2-equatorHeight/2), new Vector2d(width-1, height/2+equatorHeight/2-1));

        placeGrasses(grassesStartAmount);
//        placeGrasses(grassesStartAmount);
//        placeGrasses(grassesStartAmount);

        createFirstAnimals();
    }

    public void placeGrasses(int grassesAmount) {
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

    public void moveAllAnimals() {
        Set<Vector2d> positions = new HashSet<>(animals.keySet());

        for (Vector2d position : positions) {
            ArrayList<Animal> animalList = new ArrayList<>(animalsAt(position));
            for (Animal animal : animalList) {
                move(animal);
            }
        }
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

    public void animalsEatGrass() {
        Set<Vector2d> positions = new HashSet<>(animals.keySet());

        for (Vector2d position : positions) {
            if (!grasses.containsKey(position)) {
                continue;
            }

            Animal winningAnimal = findWinningAnimal(position);

            if (winningAnimal != null) {
                grasses.remove(position);
                winningAnimal.addEnergy(ENERGY_ADDED_AFTER_EATING_GRASS);
            }
        }
    }

    public void removeDeadAnimals() {
        Set<Vector2d> positions = new HashSet<>(animals.keySet()); // Create a copy of the key set

        for (Vector2d position : positions) {
            ArrayList<Animal> animalList = animalsAt(position);
            animalList.removeIf(animal -> animal.getEnergy() <= 0);  // Remove dead animals directly

            if (animalList.isEmpty()) {
                animals.remove(position);  // Remove entry if no animals remain
            } else {
                animals.put(position, animalList);  // Update the list (optional depending on implementation)
            }
        }
    }

    private void createFirstAnimals() {
        List<Vector2d> availablePlaces = new ArrayList<>();

        for (int x = boundary.BOTTOM_LEFT().getX(); x <= boundary.TOP_RIGHT().getX(); x++) {
            for (int y = boundary.BOTTOM_LEFT().getY(); y <= boundary.TOP_RIGHT().getY(); y++) {
                availablePlaces.add(new Vector2d(x, y));
            }
        }

        Collections.shuffle(availablePlaces);

        for (int i = 0; i < START_ANIMAL_NUMBER; i++) {
            placeAnimal(new Animal(availablePlaces.get(i), new Genes(), START_ANIMAL_ENERGY));
        }
    }

    private Animal findWinningAnimal(Vector2d position) {
        ArrayList<Animal> animalList = animalsAt(position);
        if (animalList.isEmpty()) {
            return null;
        }
        Animal winningAnimal = animalList.get(0);
        for (Animal animal : animalList) {
            if (animal.winsFight(winningAnimal)) winningAnimal = animal;
        }
        return winningAnimal;
    }

    public int numberOfAnimalsAlive() {
        int count = 0;
        for (ArrayList<Animal> animalList : animals.values()) {
            count += animalList.size();  // Sum the size of each list of animals
        }
        return count;
    }

    public void animalAging() {
        Set<Vector2d> positions = new HashSet<>(animals.keySet());

        for (Vector2d position : positions) {
            ArrayList<Animal> animalList = new ArrayList<>(animalsAt(position));
            for (Animal animal : animalList) {
                animal.addAge(1);
            }
        }
    }

    public void reproducing() {
        Set<Vector2d> positions = new HashSet<>(animals.keySet());

        for (Vector2d position : positions) {
            if (animalsAt(position).size() < 2) {
                continue;
            }

            Animal parent1 = findWinningAnimal(position);
            removeAnimal(parent1);
            Animal parent2 = findWinningAnimal(position);
            placeAnimal(parent1);

            if (parent1.getEnergy() >= BREEDING_READY_ANIMAL_ENERGY && parent2.getEnergy() >= BREEDING_READY_ANIMAL_ENERGY) {

                Animal offspring = new Animal(
                        position,
                        parent1.getGenes().combineDuringReproducing(
                                parent1.getEnergy(),
                                parent2.getEnergy(),
                                parent2.getGenes()
                        ),
                        ENERGY_LOST_IN_REPRODUCTION * 2
                );
                parent1.removeEnergy(ENERGY_LOST_IN_REPRODUCTION);
                parent2.removeEnergy(ENERGY_LOST_IN_REPRODUCTION);

                placeAnimal(offspring);
            }
        }
    }
}
