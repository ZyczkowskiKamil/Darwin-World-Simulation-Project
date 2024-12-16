package agh.ics.oop.model;

import java.util.ArrayList;

public class Animal implements WorldElement {
    private MoveDirection animalOrientation = MoveDirection.NORTH;
    private Vector2d position;
    private ArrayList<Integer> genes = new ArrayList<>();

    public Animal(Vector2d position, ArrayList<Integer> genes) {
        this.position = position;
        this.genes = genes;
    }

    private void setOrientation(MoveDirection orientation) {
        this.animalOrientation = orientation;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "A";
    }
}
