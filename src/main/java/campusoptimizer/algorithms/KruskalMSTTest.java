package campusoptimizer.algorithms;

import campusoptimizer.algorithms.KruskalMST.Edge;
import campusoptimizer.algorithms.KruskalMST.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KruskalMSTTest {

    private KruskalMST kruskal;

    @BeforeEach
    public void setUp() {
        kruskal = new KruskalMST();
    }

    @Test
    public void testSimpleTriangleGraph() {
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 10.0));
        edges.add(new Edge(1, 2, 15.0));
        edges.add(new Edge(0, 2, 5.0));

        Result result = kruskal.findMST(3, edges);

        assertEquals(2, result.getMstEdges().size());
        assertEquals(15.0, result.getTotalWeight(), 0.001);
    }

    @Test
    public void testFourNodeGraph() {
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 1.0));
        edges.add(new Edge(1, 2, 4.0));
        edges.add(new Edge(2, 3, 2.0));
        edges.add(new Edge(0, 3, 3.0));
        edges.add(new Edge(0, 2, 5.0));

        Result result = kruskal.findMST(4, edges);

        assertEquals(3, result.getMstEdges().size());
        assertEquals(6.0, result.getTotalWeight(), 0.001);
    }

    @Test
    public void testEmptyEdgesList() {
        List<Edge> edges = new ArrayList<>();
        Result result = kruskal.findMST(3, edges);

        assertTrue(result.getMstEdges().isEmpty());
        assertEquals(0.0, result.getTotalWeight(), 0.001);
    }

    @Test
    public void testInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> kruskal.findMST(0, new ArrayList<>()));
        assertThrows(IllegalArgumentException.class, () -> kruskal.findMST(5, null));
    }
}
