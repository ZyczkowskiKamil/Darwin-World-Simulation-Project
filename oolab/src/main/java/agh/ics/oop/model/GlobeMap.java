package agh.ics.oop.model;

import java.util.*;

public class GlobeMap implements WorldMap {
    private final Map<Vector2d, ArrayList<Animal> > animals = new HashMap<>();
    private final Map<Vector2d, Grass> grasses = new HashMap<>();
    private final Boundary boundary;
    private final Boundary equatorBoundary;
    private final Map<Vector2d, Integer> waterSources = new HashMap<>(); // water source position and it's state of power
    private final Set<Vector2d> waterPlaces = new HashSet<>();

    private final int energyAddedAfterEatingGrass;
    private final int breedingReadyAnimalEnergy;
    private final int energyLostInReproduction;
    private final int waterAreasMinSize;
    private final int waterAreasMaxSize;
    private final int mapHeight;
    private final int mapWidth;

    int deadAnimalNumber = 0;
    int deadAnimalAgeSum = 0;


    public GlobeMap(Parameters parameters) {
        this.mapHeight = parameters.MAP_HEIGHT;
        this.mapWidth = parameters.MAP_WIDTH;
        int startAnimalNumber = parameters.START_ANIMAL_NUMBER;
        int startAnimalEnergy = parameters.START_ANIMAL_ENERGY;
        int grassStartAmount = parameters.GRASS_START_AMOUNT;
        int waterAreasNumber = parameters.WATER_AREAS_NUMBER;

        this.energyAddedAfterEatingGrass = parameters.ENERGY_ADDED_AFTER_EATING_GRASS;
        this.breedingReadyAnimalEnergy = parameters.BREEDING_READY_ANIMAL_ENERGY;
        this.energyLostInReproduction = parameters.ENERGY_LOST_IN_REPRODUCTION;

        this.waterAreasMinSize = parameters.WATER_AREAS_MIN_SIZE;
        this.waterAreasMaxSize = parameters.WATER_AREAS_MAX_SIZE;

        this.boundary = new Boundary(new Vector2d(0,0), new Vector2d(mapWidth -1, mapHeight -1));
        int equatorHeight = mapHeight / 5;
        this.equatorBoundary = new Boundary(new Vector2d(0, mapHeight /2-equatorHeight/2), new Vector2d(mapWidth -1, mapHeight /2+equatorHeight/2-1));

        placeGrasses(grassStartAmount);
        placeInitialWaterSources(waterAreasNumber);
        placeInitialAnimals(startAnimalNumber, startAnimalEnergy);
    }

    private void placeInitialWaterSources(int waterAreasToPlace) {
        List<Vector2d> availablePlaces = new ArrayList<>();

        for (int x = boundary.BOTTOM_LEFT().getX(); x <= boundary.TOP_RIGHT().getX(); x++) {
            for (int y = boundary.BOTTOM_LEFT().getY(); y <= boundary.TOP_RIGHT().getY(); y++) {
                availablePlaces.add(new Vector2d(x, y));
            }
        }

        Collections.shuffle(availablePlaces);

        for (int i = 0; i < waterAreasToPlace; i++) {
            try {
                Random random = new Random();
                int waterLevel = random.nextInt(waterAreasMaxSize-waterAreasMinSize+1)+waterAreasMinSize;
                waterSources.put(availablePlaces.get(i), waterLevel);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        updateWater();
    }

    public void updateWater() {
        waterPlaces.clear();
        for (Vector2d position : waterSources.keySet()) {
            // update water level
            Random random = new Random();
            int waterLevel = waterSources.get(position) + (random.nextInt(3) - 1); // +1 / 0 / -1
            waterLevel = Math.min(Math.max(waterLevel,waterAreasMinSize),waterAreasMaxSize);
            waterSources.put(position, waterLevel);

            // update water places
            for (int x = position.getX()-waterLevel+1; x <= position.getX()+waterLevel-1; x++) {
                for (int y = position.getY()-waterLevel+1; y <= position.getY()+waterLevel-1; y++) {
                    if (boundary.isInside(new Vector2d(x,y)) && Math.abs(x-position.getX()) + Math.abs(y-position.getY()) <= waterLevel-1) {
                        Vector2d waterPosition = new Vector2d(x,y);
                        waterPlaces.add(waterPosition);

                        for (Animal animal : animalsAt(waterPosition)) {
                            removeAnimal(animal);
                        }
                        if (grassAt(waterPosition) != null) {
                            removeGrass(grassAt(waterPosition));
                        }
                    }
                }
            }
        }
    }

    public boolean isWaterAt(Vector2d position) {
        return waterPlaces.contains(position);
    }

    private void placeInitialAnimals(int startAnimalNumber, int startAnimalEnergy) {
        List<Vector2d> availablePlaces = new ArrayList<>();

        for (int x = boundary.BOTTOM_LEFT().getX(); x <= boundary.TOP_RIGHT().getX(); x++) {
            for (int y = boundary.BOTTOM_LEFT().getY(); y <= boundary.TOP_RIGHT().getY(); y++) {
                if (!waterSources.containsKey(new Vector2d(x,y)))
                    availablePlaces.add(new Vector2d(x, y));
            }
        }

        Collections.shuffle(availablePlaces);

        for (int i = 0; i < startAnimalNumber; i++) {
            placeAnimal(new Animal(availablePlaces.get(i), new Genes(), startAnimalEnergy));
        }
    }

    public void placeGrasses(int grassesAmount) {
        List<Vector2d> availableNormalPlaces = new ArrayList<>();
        List<Vector2d> availablePreferredPlaces = new ArrayList<>();

        for (int x = boundary.BOTTOM_LEFT().getX(); x <= boundary.TOP_RIGHT().getX(); x++) {
            for (int y = boundary.BOTTOM_LEFT().getY(); y <= boundary.TOP_RIGHT().getY(); y++) {
                Vector2d position = new Vector2d(x, y);
                if (!grasses.containsKey(position) && !waterPlaces.contains(position)) {
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
    public WorldElement objectAt(Vector2d position) {
        if (animals.containsKey(position))
            return findWinningAnimal(position);
        if (grasses.containsKey(position)) return grasses.get(position);
        return null;
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
        deadAnimalAgeSum += animal.getAge();
        deadAnimalNumber ++;
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
        if (waterPlaces.contains(animal.getPosition())) animal.moveToPreviousPosition();
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
            return new ArrayList<>(animals.get(position));
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
                winningAnimal.addEnergy(energyAddedAfterEatingGrass);
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

    private Animal findWinningAnimal(Vector2d position) {
        ArrayList<Animal> animalList = animalsAt(position);
        if (animalList.isEmpty()) {
            return null;
        }
        Animal winningAnimal = animalList.getFirst();
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

            if (parent1.getEnergy() >= breedingReadyAnimalEnergy && parent2.getEnergy() >= breedingReadyAnimalEnergy) {

                Animal offspring = new Animal(
                        position,
                        parent1.getGenes().combineDuringReproducing(
                                parent1.getEnergy(),
                                parent2.getEnergy(),
                                parent2.getGenes()
                        ),
                        energyLostInReproduction * 2
                );
                parent1.removeEnergy(energyLostInReproduction);
                parent1.addKid();
                parent2.removeEnergy(energyLostInReproduction);
                parent2.addKid();

                placeAnimal(offspring);
            }
        }
    }

    public int getAnimalNumber() {
        int animalNumber = 0;
        for(ArrayList<Animal> animalList : animals.values()) {
            animalNumber += animalList.size();
        }
        return animalNumber;
    }

    public int getGrassNumber() {
        return grasses.keySet().size();
    }

    public int getWaterNumber() {
        return waterPlaces.size();
    }

    public int getFreePlacesNumber() {
        return mapHeight * mapWidth - getWaterNumber() - getGrassNumber() - getAnimalNumber();
    }

    public double getAverageAnimalEnergy() {
        Set<Vector2d> positions = new HashSet<>(animals.keySet());
        int energySum = 0;

        for (Vector2d position : positions) {
            ArrayList<Animal> animalList = new ArrayList<>(animalsAt(position));
            for (Animal animal : animalList) {
                energySum += animal.getEnergy();
            }
        }
        return Math.round((double) energySum / getAnimalNumber() * 100.0) / 100.0;
    }

    public double getAverageDeadAnimalAge() {
        if(deadAnimalNumber == 0)
            return 0;
        return Math.round((double) deadAnimalAgeSum / deadAnimalNumber * 100.0) / 100.0;
    }

    public double getAverageKidsAmount() {
        Set<Vector2d> positions = new HashSet<>(animals.keySet());
        int kidSum = 0;

        for (Vector2d position : positions) {
            ArrayList<Animal> animalList = new ArrayList<>(animalsAt(position));
            for (Animal animal : animalList) {
                kidSum += animal.getKidsAmount();
            }
        }
        return Math.round((double) kidSum / getAnimalNumber() * 100.0) / 100.0;
    }

}
