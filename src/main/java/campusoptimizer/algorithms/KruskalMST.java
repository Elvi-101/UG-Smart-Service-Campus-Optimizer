package campusoptimizer.algorithms;

import campusoptimizer.structures.DisjointSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Kruskal's Algorithm implementation for Minimum Spanning Tree (MST)
 * computation using a custom Disjoint Set data structure.
 */
public class KruskalMST {

    /**
     * Represents a weighted road edge in the campus graph.
     */
    public static class Edge implements Comparable<Edge> {
        private final int u;
        private final int v;
        private final double weight;

        public Edge(int u, int v, double weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        public int getU() {
            return u;
        }

        public int getV() {
            return v;
        }

        public double getWeight() {
            return weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Double.compare(this.weight, other.weight);
        }

        @Override
        public String toString() {
            return String.format("%d - %d : %.2f", u, v, weight);
        }
    }

    /**
     * Container for Kruskal's algorithm output.
     */
    public static class Result {
        private final List<Edge> mstEdges;
        private final double totalWeight;

        public Result(List<Edge> mstEdges, double totalWeight) {
            this.mstEdges = mstEdges;
            this.totalWeight = totalWeight;
        }

        public List<Edge> getMstEdges() {
            return mstEdges;
        }

        public double getTotalWeight() {
            return totalWeight;
        }
    }

    /**
     * Finds the Minimum Spanning Tree for a given set of vertices and edges.
     * @param numVertices total number of location nodes
     * @param edges list of all network edges
     * @return Result containing MST edges and total network distance/weight
     */
    public Result findMST(int numVertices, List<Edge> edges) {
        if (numVertices <= 0) {
            throw new IllegalArgumentException("Number of vertices must be positive");
        }
        if (edges == null) {
            throw new IllegalArgumentException("Edges list cannot be null");
        }

        List<Edge> sortedEdges = new ArrayList<>(edges);
        Collections.sort(sortedEdges);

        DisjointSet ds = new DisjointSet(numVertices);
        List<Edge> mstEdges = new ArrayList<>();
        double totalWeight = 0.0;

        for (Edge edge : sortedEdges) {
            if (ds.union(edge.getU(), edge.getV())) {
                mstEdges.add(edge);
                totalWeight += edge.getWeight();
                if (mstEdges.size() == numVertices - 1) {
                    break;
                }
            }
        }

        return new Result(mstEdges, totalWeight);
    }
}
