package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoundaryTest {
    @Test
    void isInsideTest() {
        Boundary boundary = new Boundary(new Vector2d(0,0), new Vector2d(10,10));
        assertTrue(boundary.isInside(new Vector2d(0,0)));
        assertTrue(boundary.isInside(new Vector2d(10,10)));
        assertTrue(boundary.isInside(new Vector2d(0,10)));
        assertTrue(boundary.isInside(new Vector2d(10,0)));
        assertTrue(boundary.isInside(new Vector2d(5,5)));

        assertFalse(boundary.isInside(new Vector2d(-1, 5)));
        assertFalse(boundary.isInside(new Vector2d(0,-1)));
        assertFalse(boundary.isInside(new Vector2d(10,-1)));
        assertFalse(boundary.isInside(new Vector2d(0,-10)));
        assertFalse(boundary.isInside(new Vector2d(10,-10)));

    }
}
