package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {

    @Test
    public void toStringTest() {
        // test 1
        Vector2d my_vector = new Vector2d(2,3);
        assertEquals("(2,3)", my_vector.toString());

        // test 2
        my_vector = new Vector2d(1,2);
        assertEquals("(1,2)", my_vector.toString());

        // test 3
        my_vector = new Vector2d(-1,0);
        assertEquals("(-1,0)", my_vector.toString());
    }

    @Test
    public void precedesTest() {
        Vector2d my_vector = new Vector2d(2,3);
        assertTrue(my_vector.precedes(my_vector));

        my_vector = new Vector2d(0,0);
        assertTrue(my_vector.precedes(new Vector2d(1,1)));
        assertTrue(my_vector.precedes(new Vector2d(0,1)));
        assertTrue(my_vector.precedes(new Vector2d(1,0)));
        assertTrue(my_vector.precedes(new Vector2d(0,0)));

        assertFalse(my_vector.precedes(new Vector2d(-1,-1)));
        assertFalse(my_vector.precedes(new Vector2d(0,-1)));
        assertFalse(my_vector.precedes(new Vector2d(-1,0)));
        assertFalse(my_vector.precedes(new Vector2d(-20,-1)));
    }

    @Test
    public void followsTest() {
        Vector2d my_vector = new Vector2d(2,3);
        // vector follows itself
        assertTrue(my_vector.follows(my_vector));

        my_vector = new Vector2d(0,0);
        assertTrue(my_vector.follows(new Vector2d(0,0)));
        assertTrue(my_vector.follows(new Vector2d(-1,-1)));
        assertTrue(my_vector.follows(new Vector2d(0,-1)));
        assertTrue(my_vector.follows(new Vector2d(-1,0)));
        assertTrue(my_vector.follows(new Vector2d(-20,-1)));

        assertFalse(my_vector.follows(new Vector2d(1,1)));
        assertFalse(my_vector.follows(new Vector2d(0,1)));
        assertFalse(my_vector.follows(new Vector2d(1,0)));
    }

    @Test
    public void addTest() {
        Vector2d my_vector = new Vector2d(0,0);
        assertEquals(my_vector, my_vector.add(my_vector));
        assertEquals(my_vector, my_vector.add(new Vector2d(0,0)));
        assertEquals(new Vector2d(1,2), my_vector.add(new Vector2d(1,2)));
        my_vector = new Vector2d(1,2);
        assertEquals(new Vector2d(2,3), my_vector.add(new Vector2d(1,1)));
        assertEquals(new Vector2d(-3,3), my_vector.add(new Vector2d(-4,1)));
        assertEquals(new Vector2d(-20,-30), my_vector.add(new Vector2d(-21,-32)));
    }

    @Test
    public void subtractTest() {
        Vector2d my_vector = new Vector2d(0,0);
        assertEquals(my_vector, my_vector.subtract(my_vector));
        assertEquals(my_vector, my_vector.subtract(new Vector2d(0,0)));
        assertEquals(new Vector2d(1,2), my_vector.subtract(new Vector2d(-1,-2)));

        my_vector = new Vector2d(2,3);
        assertEquals(new Vector2d(1,2), my_vector.subtract(new Vector2d(1,1)));
        assertEquals(new Vector2d(-3,3), my_vector.subtract(new Vector2d(5,0)));
        assertEquals(new Vector2d(40,-30), my_vector.subtract(new Vector2d(-38,33)));
        assertEquals(new Vector2d(-30,40), my_vector.subtract(new Vector2d(32,-37)));
    }

    @Test
    public void upperRightTest() {
        assertEquals(new Vector2d(2,2), new Vector2d(1,2).upperRight(new Vector2d(2,1)));
        assertEquals(new Vector2d(-2,-2), new Vector2d(-2,-2).upperRight(new Vector2d(-22,-11)));
        assertEquals(new Vector2d(2,-2), new Vector2d(-10,-10).upperRight(new Vector2d(2,-2)));
        assertEquals(new Vector2d(-2,2), new Vector2d(-2,2).upperRight(new Vector2d(-22,-11)));
        assertEquals(new Vector2d(0,0), new Vector2d(0,0).upperRight(new Vector2d(0,0)));
    }

    @Test
    public void lowerLeftTest() {
        assertEquals(new Vector2d(-2,-2), new Vector2d(-1,-2).lowerLeft(new Vector2d(-2,-1)));
        assertEquals(new Vector2d(2,2), new Vector2d(2,2).lowerLeft(new Vector2d(22,11)));
        assertEquals(new Vector2d(-2,2), new Vector2d(10,10).lowerLeft(new Vector2d(-2,2)));
        assertEquals(new Vector2d(2,-2), new Vector2d(2,-2).lowerLeft(new Vector2d(22,11)));
        assertEquals(new Vector2d(0,0), new Vector2d(0,0).lowerLeft(new Vector2d(0,0)));
    }

    @Test
    public void oppositeTest() {
        assertEquals(new Vector2d(0,0), new Vector2d(0,0).opposite());
        assertEquals(new Vector2d(1,1), new Vector2d(-1,-1).opposite());
        assertEquals(new Vector2d(-1,1), new Vector2d(1,-1).opposite());
        assertEquals(new Vector2d(1,-1), new Vector2d(-1,1).opposite());
        assertEquals(new Vector2d(-1,-1), new Vector2d(1,1).opposite());
        assertEquals(new Vector2d(0,1), new Vector2d(0,-1).opposite());
        assertEquals(new Vector2d(0,-1), new Vector2d(0,1).opposite());
        assertEquals(new Vector2d(-1,0), new Vector2d(1,0).opposite());
        assertEquals(new Vector2d(1,0), new Vector2d(-1,0).opposite());
    }

    @Test
    public void equalsTest() {
        Object object = new Vector2d(1, 2);
        Vector2d myVector = new Vector2d(1,2);
        assertTrue(myVector.equals(object));
        myVector = new Vector2d(1,3);
        assertFalse(myVector.equals(object));

        object = "asfsa";
        assertFalse(myVector.equals(object));
        assertTrue(myVector.equals(myVector));
        assertFalse(myVector.equals(new Vector2d(5,5)));
    }

}
