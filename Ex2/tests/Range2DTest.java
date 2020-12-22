package tests;

import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Range2DTest {

    @Test
    void getPortion() {
        Range xRange = new Range(12, 20);
        Range yRange = new Range(-2,13);
        Range2D range2D = new Range2D(xRange, yRange);
        Point3D p = new Point3D(28, 43, 0);
        assertEquals(new Point3D(2,3,0), range2D.getPortion(p));
    }

    @Test
    void fromPortion() {
        Range xRange = new Range(12, 20);
        Range yRange = new Range(-2,13);
        Range2D range2D = new Range2D(xRange, yRange);
        Point3D p = new Point3D(16, 3, 0);
        assertEquals(new Point3D(0.5,1,0), range2D.getPortion(p));

    }
}