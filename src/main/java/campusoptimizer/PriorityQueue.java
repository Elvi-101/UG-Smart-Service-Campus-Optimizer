import java.util.ArrayList;
import java.util.List;

/**
 * A generic Priority Queue implemented as a binary MIN-heap.
 * The element with the SMALLEST priority value is always served first.
 * (For Dijkstra: priority = distance from source, so the closest
 * unvisited location always comes out first.)
 *
 * Backed by an ArrayList used as a complete binary tree:
 *   - parent of index i        -> (i - 1) / 2
 *   - left child of index i    -> 2*i + 1
 *   - right child of index i   -> 2*i + 2
 */
public class PriorityQueue<T> {

    // Wraps an element together with its priority so we can compare them.
    private class Entry {
        T element;
        int priority;

        Entry(T element, int priority) {
            this.element = element;
            this.priority = priority;
        }
    }

    private final List<Entry> heap;

    public PriorityQueue() {
        heap = new ArrayList<>();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    /** Insert / enqueue a new element with a given priority. O(log n) */
    public void insert(T element, int priority) {
        Entry entry = new Entry(element, priority);
        heap.add(entry);              // add at the end (bottom-right of tree)
        siftUp(heap.size() - 1);      // restore heap order by bubbling up
    }

    /** Look at (without removing) the highest-priority element. O(1) */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }
        return heap.get(0).element;
    }

    /**
     * Remove and return the element with the SMALLEST priority.
     * This is the operation Dijkstra calls repeatedly to pick the
     * next closest, unvisited location. O(log n)
     */
    public T extractMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot extract from an empty priority queue");
        }

        Entry min = heap.get(0);
        Entry last = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, last);   // move last element to the root
            siftDown(0);         // bubble it down to restore heap order
        }

        return min.element;
    }

    // ---- internal heap-maintenance helpers ----

    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (heap.get(i).priority < heap.get(parent).priority) {
                swap(i, parent);
                i = parent;
            } else {
                break; // correct position found
            }
        }
    }

    private void siftDown(int i) {
        int size = heap.size();
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;

            if (left < size && heap.get(left).priority < heap.get(smallest).priority) {
                smallest = left;
            }
            if (right < size && heap.get(right).priority < heap.get(smallest).priority) {
                smallest = right;
            }
            if (smallest == i) {
                break; // correct position found
            }
            swap(i, smallest);
            i = smallest;
        }
    }

    private void swap(int i, int j) {
        Entry temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
