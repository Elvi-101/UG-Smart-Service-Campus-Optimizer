package optimizer;

public class Deque {

    private int[] data;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    public Deque(int capacity) {
        this.capacity = capacity;
        data = new int[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    // Insert at rear
    public void insertRear(int value) {
        if (isFull()) {
            System.out.println("Deque is full");
            return;
        }

        rear = (rear + 1) % capacity;
        data[rear] = value;
        size++;
    }

    // Insert at front
    public void insertFront(int value) {
        if (isFull()) {
            System.out.println("Deque is full");
            return;
        }

        front = (front - 1 + capacity) % capacity;
        data[front] = value;
        size++;

        if (size == 1) {
            rear = front;
        }
    }

    // Remove from front
    public int deleteFront() {

        if (isEmpty()) {
            throw new RuntimeException("Deque is empty");
        }

        int value = data[front];
        front = (front + 1) % capacity;
        size--;

        return value;
    }

    // Remove from rear
    public int deleteRear() {

        if (isEmpty()) {
            throw new RuntimeException("Deque is empty");
        }

        int value = data[rear];
        rear = (rear - 1 + capacity) % capacity;
        size--;

        return value;
    }

    // Access front element
    public int getFront() {

        if (isEmpty()) {
            throw new RuntimeException("Deque is empty");
        }

        return data[front];
    }

    // Access rear element
    public int getRear() {

        if (isEmpty()) {
            throw new RuntimeException("Deque is empty");
        }

        return data[rear];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public int size() {
        return size;
    }
}