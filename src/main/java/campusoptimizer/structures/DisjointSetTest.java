package campusoptimizer.structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DisjointSetTest {

    private DisjointSet ds;

    @BeforeEach
    public void setUp() {
        ds = new DisjointSet(5);
    }

    @Test
    public void testInitialState() {
        assertEquals(5, ds.getCount());
        for (int i = 0; i < 5; i++) {
            assertEquals(i, ds.find(i));
        }
    }

    @Test
    public void testUnionAndConnected() {
        assertTrue(ds.union(0, 1));
        assertTrue(ds.connected(0, 1));
        assertEquals(4, ds.getCount());
    }

    @Test
    public void testCycleDetection() {
        ds.union(0, 1);
        ds.union(1, 2);
        assertFalse(ds.union(0, 2));
    }

    @Test
    public void testTransitiveConnection() {
        ds.union(0, 1);
        ds.union(1, 2);
        assertTrue(ds.connected(0, 2));
    }

    @Test
    public void testInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> ds.find(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> ds.find(10));
    }
}
