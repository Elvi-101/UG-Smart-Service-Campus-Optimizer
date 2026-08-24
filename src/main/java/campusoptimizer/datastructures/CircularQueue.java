package campusoptimizer.datastructures;

/**
 * CircularQueue - a custom array-based, fixed-capacity FIFO queue
 * that wraps around when it reaches the end of the backing array.
 *
 * Built from scratch for the Group 31 Smart Campus Service Operations
 * Optimizer project (DCIT 308). Models bounded resource pools such as
 * maintenance crew dispatch slots, where the number of concurrent
 * requests that can be buffered is fixed.
 *
 * Does NOT use any java.util collections internally — only a raw
 * Object[] — per the project's "no built-in core structures" rule.
 *
 * @param <T> the type of element stored
 */
public class CircularQueue<T> {

    private final Object[] data;
    private final int capacity;
    private int front;
    private int rear;
    private int size;

    /**
     * Creates a circular queue with the given maximum capacity.
     *
     * @param capacity the fixed maximum number of elements
     * @throws IllegalArgumentException if capacity is less than 1
     */
    public CircularQueue(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException(
                    "Capacity must be at least 1, got: " + capacity);
        }
        this.capacity = capacity;
        this.data = new Object[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    /**
     * Adds an element to the rear of the circular queue. O(1).
     *
     * @throws IllegalStateException if the queue is full
     */
    public void enqueue(T element) {
        if (isFull()) {
            throw new IllegalStateException("Circular queue is full");
        }
        rear = (rear + 1) % capacity;
        data[rear] = element;
        size++;
    }

    /**
     * Removes and returns the element at the front. O(1).
     *
     * @throws IllegalStateException if the queue is empty
     */
    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Circular queue is empty");
        }
        T element = (T) data[front];
        data[front] = null; // help GC
        front = (front + 1) % capacity;
        size--;
        return element;
    }

    /**
     * Returns the element at the front without removing it. O(1).
     *
     * @throws IllegalStateException if the queue is empty
     */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Circular queue is empty");
        }
        return (T) data[front];
    }

    /** Returns true if the queue contains no elements. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns true if the queue has reached its maximum capacity. */
    public boolean isFull() {
        return size == capacity;
    }

    /** Returns the number of elements currently stored. */
    public int size() {
        return size;
    }

    /** Returns the maximum number of elements the queue can hold. */
    public int capacity() {
        return capacity;
    }

    /**
     * Returns a string representation showing elements from front to rear.
     * Example: "[A, B, C]" where A is the front.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            sb.append(data[index]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        return sb.append("]").toString();
    }
}
