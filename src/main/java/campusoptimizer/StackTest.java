package campusoptimizer;

import campusoptimizer.datastructures.Stack;

public class StackTest {

    public static void main(String[] args) {

        Stack<Integer> stack = new Stack<>();

        // ---- Push operations ----
        stack.push(10);
        stack.push(20);
        stack.push(30);

        check(stack.peek() == 30, "Peek after pushes");
        check(stack.size() == 3, "Size after 3 pushes");
        check(!stack.isEmpty(), "Not empty after pushes");

        // ---- Pop operations ----
        check(stack.pop() == 30, "Pop returns top (30)");
        check(stack.pop() == 20, "Pop returns next (20)");
        check(stack.peek() == 10, "Peek after 2 pops");
        check(stack.size() == 1, "Size after 2 pops");

        // ---- Remove last element ----
        check(stack.pop() == 10, "Pop last element");
        check(stack.isEmpty(), "Stack should be empty");
        check(stack.size() == 0, "Empty stack size");

        // ---- LIFO order verification ----
        stack.push(1);
        stack.push(2);
        stack.push(3);
        check(stack.pop() == 3, "LIFO order: 3");
        check(stack.pop() == 2, "LIFO order: 2");
        check(stack.pop() == 1, "LIFO order: 1");

        // ---- Empty stack error handling ----
        try {
            stack.pop();
            System.out.println("FAILED: Empty pop handled");
        } catch (IllegalStateException e) {
            System.out.println("PASSED: Empty pop handled");
        }

        try {
            stack.peek();
            System.out.println("FAILED: Empty peek handled");
        } catch (IllegalStateException e) {
            System.out.println("PASSED: Empty peek handled");
        }

        // ---- toString ----
        stack.push(100);
        stack.push(200);
        check(stack.toString().equals("[200, 100]"), "toString top-to-bottom");

        System.out.println();
        System.out.println("All Stack tests completed successfully.");
    }

    private static void check(boolean condition, String testName) {
        if (condition) {
            System.out.println("PASSED: " + testName);
        } else {
            throw new AssertionError("FAILED: " + testName);
        }
    }
}
