package campusoptimizer.structures;

/**
 * Custom Disjoint Set (Union-Find) data structure supporting
 * Path Compression and Union by Rank for network connectivity tracking.
 */
public class DisjointSet {
    private final int[] parent;
    private final int[] rank;
    private int count;

    /**
     * Initializes a Disjoint Set for elements from 0 to capacity - 1.
     * @param capacity maximum number of elements
     */
    public DisjointSet(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.parent = new int[capacity];
        this.rank = new int[capacity];
        this.count = capacity;
        makeSet();
    }

    /**
     * Sets each element as its own set representative with rank 0.
     */
    public void makeSet() {
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    /**
     * Finds the representative root of element i with path compression.
     * @param i element index
     * @return root representative of element i
     */
    public int find(int i) {
        if (i < 0 || i >= parent.length) {
            throw new IndexOutOfBoundsException("Element index out of bounds: " + i);
        }
        if (parent[i] != i) {
            parent[i] = find(parent[i]);
        }
        return parent[i];
    }

    /**
     * Unites sets containing elements i and j using union by rank.
     * @param i first element
     * @param j second element
     * @return true if merged; false if already in the same set (cycle detected)
     */
    public boolean union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);

        if (rootI == rootJ) {
            return false;
        }

        if (rank[rootI] < rank[rootJ]) {
            parent[rootI] = rootJ;
        } else if (rank[rootI] > rank[rootJ]) {
            parent[rootJ] = rootI;
        } else {
            parent[rootI] = rootJ;
            rank[rootJ]++;
        }
        count--;
        return true;
    }

    /**
     * Checks if elements i and j belong to the same connected component.
     */
    public boolean connected(int i, int j) {
        return find(i) == find(j);
    }

    /**
     * Returns the total count of disjoint sets.
     */
    public int getCount() {
        return count;
    }
}
