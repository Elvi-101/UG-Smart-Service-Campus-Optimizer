package campusoptimizer;

public class Deque<T> {

    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> previous;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> front;
    private Node<T> rear;
    private int size;

    // Insert an element at the front
    public void addFront(T data) {
        Node<T> newNode = new Node<>(data);

        if (isEmpty()) {
            front = rear = newNode;
        } else {
            newNode.next = front;
            front.previous = newNode;
            front = newNode;
        }

        size++;
    }

    // Insert an element at the rear
    public void addRear(T data) {
        Node<T> newNode = new Node<>(data);

        if (isEmpty()) {
            front = rear = newNode;
        } else {
            newNode.previous = rear;
            rear.next = newNode;
            rear = newNode;
        }

        size++;
    }

    // Remove and return the front element
    public T removeFront() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }

        T data = front.data;
        front = front.next;

        if (front == null) {
            rear = null;
        } else {
            front.previous = null;
        }

        size--;
        return data;
    }

    // Remove and return the rear element
    public T removeRear() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }

        T data = rear.data;
        rear = rear.previous;

        if (rear == null) {
            front = null;
        } else {
            rear.next = null;
        }

        size--;
        return data;
    }

    // Access the front element without removing it
    public T peekFront() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }

        return front.data;
    }

    // Access the rear element without removing it
    public T peekRear() {
        if (isEmpty()) {
            throw new IllegalStateException("Deque is empty");
        }

        return rear.data;
    }

    // Check whether the Deque is empty
    public boolean isEmpty() {  
        return size == 0;
    }

    // Return the number of elements
    public int size() {
        return size;
    }
}