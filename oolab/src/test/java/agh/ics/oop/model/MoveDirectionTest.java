package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveDirectionTest {

    @Test
    void testToString() {
        assertEquals("north", MoveDirection.NORTH.toString());
        assertEquals("south", MoveDirection.SOUTH.toString());
        assertEquals("east", MoveDirection.EAST.toString());
        assertEquals("west", MoveDirection.WEST.toString());
        assertEquals("north-east", MoveDirection.NORTH_EAST.toString());
        assertEquals("south-east", MoveDirection.SOUTH_EAST.toString());
        assertEquals("north-west", MoveDirection.NORTH_WEST.toString());
        assertEquals("south-west", MoveDirection.SOUTH_WEST.toString());
    }

    @Test
    void moveDirectionToValue() {
        assertEquals(0, MoveDirection.NORTH.moveDirectionToValue());
        assertEquals(1, MoveDirection.NORTH_EAST.moveDirectionToValue());
        assertEquals(2, MoveDirection.EAST.moveDirectionToValue());
        assertEquals(3, MoveDirection.SOUTH_EAST.moveDirectionToValue());
        assertEquals(4, MoveDirection.SOUTH.moveDirectionToValue());
        assertEquals(5, MoveDirection.SOUTH_WEST.moveDirectionToValue());
        assertEquals(6, MoveDirection.WEST.moveDirectionToValue());
        assertEquals(7, MoveDirection.NORTH_WEST.moveDirectionToValue());
    }

    @Test
    void valueToMoveDirection() {
        assertEquals(MoveDirection.NORTH, MoveDirection.valueToMoveDirection(0));
        assertEquals(MoveDirection.NORTH_EAST, MoveDirection.valueToMoveDirection(1));
        assertEquals(MoveDirection.EAST, MoveDirection.valueToMoveDirection(2));
        assertEquals(MoveDirection.SOUTH_EAST, MoveDirection.valueToMoveDirection(3));
        assertEquals(MoveDirection.SOUTH, MoveDirection.valueToMoveDirection(4));
        assertEquals(MoveDirection.SOUTH_WEST, MoveDirection.valueToMoveDirection(5));
        assertEquals(MoveDirection.WEST, MoveDirection.valueToMoveDirection(6));
        assertEquals(MoveDirection.NORTH_WEST, MoveDirection.valueToMoveDirection(7));

        assertThrows(IllegalArgumentException.class, () -> MoveDirection.valueToMoveDirection(-1));
        assertThrows(IllegalArgumentException.class, () -> MoveDirection.valueToMoveDirection(8));
    }

    @Test
    void getOppositeDirection() {
        assertEquals(MoveDirection.SOUTH, MoveDirection.NORTH.getOppositeDirection());
        assertEquals(MoveDirection.SOUTH_WEST, MoveDirection.NORTH_EAST.getOppositeDirection());
        assertEquals(MoveDirection.WEST, MoveDirection.EAST.getOppositeDirection());
        assertEquals(MoveDirection.NORTH_WEST, MoveDirection.SOUTH_EAST.getOppositeDirection());
        assertEquals(MoveDirection.NORTH, MoveDirection.SOUTH.getOppositeDirection());
        assertEquals(MoveDirection.NORTH_EAST, MoveDirection.SOUTH_WEST.getOppositeDirection());
        assertEquals(MoveDirection.EAST, MoveDirection.WEST.getOppositeDirection());
        assertEquals(MoveDirection.SOUTH_EAST, MoveDirection.NORTH_WEST.getOppositeDirection());
    }

    @Test
    void toUnitVector() {
        assertEquals(new Vector2d(0,1), MoveDirection.NORTH.toUnitVector());
        assertEquals(new Vector2d(1,1), MoveDirection.NORTH_EAST.toUnitVector());
        assertEquals(new Vector2d(1,0), MoveDirection.EAST.toUnitVector());
        assertEquals(new Vector2d(1,-1), MoveDirection.SOUTH_EAST.toUnitVector());
        assertEquals(new Vector2d(0,-1), MoveDirection.SOUTH.toUnitVector());
        assertEquals(new Vector2d(-1,-1), MoveDirection.SOUTH_WEST.toUnitVector());
        assertEquals(new Vector2d(-1,0), MoveDirection.WEST.toUnitVector());
        assertEquals(new Vector2d(-1,1), MoveDirection.NORTH_WEST.toUnitVector());
    }
}