package campusoptimizer.datastructures;

public class Stack<T> {

    // ---- internal node ----

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    // ---- fields ----

    private Node<T> top;
    private int size;

    // ---- public API ----

    /**
     * Pushes an element onto the top of the stack. O(1).
     */
    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = top;
        top = newNode;
        size++;
    }

    /**
     * Removes and returns the element at the top of the stack. O(1).
     *
     * @throws IllegalStateException if the stack is empty
     */
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    /**
     * Returns the element at the top without removing it. O(1).
     *
     * @throws IllegalStateException if the stack is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return top.data;
    }

    /** Returns true if the stack contains no elements. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of elements currently in the stack. */
    public int size() {
        return size;
    }

    /**
     * Returns a string representation of the stack from top to bottom.
     * Example: "[C, B, A]" where C is the top.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = top;
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
