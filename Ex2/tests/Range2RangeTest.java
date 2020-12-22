package tests;

import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Range2RangeTest {

    @Test
    void world2frame() {
        Range xRange = new Range(0,4);
        Range yRange = new Range(0,5);
        Range2D range2D = new Range2D(xRange,yRange);
        xRange = new Range(2,2);
        yRange = new Range(4,4);
        Range2Range r2r = new Range2Range(range2D,new Range2D(xRange,yRange));
        Point3D p = new Point3D(1,2,0);
        assertEquals(new Point3D(2,4,0),r2r.world2frame(p));
    }

    @Test
    void frame2world() {
        Range xRange = new Range(0,2);
        Range yRange = new Range(0,2);
        Range2D range2D = new Range2D(xRange,yRange);
        xRange = new Range(0,4);
        yRange = new Range(0,4);
        Range2Range r2r = new Range2Range(range2D,new Range2D(xRange,yRange));
        Point3D p = new Point3D(2,4,0);
        assertEquals(new Point3D(1,2,0),r2r.frame2world(p));
    }
}