import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

public class BFS {

    /**
     * Performs a breadth-first search traversal of the graph starting from the specified location.
     *
     * @param graph the campus graph
     * @param start the location to begin traversal from
     * @return list of locations in the order they were visited
     * @throws NoSuchElementException if start is not in the graph
     */
    public static List<String> traverse(Graph graph, String start) {
        if (!graph.hasLocation(start)) {
            throw new NoSuchElementException("Start location not found: " + start);
        }

        List<String> visitOrder = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        visited.add(start);
        queue.offer(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            visitOrder.add(current);

            for (Graph.Edge edge : graph.getNeighbors(current)) {
                String neighbor = edge.destination; // weight is ignored by BFS
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);   // mark visited at ENQUEUE time,
                    queue.offer(neighbor);   // not at dequeue — prevents duplicate enqueues
                }
            }
        }

        return visitOrder;
    }

    /**
     * Traverses all connected components of the graph, returning a list of lists.
     * Each inner list contains the locations in one connected component, in the order they were visited.
     */
    public static List<List<String>> traverseAll(Graph graph) {
        List<List<String>> components = new ArrayList<>();
        Set<String> globallyVisited = new HashSet<>();

        for (String location : graph.getAllLocations()) {
            if (!globallyVisited.contains(location)) {
                List<String> component = traverse(graph, location);
                components.add(component);
                globallyVisited.addAll(component);
            }
        }

        return components;
    }


    public static List<String> shortestPathByHops(Graph graph, String start, String target) {
        if (!graph.hasLocation(start) || !graph.hasLocation(target)) {
            return new ArrayList<>();
        }
        if (start.equals(target)) {
            List<String> single = new ArrayList<>();
            single.add(start);
            return single;
        }

        java.util.Map<String, String> parent = new java.util.HashMap<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        visited.add(start);
        queue.offer(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(target)) {
                break;
            }
            for (Graph.Edge edge : graph.getNeighbors(current)) {
                String neighbor = edge.destination;
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }

        if (!visited.contains(target)) {
            return new ArrayList<>(); // unreachable
        }

        LinkedList<String> path = new LinkedList<>();
        String step = target;
        while (step != null) {
            path.addFirst(step);
            step = parent.get(step);
        }
        return path;
    }
}