package agh.ics.oop.model;

public record Boundary(Vector2d BOTTOM_LEFT, Vector2d TOP_RIGHT) {
    public boolean isInside(Vector2d point) {
        return BOTTOM_LEFT.precedes(point) && TOP_RIGHT.follows(point);
    }
}