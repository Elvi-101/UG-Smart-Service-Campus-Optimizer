package campusoptimizer;

import campusoptimizer.datastructures.CircularQueue;

/**
 * Standalone test harness for the custom CircularQueue data structure.
 * Run with:  java campusoptimizer.CircularQueueTest
 */
public class CircularQueueTest {

    public static void main(String[] args) {

        // ---- Basic operations with capacity 3 ----
        CircularQueue<Integer> cq = new CircularQueue<>(3);

        check(cq.isEmpty(), "New queue is empty");
        check(!cq.isFull(), "New queue is not full");
        check(cq.size() == 0, "New queue size is 0");
        check(cq.capacity() == 3, "Capacity is 3");

        // Enqueue to full
        cq.enqueue(10);
        cq.enqueue(20);
        cq.enqueue(30);

        check(cq.isFull(), "Queue full after 3 enqueues");
        check(cq.size() == 3, "Size is 3");
        check(cq.peek() == 10, "Peek returns front (10)");

        // Full queue error
        try {
            cq.enqueue(40);
            System.out.println("FAILED: Full enqueue handled");
        } catch (IllegalStateException e) {
            System.out.println("PASSED: Full enqueue handled");
        }

        // Dequeue in FIFO order
        check(cq.dequeue() == 10, "Dequeue returns 10");
        check(cq.dequeue() == 20, "Dequeue returns 20");
        check(cq.size() == 1, "Size after 2 dequeues");

        // ---- Wrap-around behavior ----
        // rear wrapped past the end of the array, front also advanced
        cq.enqueue(40);
        cq.enqueue(50);
        check(cq.isFull(), "Full after wrap-around enqueues");
        check(cq.peek() == 30, "Front is 30 after wrap");

        check(cq.dequeue() == 30, "Wrap dequeue: 30");
        check(cq.dequeue() == 40, "Wrap dequeue: 40");
        check(cq.dequeue() == 50, "Wrap dequeue: 50");
        check(cq.isEmpty(), "Empty after full wrap-around cycle");

        // ---- Multiple wrap-around cycles ----
        for (int cycle = 0; cycle < 5; cycle++) {
            cq.enqueue(cycle * 10);
            cq.enqueue(cycle * 10 + 1);
            cq.enqueue(cycle * 10 + 2);
            check(cq.dequeue() == cycle * 10, "Cycle " + cycle + " first");
            check(cq.dequeue() == cycle * 10 + 1, "Cycle " + cycle + " second");
            check(cq.dequeue() == cycle * 10 + 2, "Cycle " + cycle + " third");
        }
        check(cq.isEmpty(), "Empty after 5 wrap cycles");

        // ---- Empty queue error handling ----
        try {
            cq.dequeue();
            System.out.println("FAILED: Empty dequeue handled");
        } catch (IllegalStateException e) {
            System.out.println("PASSED: Empty dequeue handled");
        }

        try {
            cq.peek();
            System.out.println("FAILED: Empty peek handled");
        } catch (IllegalStateException e) {
            System.out.println("PASSED: Empty peek handled");
        }

        // ---- Invalid capacity ----
        try {
            new CircularQueue<>(0);
            System.out.println("FAILED: Zero capacity handled");
        } catch (IllegalArgumentException e) {
            System.out.println("PASSED: Zero capacity handled");
        }

        // ---- toString ----
        CircularQueue<String> sq = new CircularQueue<>(4);
        sq.enqueue("A");
        sq.enqueue("B");
        sq.enqueue("C");
        check(sq.toString().equals("[A, B, C]"), "toString front-to-rear");

        System.out.println();
        System.out.println("All CircularQueue tests completed successfully.");
    }

    private static void check(boolean condition, String testName) {
        if (condition) {
            System.out.println("PASSED: " + testName);
        } else {
            throw new AssertionError("FAILED: " + testName);
        }
    }
}
