package campusoptimizer.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * DynamicArray - a custom array-backed list implementation.
 *
 * Built from scratch for the Group 31 Smart Campus Service Operations
 * Optimizer project (DCIT 308). Backs the service-request collection that
 * later gets sorted by Quick Sort (see algorithms.QuickSort).
 *
 * Does NOT use java.util.ArrayList / Vector internally — only a raw
 * Object[] — per the project's "no built-in core structures" rule.
 *
 * @param <T> the type of element stored
 */
public class DynamicArray<T> implements Iterable<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int GROWTH_FACTOR = 2;

    private Object[] data;
    private int size;

    public DynamicArray() {
        this(DEFAULT_CAPACITY);
    }

    public DynamicArray(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Initial capacity cannot be negative: " + initialCapacity);
        }
        // Guard against capacity 0 so the first insert has something to double from.
        this.data = new Object[Math.max(initialCapacity, 1)];
        this.size = 0;
    }

    /** Number of elements currently stored (not the same as capacity). */
    public int size() {
        return size;
    }

    /** Current length of the backing array. Exposed mainly so tests can verify resize behaviour. */
    public int capacity() {
        return data.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Appends a value to the end of the array. O(1) amortised.
     */
    public void add(T value) {
        insert(size, value);
    }

    /**
     * Inserts a value at the given index, shifting subsequent elements right.
     * O(n) worst case because of the shift; O(1) when index == size (append).
     */
    public void insert(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Insert index " + index + " out of bounds for size " + size);
        }
        ensureCapacity(size + 1);

        // Shift everything from index.size-1 one slot to the right.
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = value;
        size++;
    }

    /**
     * Returns the value at the given index. O(1).
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    /**
     * Replaces the value at the given index and returns the old value. O(1).
     */
    @SuppressWarnings("unchecked")
    public T set(int index, T value) {
        checkIndex(index);
        T old = (T) data[index];
        data[index] = value;
        return old;
    }

    /**
     * Removes and returns the value at the given index, shifting subsequent
     * elements left. O(n) worst case.
     */
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkIndex(index);
        T removed = (T) data[index];

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[size - 1] = null; // avoid holding a stale reference (memory leak)
        size--;

        // Shrink when usage drops to a quarter of capacity, but never below
        // DEFAULT_CAPACITY. This is optional evidence for the "resize" trace
        // (shows both growth and shrink behaviour).
        if (size > 0 && size == data.length / 4 && data.length / 2 >= DEFAULT_CAPACITY) {
            resize(data.length / 2);
        }
        return removed;
    }

    /** Converts the current contents to a plain Java array — used by QuickSort. */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(data, 0, result, 0, size);
        return (T[]) result;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            int newCapacity = Math.max(data.length * GROWTH_FACTOR, minCapacity);
            resize(newCapacity);
        }
    }

    private void resize(int newCapacity) {
        Object[] newData = new Object[newCapacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " out of bounds for size " + size);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) data[cursor++];
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) sb.append(", ");
        }
        return sb.append("]").toString();
    }
}