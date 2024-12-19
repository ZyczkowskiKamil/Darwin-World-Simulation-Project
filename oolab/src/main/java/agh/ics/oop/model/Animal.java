package agh.ics.oop.model;

import java.util.Random;

public class Animal implements WorldElement {
    private MoveDirection animalOrientation;
    private Vector2d position;
    private final Genes genes;
    private int nextGene;

    public Animal(Vector2d position, Genes genes) {
        this.position = position;
        this.genes = genes;
        Random rand = new Random();
        this.animalOrientation = MoveDirection.valueToMoveDirection(rand.nextInt(8));

        nextGene = rand.nextInt(8);
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
        int rotationNumber = this.genes.getGenesList().get(nextGene);
        nextGene = (nextGene + 1) % 8;

        this.animalOrientation = MoveDirection.valueToMoveDirection((animalOrientation.moveDirectionToValue()+rotationNumber) % 8);
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
        }

    }
}
