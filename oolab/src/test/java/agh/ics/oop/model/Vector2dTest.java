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

    @Test
    void equals() {
        // when
        Vector2d v1 = new Vector2d(-1,1);

        // then
        assertFalse(v1.equals(new Vector2d(1,1)));
        assertTrue(v1.equals(new Vector2d(-1,1)));
        assertTrue(v1.equals(v1));
        assertFalse(v1.equals(null));
        assertFalse(v1.equals(new int[]{-1,1}));
    }

    @Test
    void toStringWorks() {
        // when
        Vector2d v1 = new Vector2d(-1,1);

        // then
        assertEquals("(-1,1)", v1.toString());
    }

    @Test
    void precedes() {
        Vector2d v1 = new Vector2d(-1,1);

        assertTrue(v1.precedes(new Vector2d(-1,1)));
        assertTrue(v1.precedes(new Vector2d(0,2)));
        assertFalse(v1.precedes(new Vector2d(-2,0)));
        assertFalse(v1.precedes(new Vector2d(-1,-1)));
    }

    @Test
    void follows() {
        Vector2d v1 = new Vector2d(1,1);

        assertTrue(v1.follows(new Vector2d(1,1)));
        assertTrue(v1.follows(new Vector2d(0,1)));
        assertFalse(v1.follows(new Vector2d(2,3)));
        assertFalse(v1.follows(new Vector2d(1,2)));
    }

    @Test
    void upperRight() {
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(2,1);

        Vector2d v3 = new Vector2d(2,2);

        assertEquals(v3, v1.upperRight(v2));
        assertEquals(v3, v2.upperRight(v1));
        assertEquals(v1, v1.upperRight(v1));
        assertEquals(v3, v3.upperRight(v2));
        assertEquals(v3, v3.upperRight(v1));
    }

    @Test
    void lowerLeft() {
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(2,1);

        Vector2d v3 = new Vector2d(1,1);

        assertEquals(v3, v1.lowerLeft(v2));
        assertEquals(v3, v2.lowerLeft(v1));
        assertEquals(v1, v1.lowerLeft(v1));
        assertEquals(v3, v3.lowerLeft(v2));
        assertEquals(v3, v3.lowerLeft(v1));
    }

    @Test
    void add() {
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(2,1);

        assertEquals((new Vector2d(3,3)), v1.add(v2));
        assertEquals((new Vector2d(3,3)), v2.add(v1));
    }

    @Test
    void subtract() {
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(2,1);

        assertEquals((new Vector2d(-1,1)), v1.subtract(v2));
        assertEquals((new Vector2d(1,-1)), v2.subtract(v1));
        assertEquals((new Vector2d(0,0)), v1.subtract(v1));
    }

    @Test
    void opposite() {
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(0,0);

        assertEquals((new Vector2d(-1,-2)), v1.opposite());
        assertEquals(v2, v2.opposite());
    }
}
