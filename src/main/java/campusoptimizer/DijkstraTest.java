package campusoptimizer;

import java.util.List;
import java.util.Map;

public class DijkstraTest {

    public static void main(String[] args) {
        testShortestDistances();
        testPathReconstruction();
        testUnreachableLocation();
        System.out.println("All Dijkstra tests passed.");
    }

    /** Builds a small campus graph used across the tests. */
    private static Graph buildCampusGraph() {
        Graph g = new Graph();
        // Weights = walking time in minutes
        g.addRoute("MainGate", "Library", 5);
        g.addRoute("MainGate", "Hostel", 10);
        g.addRoute("Library", "Cafeteria", 3);
        g.addRoute("Hostel", "Cafeteria", 4);
        g.addRoute("Cafeteria", "SportsComplex", 6);
        g.addRoute("Hostel", "SportsComplex", 15);
        return g;
    }

    private static void testShortestDistances() {
        Graph g = buildCampusGraph();
        Dijkstra.Result result = Dijkstra.run(g, "MainGate");

        // MainGate -> Library (5) -> Cafeteria (3) = 8, cheaper than MainGate -> Hostel
        // -> Cafeteria (14)
        assertEquals(0, result.distances.get("MainGate"), "distance to source should be 0");
        assertEquals(5, result.distances.get("Library"), "MainGate->Library");
        assertEquals(10, result.distances.get("Hostel"), "MainGate->Hostel");
        assertEquals(8, result.distances.get("Cafeteria"), "MainGate->Library->Cafeteria should win");
        assertEquals(14, result.distances.get("SportsComplex"), "MainGate->Library->Cafeteria->SportsComplex");

        System.out.println("testShortestDistances passed");
    }

    private static void testPathReconstruction() {
        Graph g = buildCampusGraph();
        Dijkstra.Result result = Dijkstra.run(g, "MainGate");

        List<String> path = result.reconstructPath("SportsComplex");
        List<String> expected = List.of("MainGate", "Library", "Cafeteria", "SportsComplex");

        if (!path.equals(expected)) {
            throw new AssertionError("Path mismatch. expected=" + expected + " actual=" + path);
        }
        System.out.println("testPathReconstruction passed: " + String.join(" -> ", path));
    }

    private static void testUnreachableLocation() {
        Graph g = buildCampusGraph();
        g.addLocation("IsolatedAnnex"); // registered, but no routes connect it to anything

        Dijkstra.Result result = Dijkstra.run(g, "MainGate");

        assertEquals(Dijkstra.UNREACHABLE, result.distances.get("IsolatedAnnex"),
                "isolated location should be unreachable");
        List<String> path = result.reconstructPath("IsolatedAnnex");
        assertTrue(path.isEmpty(), "path to unreachable location should be empty");

        System.out.println("testUnreachableLocation passed");
    }

    // --- tiny assertion helpers ---
    private static void assertEquals(Object expected, Object actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message + " | expected=" + expected + " actual=" + actual);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
