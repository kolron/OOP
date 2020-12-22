package tests;

import gameClient.util.Range;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangeTest {

    @Test
    void isIn() {
        Range range = new Range(-1, 20);
        assertTrue(range.isIn(0));
        assertTrue(range.isIn(-1));
        assertFalse(range.isIn(21));
    }

    @Test
    void isEmpty() {
        Range range = new Range(-1, 20);
        assertFalse(range.isEmpty());
        range = new Range(20,20);
        assertFalse(range.isEmpty());
        range = new Range(21, 20);
        assertTrue(range.isEmpty());
    }

    @Test
    void get_length() {
        Range range = new Range(-1, 20);
        assertEquals(21, range.get_length());
        range = new Range(20, 10);
        assertEquals(-10, range.get_length());
    }

    @Test
    void getPortion() {
        Range range = new Range(10, 20);
        double d = 5;
        assertEquals(-0.5, range.getPortion(d));
        d = 15;
        assertEquals(0.5, range.getPortion(d));
    }

    @Test
    void fromPortion() {
        Range range = new Range(10, 20);
        assertEquals(15, range.fromPortion(0.5));
    }
}