package agh.ics.oop.model;

import java.io.FileNotFoundException;
import java.util.Random;

public class Animal implements WorldElement {

    private final static Parameters PARAMETERS;
    static {
        try {
            PARAMETERS = new Parameters();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private MoveDirection animalOrientation;
    private Vector2d position;
    private Vector2d previousPosition;
    private final Genes genes;
    private int nextGene;

    private int kidsAmount;
    private int age;
    private int energy;

    private static final int ENERGY_NEEDED_FOR_MOVEMENT = PARAMETERS.ENERGY_NEEDED_FOR_MOVEMENT;
    private static final int GENES_LENGTH = PARAMETERS.GENES_LENGTH;

    public Animal(Vector2d position, Genes genes, int energy){
        this.position = position;
        this.previousPosition = position;
        this.genes = genes;
        this.energy = energy;
        this.kidsAmount = 0;
        this.age = 0;

        Random rand = new Random();
        this.animalOrientation = MoveDirection.valueToMoveDirection(rand.nextInt(8));

        nextGene = rand.nextInt(GENES_LENGTH);
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public MoveDirection getAnimalOrientation() {
        return animalOrientation;
    }

    @Override
    public String toString() {
        return "A";
    }

    public void move(Boundary boundary) {
        this.previousPosition = position;
        this.removeEnergy(ENERGY_NEEDED_FOR_MOVEMENT);

        int rotationNumber = this.genes.getGenesList().get(nextGene);
        nextGene = (nextGene + 1) % GENES_LENGTH;

        this.animalOrientation = MoveDirection.valueToMoveDirection((animalOrientation.moveDirectionToValue()+rotationNumber) % 8);
        Vector2d originalPosition = this.position;
        this.position = this.position.add(animalOrientation.toUnitVector());

        if (this.position.getX() < boundary.BOTTOM_LEFT().getX()) {
            // out of left bound
            this.position = new Vector2d(boundary.TOP_RIGHT().getX(), this.position.getY());
        }
        if (this.position.getX() > boundary.TOP_RIGHT().getX()) {
            // out of right bound
            this.position = new Vector2d(boundary.BOTTOM_LEFT().getX(), this.position.getY());
        }
        if (this.position.getY() > boundary.TOP_RIGHT().getY() || this.position.getY() < boundary.BOTTOM_LEFT().getY()) {
            // out of upper or bottom bound
            this.animalOrientation = this.animalOrientation.getOppositeDirection();
            this.position = originalPosition;
        }
    }

    public void moveToPreviousPosition() {
        position = previousPosition;
    }

    public int getEnergy() {
        return energy;
    }

    public int getAge() {
        return age;
    }

    public void addAge(int ageToAdd) {
        this.age += ageToAdd;
    }

    public int getKidsAmount() {
        return kidsAmount;
    }

    public void addKid() {
        kidsAmount++;
    }

    public Genes getGenes () {
        return this.genes;
    }

    public void addEnergy(int energyAmount) {
        this.energy += energyAmount;
    }

    public void removeEnergy(int energyAmount) {
        this.energy -= energyAmount;
    }

    public boolean winsFight(Animal otherAnimal) {
        if (this.energy > otherAnimal.getEnergy()) return true;
        else if (this.energy < otherAnimal.getEnergy()) return false;
        // energy1 == energy2

        if (this.age > otherAnimal.getAge()) return true;
        else if (this.age < otherAnimal.getAge()) return false;
        // age1 == age2

        if (this.kidsAmount > otherAnimal.getKidsAmount()) return true;
        else if (this.kidsAmount < otherAnimal.getKidsAmount()) return false;
        // kids1 == kids2

        Random rand = new Random();
        return rand.nextBoolean();
    }
}
