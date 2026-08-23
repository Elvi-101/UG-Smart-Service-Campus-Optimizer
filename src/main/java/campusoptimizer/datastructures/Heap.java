package campusoptimizer.datastructures;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Heap - a generic array-backed heap implementation.
 *
 * Built from scratch for the Smart Campus Service Operations Optimizer.
 * Uses the project's custom DynamicArray rather than Java's built-in
 * priority queue or ArrayList.
 *
 * @param <T> the type of element stored in the heap
 */
public class Heap<T> {

    private final DynamicArray<T> data;
    private final Comparator<T> comparator;

    /**
     * Creates a heap using the provided comparator.
     *
     * The comparator determines which element has higher priority.
     */
    public Heap(Comparator<T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }

        this.data = new DynamicArray<>();
        this.comparator = comparator;
    }

    /**
     * Returns the number of elements in the heap.
     */
    public int size() {
        return data.size();
    }

    /**
     * Returns true if the heap contains no elements.
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Inserts a new value into the heap.
     *
     * Time Complexity: O(log n)
     */
    public void insert(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Heap does not accept null values");
        }

        data.add(value);
        heapifyUp(data.size() - 1);
    }

    /**
     * Returns the highest-priority element without removing it.
     *
     * Time Complexity: O(1)
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }

        return data.get(0);
    }

    /**
     * Removes and returns the highest-priority element.
     *
     * Time Complexity: O(log n)
     */
    public T extract() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }

        T root = data.get(0);

        int lastIndex = data.size() - 1;

        if (lastIndex == 0) {
            data.remove(0);
            return root;
        }

        T lastElement = data.remove(lastIndex);
        data.set(0, lastElement);

        heapifyDown(0);

        return root;
    }

    /**
     * Moves an element upward until the heap property is restored.
     */
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = parent(index);

            if (hasHigherPriority(data.get(index), data.get(parentIndex))) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    /**
     * Moves an element downward until the heap property is restored.
     */
    private void heapifyDown(int index) {
        while (true) {
            int highestPriorityIndex = index;

            int leftChild = leftChild(index);
            int rightChild = rightChild(index);

            if (leftChild < data.size()
                    && hasHigherPriority(
                            data.get(leftChild),
                            data.get(highestPriorityIndex))) {

                highestPriorityIndex = leftChild;
            }

            if (rightChild < data.size()
                    && hasHigherPriority(
                            data.get(rightChild),
                            data.get(highestPriorityIndex))) {

                highestPriorityIndex = rightChild;
            }

            if (highestPriorityIndex == index) {
                break;
            }

            swap(index, highestPriorityIndex);
            index = highestPriorityIndex;
        }
    }

    /**
     * Returns true if first has higher priority than second.
     */
    private boolean hasHigherPriority(T first, T second) {
        return comparator.compare(first, second) > 0;
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    /**
     * Swaps two elements in the heap.
     */
    private void swap(int firstIndex, int secondIndex) {
        T first = data.get(firstIndex);

        data.set(firstIndex, data.get(secondIndex));
        data.set(secondIndex, first);
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
