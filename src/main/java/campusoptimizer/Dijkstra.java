package campusoptimizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Dijkstra's shortest-path algorithm for the campus route graph.
 * Uses the custom PriorityQueue (min-heap) to always expand the
 * closest not-yet-finalized location next.
 */
public class Dijkstra {

    public static final int UNREACHABLE = Integer.MAX_VALUE;

    /** Result bundle: shortest distance to every location, plus how to rebuild each path. */
    public static class Result {
        public final Map<String, Integer> distances;
        public final Map<String, String> predecessors;

        public Result(Map<String, Integer> distances, Map<String, String> predecessors) {
            this.distances = distances;
            this.predecessors = predecessors;
        }

        /** Rebuilds the path from source to target by walking predecessors backwards. */
        public List<String> reconstructPath(String target) {
            if (!distances.containsKey(target) || distances.get(target) == UNREACHABLE) {
                return new ArrayList<>(); // no path exists
            }
            List<String> path = new ArrayList<>();
            String current = target;
            while (current != null) {
                path.add(current);
                current = predecessors.get(current);
            }
            Collections.reverse(path);
            return path;
        }
    }

    public static Result run(Graph graph, String source) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> predecessors = new HashMap<>();
        Set<String> finalized = new HashSet<>(); // locations whose shortest distance is confirmed

        // Step 1: initialize every location to "infinity" except the source (0)
        for (String location : graph.getAllLocations()) {
            distances.put(location, UNREACHABLE);
            predecessors.put(location, null);
        }
        distances.put(source, 0);

        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.insert(source, 0);

        while (!pq.isEmpty()) {
            String current = pq.extractMin();

            // Lazy deletion: skip stale entries for locations already finalized
            if (finalized.contains(current)) {
                continue;
            }
            finalized.add(current);

            // Relax every edge out of the current location
            for (Graph.Edge edge : graph.getNeighbors(current)) {
                if (finalized.contains(edge.destination)) {
                    continue;
                }
                int newDist = distances.get(current) + edge.weight;
                if (newDist < distances.get(edge.destination)) {
                    distances.put(edge.destination, newDist);
                    predecessors.put(edge.destination, current);
                    pq.insert(edge.destination, newDist); // re-insert with better priority
                }
            }
        }

        return new Result(distances, predecessors);
    }
}

