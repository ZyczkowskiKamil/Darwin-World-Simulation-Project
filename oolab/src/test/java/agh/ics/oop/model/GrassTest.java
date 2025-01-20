package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrassTest {
    @Test
    void getPositionTest() {
        Grass grass = new Grass(new Vector2d(3,4));
        assertEquals(new Vector2d(3,4), grass.getPosition());
    }

    @Test
    void toStringTest() {
        Grass grass = new Grass(new Vector2d(3,4));
        assertEquals(".", grass.toString());
    }
}
