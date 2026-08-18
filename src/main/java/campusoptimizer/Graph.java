import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Weighted graph representing campus locations (vertices) and the
 * routes between them (edges), e.g. distance in meters or time in minutes.
 *
 * Stored as an adjacency list: each location maps to a list of
 * its direct neighbors and the weight (cost) of the route to each.
 */
public class Graph {

    public static class Edge {
        public final String destination;
        public final int weight;

        public Edge(String destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    private final Map<String, List<Edge>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    /** Register a location even if it has no routes yet (keeps it in the graph). */
    public void addLocation(String location) {
        adjacencyList.putIfAbsent(location, new ArrayList<>());
    }

    /**
     * Add a route between two locations. Campus paths are usually walkable
     * in both directions, so this adds the edge both ways (undirected).
     * Use addDirectedRoute() instead for one-way routes (e.g. one-way roads).
     */
    public void addRoute(String from, String to, int weight) {
        addDirectedRoute(from, to, weight);
        addDirectedRoute(to, from, weight);
    }

    public void addDirectedRoute(String from, String to, int weight) {
        addLocation(from);
        addLocation(to);
        adjacencyList.get(from).add(new Edge(to, weight));
    }

    public List<Edge> getNeighbors(String location) {
        return adjacencyList.getOrDefault(location, new ArrayList<>());
    }

    public boolean hasLocation(String location) {
        return adjacencyList.containsKey(location);
    }

    public List<String> getAllLocations() {
        return new ArrayList<>(adjacencyList.keySet());
    }
}
