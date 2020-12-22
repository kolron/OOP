package tests;

import gameClient.util.Point3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {

    @Test
    void testToString() {
        Point3D p1 = new Point3D(3,3,3);
        assertEquals("3.0,3.0,3.0", p1.toString());
        p1 = new Point3D(3.2,3.9,3.0);
        assertEquals("3.2,3.9,3.0", p1.toString());
    }

    @Test
    void distance() {
        Point3D p1 = null;
        Point3D p2 = new Point3D(2,2,2);
        assertEquals(-1, p2.distance(p1));
        p1 = new Point3D(2,2,2);
        assertEquals(0, p2.distance(p1));
        p2 = new Point3D(3,4,4);
        assertEquals(3, p1.distance(p2));
        assertEquals(3, p2.distance(p1));
    }

    @Test
    void testEquals() {
        Point3D p1 = null;
        Point3D p2 = new Point3D(2,3,4);
        assertFalse(p2.equals(p1));
        p1 = new Point3D(2,3,4);
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));
        p2 = new Point3D(2,3,0);
        assertFalse(p2.equals(p1));

    }

    @Test
    void close2equals() {
        Point3D p1 = null;
        Point3D p2 = new Point3D(2,3,4);
        assertFalse(p2.close2equals(p1));
        p1 = new Point3D(2+0.00000001, 3, 4);
        assertTrue(p1.close2equals(p2));
    }
}