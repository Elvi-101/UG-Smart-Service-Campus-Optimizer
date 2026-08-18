package campusoptimizer;

public class DequeTest {

    public static void main(String[] args) {

        Deque<Integer> deque = new Deque<>();

        // Front insertion
        deque.addFront(20);
        deque.addFront(10);

        check(deque.peekFront() == 10, "Front insertion");
        check(deque.peekRear() == 20, "Rear access");

        // Rear insertion
        deque.addRear(30);

        check(deque.peekRear() == 30, "Rear insertion");
        check(deque.size() == 3, "Size after insertion");

        // Front deletion
        check(deque.removeFront() == 10, "Front deletion");

        // Rear deletion
        check(deque.removeRear() == 30, "Rear deletion");

        check(deque.peekFront() == 20, "Front access");
        check(deque.peekRear() == 20, "Rear access");

        // Remove last element
        check(deque.removeFront() == 20, "Remove last element");
        check(deque.isEmpty(), "Deque should be empty");
        check(deque.size() == 0, "Empty Deque size");

        // Empty Deque handling
        try {
            deque.removeFront();
            System.out.println("FAILED: Empty removeFront");
        } catch (IllegalStateException e) {
            System.out.println("PASSED: Empty removeFront handled");
        }

        try {
            deque.removeRear();
            System.out.println("FAILED: Empty removeRear");
        } catch (IllegalStateException e) {
            System.out.println("PASSED: Empty removeRear handled");
        }

        try {
            deque.peekFront();
            System.out.println("FAILED: Empty peekFront");
        } catch (IllegalStateException e) {
            System.out.println("PASSED: Empty peekFront handled");
        }

        try {
            deque.peekRear();
            System.out.println("FAILED: Empty peekRear");
        } catch (IllegalStateException e) {
            System.out.println("PASSED: Empty peekRear handled");
        }

        System.out.println();
        System.out.println("All Deque tests completed successfully.");
    }

    private static void check(boolean condition, String testName) {
        if (condition) {
            System.out.println("PASSED: " + testName);
        } else {
            throw new AssertionError("FAILED: " + testName);
        }
    }
}