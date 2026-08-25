package campusoptimizer;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class BFSTest {

    private Graph buildConnectedCampusGraph() {
        Graph g = new Graph();
        g.addRoute("Library", "Cafeteria", 3);
        g.addRoute("Cafeteria", "Gym", 6);
        g.addRoute("Library", "Hostel", 5);
        g.addRoute("Cafeteria", "Clinic", 2);
        return g;
    }

    @Test
    void traverse_singleLocationNoRoutes() {
        Graph g = new Graph();
        g.addLocation("Library");

        List<String> result = BFS.traverse(g, "Library");
        assertEquals(List.of("Library"), result);
    }

    @Test
    void traverse_visitsAllReachableLocations() {
        Graph g = buildConnectedCampusGraph();

        List<String> result = BFS.traverse(g, "Library");

        assertEquals(5, result.size());
        assertEquals("Library", result.get(0));
        assertTrue(result.indexOf("Cafeteria") < result.indexOf("Gym"));
        assertTrue(result.indexOf("Cafeteria") < result.indexOf("Clinic"));
    }

    @Test
    void traverse_levelOrderOnLinearChain() {
        Graph g = new Graph();
        g.addRoute("A", "B", 1);
        g.addRoute("B", "C", 1);
        g.addRoute("C", "D", 1);

        List<String> result = BFS.traverse(g, "A");
        assertEquals(List.of("A", "B", "C", "D"), result);
    }

    @Test
    void traverse_handlesCycleWithoutInfiniteLoop() {
        Graph g = new Graph();
        g.addRoute("A", "B", 1);
        g.addRoute("B", "C", 1);
        g.addRoute("C", "A", 1); // cycle back to A

        List<String> result = BFS.traverse(g, "A");
        assertEquals(3, result.size());
    }

    @Test
    void traverse_ignoresEdgeWeights() {
        Graph g = new Graph();
        g.addRoute("A", "B", 999);

        List<String> result = BFS.traverse(g, "A");
        assertEquals(List.of("A", "B"), result);
    }

    @Test
    void traverse_startLocationNotInGraphThrows() {
        Graph g = new Graph();
        g.addLocation("Library");

        assertThrows(NoSuchElementException.class, () -> BFS.traverse(g, "Nonexistent"));
    }

    @Test
    void traverse_emptyGraphThrows() {
        Graph g = new Graph();
        assertThrows(NoSuchElementException.class, () -> BFS.traverse(g, "Anything"));
    }

    @Test
    void traverse_onDisconnectedGraphOnlyReachesOwnComponent() {
        Graph g = new Graph();
        g.addRoute("Library", "Cafeteria", 3); // component 1
        g.addLocation("IsolatedShed"); // component 2, no routes

        List<String> result = BFS.traverse(g, "Library");
        assertEquals(2, result.size());
        assertFalse(result.contains("IsolatedShed"));
    }

    @Test
    void traverseAll_coversEveryComponent() {
        Graph g = new Graph();
        g.addRoute("Library", "Cafeteria", 3); // component 1
        g.addRoute("Hostel", "Gym", 4); // component 2
        g.addLocation("IsolatedShed"); // component 3

        List<List<String>> components = BFS.traverseAll(g);

        assertEquals(3, components.size());
        int totalLocations = components.stream().mapToInt(List::size).sum();
        assertEquals(5, totalLocations);
    }

    @Test
    void shortestPathByHops_findsFewestHopsNotLowestWeight() {
        Graph g = new Graph();
        g.addRoute("A", "C", 100);
        g.addRoute("A", "B", 1);
        g.addRoute("B", "C", 1);

        List<String> path = BFS.shortestPathByHops(g, "A", "C");
        assertEquals(List.of("A", "C"), path); // 1 hop, even though heavier by weight
    }

    @Test
    void shortestPathByHops_sameStartAndTarget() {
        Graph g = new Graph();
        g.addLocation("A");
        assertEquals(List.of("A"), BFS.shortestPathByHops(g, "A", "A"));
    }

    @Test
    void shortestPathByHops_unreachableTargetReturnsEmpty() {
        Graph g = new Graph();
        g.addRoute("A", "B", 1);
        g.addLocation("Z"); // disconnected

        assertTrue(BFS.shortestPathByHops(g, "A", "Z").isEmpty());
    }

    @Test
    void shortestPathByHops_missingLocationReturnsEmpty() {
        Graph g = new Graph();
        g.addLocation("A");
        assertTrue(BFS.shortestPathByHops(g, "A", "Ghost").isEmpty());
    }
}