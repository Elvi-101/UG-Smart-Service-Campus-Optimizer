package campusoptimizer;

public class PriorityQueueTest {

    public static void main(String[] args) {
        testInsertAndOrdering();
        testExtractRemovesInPriorityOrder();
        testEmptyQueueHandling();
        System.out.println("All PriorityQueue tests passed.");
    }

    // Insert several out-of-order priorities, confirm the smallest comes out first
    private static void testInsertAndOrdering() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.insert("D", 9);
        pq.insert("A", 2);
        pq.insert("C", 7);
        pq.insert("B", 5);

        assertEquals("A", pq.peek(), "peek() should return lowest-priority element");
        System.out.println("testInsertAndOrdering passed");
    }

    // Extract everything and confirm it comes out in ascending priority order
    private static void testExtractRemovesInPriorityOrder() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.insert("Library", 12);
        pq.insert("Gym", 4);
        pq.insert("Cafeteria", 8);
        pq.insert("Hostel", 1);
        pq.insert("Bank", 6);

        String[] expectedOrder = { "Hostel", "Gym", "Bank", "Cafeteria", "Library" };
        for (String expected : expectedOrder) {
            String actual = pq.extractMin();
            assertEquals(expected, actual, "extractMin() out of order");
        }
        assertTrue(pq.isEmpty(), "queue should be empty after extracting everything");
        System.out.println("testExtractRemovesInPriorityOrder passed");
    }

    // Confirm empty queue is handled safely (isEmpty flag + exception on illegal
    // extract)
    private static void testEmptyQueueHandling() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        assertTrue(pq.isEmpty(), "new queue should start empty");

        boolean threw = false;
        try {
            pq.extractMin();
        } catch (IllegalStateException e) {
            threw = true;
        }
        assertTrue(threw, "extractMin() on empty queue should throw IllegalStateException");
        System.out.println("testEmptyQueueHandling passed");
    }

    // --- tiny assertion helpers (no JUnit dependency needed) ---

    private static void assertEquals(Object expected, Object actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message + " | expected=" + expected + " actual=" + actual);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
