package campusoptimizer;

import campusoptimizer.datastructures.Queue;

/**
 * Standalone test harness for the custom Queue data structure.
 * Run with:  java campusoptimizer.QueueTest
 */
public class QueueTest {

    public static void main(String[] args) {

        Queue<Integer> queue = new Queue<>();

        // ---- Enqueue operations ----
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        check(queue.peek() == 10, "Peek returns front (10)");
        check(queue.size() == 3, "Size after 3 enqueues");
        check(!queue.isEmpty(), "Not empty after enqueues");

        // ---- Dequeue operations (FIFO order) ----
        check(queue.dequeue() == 10, "Dequeue returns 10 (FIFO)");
        check(queue.dequeue() == 20, "Dequeue returns 20 (FIFO)");
        check(queue.peek() == 30, "Peek after 2 dequeues");
        check(queue.size() == 1, "Size after 2 dequeues");

        // ---- Remove last element ----
        check(queue.dequeue() == 30, "Dequeue last element");
        check(queue.isEmpty(), "Queue should be empty");
        check(queue.size() == 0, "Empty queue size");

        // ---- FIFO order with more elements ----
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        check(queue.dequeue() == 1, "FIFO order: 1");
        check(queue.dequeue() == 2, "FIFO order: 2");
        check(queue.dequeue() == 3, "FIFO order: 3");
        check(queue.dequeue() == 4, "FIFO order: 4");

        // ---- Empty queue error handling ----
        try {
            queue.dequeue();
            System.out.println("FAILED: Empty dequeue handled");
        } catch (IllegalStateException e) {
            System.out.println("PASSED: Empty dequeue handled");
        }

        try {
            queue.peek();
            System.out.println("FAILED: Empty peek handled");
        } catch (IllegalStateException e) {
            System.out.println("PASSED: Empty peek handled");
        }

        // ---- toString ----
        queue.enqueue(100);
        queue.enqueue(200);
        queue.enqueue(300);
        check(queue.toString().equals("[100, 200, 300]"), "toString front-to-rear");

        System.out.println();
        System.out.println("All Queue tests completed successfully.");
    }

    private static void check(boolean condition, String testName) {
        if (condition) {
            System.out.println("PASSED: " + testName);
        } else {
            throw new AssertionError("FAILED: " + testName);
        }
    }
}
