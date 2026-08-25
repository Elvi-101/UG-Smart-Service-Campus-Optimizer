package campusoptimizer;

import java.util.Random;

/**
 * Runs Dijkstra on randomly generated graphs of increasing size
 * to observe how running time scales, supporting the complexity analysis.
 */
public class ScalingTest {

    public static void main(String[] args) {
        int[] sizes = {50, 200, 1000, 5000, 20000};
        System.out.printf("%-10s %-12s %-15s%n", "Vertices", "Edges(~)", "Time (ms)");

        for (int n : sizes) {
            Graph g = generateRandomGraph(n, /* avgDegree */ 4, /* seed */ 42);

            long start = System.nanoTime();
            Dijkstra.run(g, "V0");
            long end = System.nanoTime();

            long edgeCount = 0;
            for (String v : g.getAllLocations()) edgeCount += g.getNeighbors(v).size();

            System.out.printf("%-10d %-12d %-15.3f%n", n, edgeCount, (end - start) / 1_000_000.0);
        }
    }

    /** Builds a random connected-ish graph with n vertices, each with ~avgDegree random edges. */
    private static Graph generateRandomGraph(int n, int avgDegree, long seed) {
        Graph g = new Graph();
        Random rand = new Random(seed);

        for (int i = 0; i < n; i++) {
            g.addLocation("V" + i);
        }
        // Chain edges first, guaranteeing connectivity: V0-V1-V2-...-V(n-1)
        for (int i = 0; i < n - 1; i++) {
            g.addRoute("V" + i, "V" + (i + 1), 1 + rand.nextInt(20));
        }
        // Extra random edges to raise average degree
        int extraEdges = n * (avgDegree - 2) / 2;
        for (int i = 0; i < extraEdges; i++) {
            int a = rand.nextInt(n);
            int b = rand.nextInt(n);
            if (a != b) {
                g.addRoute("V" + a, "V" + b, 1 + rand.nextInt(20));
            }
        }
        return g;
    }
}
