package campusoptimizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Same algorithm as Dijkstra.run(), but records a step-by-step trace
 * (which vertex was extracted, and the distance table snapshot after
 * processing it) so we can print a trace table for the analysis writeup.
 */
public class DijkstraTraced {

    public static class Step {
        public final String extractedVertex;
        public final Map<String, Integer> distanceSnapshot;

        public Step(String extractedVertex, Map<String, Integer> distanceSnapshot) {
            this.extractedVertex = extractedVertex;
            this.distanceSnapshot = distanceSnapshot;
        }
    }

    public static List<Step> runWithTrace(Graph graph, String source) {
        List<Step> trace = new ArrayList<>();
        Map<String, Integer> distances = new TreeMap<>(); // TreeMap => alphabetical, stable printing
        Map<String, String> predecessors = new HashMap<>();
        Set<String> finalized = new HashSet<>();

        for (String location : graph.getAllLocations()) {
            distances.put(location, Dijkstra.UNREACHABLE);
        }
        distances.put(source, 0);

        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.insert(source, 0);

        while (!pq.isEmpty()) {
            String current = pq.extractMin();
            if (finalized.contains(current)) {
                continue;
            }
            finalized.add(current);

            for (Graph.Edge edge : graph.getNeighbors(current)) {
                if (finalized.contains(edge.destination))
                    continue;
                int newDist = distances.get(current) + edge.weight;
                if (newDist < distances.get(edge.destination)) {
                    distances.put(edge.destination, newDist);
                    predecessors.put(edge.destination, current);
                    pq.insert(edge.destination, newDist);
                }
            }

            trace.add(new Step(current, new TreeMap<>(distances)));
        }
        return trace;
    }

    public static void printTraceTable(List<Step> trace) {
        if (trace.isEmpty())
            return;
        List<String> vertices = new ArrayList<>(trace.get(trace.size() - 1).distanceSnapshot.keySet());

        StringBuilder header = new StringBuilder(String.format("%-5s %-16s", "Step", "Extracted"));
        for (String v : vertices)
            header.append(String.format("%-16s", v));
        System.out.println(header);

        int step = 1;
        for (Step s : trace) {
            StringBuilder row = new StringBuilder(String.format("%-5d %-16s", step++, s.extractedVertex));
            for (String v : vertices) {
                int d = s.distanceSnapshot.get(v);
                row.append(String.format("%-16s", d == Dijkstra.UNREACHABLE ? "INF" : String.valueOf(d)));
            }
            System.out.println(row);
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph();
        g.addRoute("MainGate", "Library", 5);
        g.addRoute("MainGate", "Hostel", 10);
        g.addRoute("Library", "Cafeteria", 3);
        g.addRoute("Hostel", "Cafeteria", 4);
        g.addRoute("Cafeteria", "SportsComplex", 6);
        g.addRoute("Hostel", "SportsComplex", 15);

        List<Step> trace = runWithTrace(g, "MainGate");
        printTraceTable(trace);
    }
}
