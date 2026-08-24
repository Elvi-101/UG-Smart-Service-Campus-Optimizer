package campusoptimizer.datastructures;

/**
 * Queue - a custom linked-list-based FIFO data structure.
 *
 * Built from scratch for the Group 31 Smart Campus Service Operations
 * Optimizer project (DCIT 308). Used to queue incoming campus service
 * requests in arrival order before they are priority-sorted, and to
 * support BFS-based campus graph traversal.
 *
 * Does NOT use java.util.Queue or java.util.LinkedList internally —
 * only a raw singly-linked list — per the project's "no built-in core
 * structures" rule.
 *
 * @param <T> the type of element stored
 */
public class Queue<T> {

    // ---- internal node ----

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    // ---- fields ----

    private Node<T> front;
    private Node<T> rear;
    private int size;

    // ---- public API ----

    /**
     * Adds an element to the rear of the queue. O(1).
     */
    public void enqueue(T data) {
        Node<T> newNode = new Node<>(data);

        if (isEmpty()) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }

        size++;
    }

    /**
     * Removes and returns the element at the front of the queue. O(1).
     *
     * @throws IllegalStateException if the queue is empty
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }

        T data = front.data;
        front = front.next;

        if (front == null) {
            rear = null;
        }

        size--;
        return data;
    }

    /**
     * Returns the element at the front without removing it. O(1).
     *
     * @throws IllegalStateException if the queue is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return front.data;
    }

    /** Returns true if the queue contains no elements. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of elements currently in the queue. */
    public int size() {
        return size;
    }

    /**
     * Returns a string representation of the queue from front to rear.
     * Example: "[A, B, C]" where A is the front.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = front;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        return sb.append("]").toString();
    }
}
