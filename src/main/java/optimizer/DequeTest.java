package optimizer;

public class DequeTest {

    public static void main(String[] args) {

        Deque deque = new Deque(5);

        System.out.println("Adding elements...");

        deque.insertRear(10);
        deque.insertRear(20);
        deque.insertFront(5);

        System.out.println("Front element: " + deque.getFront());
        System.out.println("Rear element: " + deque.getRear());
        System.out.println("Size: " + deque.size());

        System.out.println("\nRemoving elements...");

        System.out.println("Deleted front: " + deque.deleteFront());
        System.out.println("Deleted rear: " + deque.deleteRear());

        System.out.println("\nAfter deletion:");
        System.out.println("Front element: " + deque.getFront());
        System.out.println("Rear element: " + deque.getRear());
        System.out.println("Size: " + deque.size());

        System.out.println("\nDeque empty? " + deque.isEmpty());
    }
}