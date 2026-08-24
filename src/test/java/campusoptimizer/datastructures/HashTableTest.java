package campusoptimizer.datastructures;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    @Test
    void newTableShouldBeEmpty() {
        HashTable<String, Integer> table = new HashTable<>();

        assertTrue(table.isEmpty());
        assertEquals(0, table.size());
    }

    @Test
    void putAndGetShouldWork() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("Alice", 100);
        table.put("Bob", 200);

        assertEquals(100, table.get("Alice"));
        assertEquals(200, table.get("Bob"));
        assertEquals(2, table.size());
    }

    @Test
    void insertingExistingKeyShouldUpdateValue() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("Alice", 100);
        table.put("Alice", 150);

        assertEquals(150, table.get("Alice"));
        assertEquals(1, table.size());
    }

    @Test
    void containsKeyShouldWork() {
        HashTable<String, String> table = new HashTable<>();

        table.put("UG001", "Computer Science");

        assertTrue(table.containsKey("UG001"));
        assertFalse(table.containsKey("UG999"));
    }

    @Test
    void removeShouldDeleteEntry() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("Alice", 100);
        table.put("Bob", 200);

        int removed = table.remove("Alice");

        assertEquals(100, removed);
        assertFalse(table.containsKey("Alice"));
        assertEquals(1, table.size());
        assertEquals(200, table.get("Bob"));
    }

    @Test
    void removeShouldHandleCollisionChain() {
        HashTable<CollisionKey, String> table = new HashTable<>(4);

        CollisionKey first = new CollisionKey("first");
        CollisionKey second = new CollisionKey("second");
        CollisionKey third = new CollisionKey("third");

        table.put(first, "First Value");
        table.put(second, "Second Value");
        table.put(third, "Third Value");

        assertEquals("First Value", table.get(first));
        assertEquals("Second Value", table.get(second));
        assertEquals("Third Value", table.get(third));

        table.remove(second);

        assertFalse(table.containsKey(second));
        assertEquals("First Value", table.get(first));
        assertEquals("Third Value", table.get(third));
        assertEquals(2, table.size());
    }

    @Test
    void collisionHandlingShouldStoreMultipleKeys() {
        HashTable<CollisionKey, String> table = new HashTable<>(4);

        CollisionKey key1 = new CollisionKey("A");
        CollisionKey key2 = new CollisionKey("B");
        CollisionKey key3 = new CollisionKey("C");

        table.put(key1, "Value A");
        table.put(key2, "Value B");
        table.put(key3, "Value C");

        assertEquals("Value A", table.get(key1));
        assertEquals("Value B", table.get(key2));
        assertEquals("Value C", table.get(key3));
        assertEquals(3, table.size());
    }

    @Test
    void missingKeyShouldThrowException() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("Alice", 100);

        assertThrows(
                NoSuchElementException.class,
                () -> table.get("Unknown")
        );
    }

    @Test
    void removingMissingKeyShouldThrowException() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("Alice", 100);

        assertThrows(
                NoSuchElementException.class,
                () -> table.remove("Unknown")
        );
    }

    @Test
    void nullKeyShouldBeRejected() {
        HashTable<String, Integer> table = new HashTable<>();

        assertThrows(
                IllegalArgumentException.class,
                () -> table.put(null, 100)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> table.get(null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> table.containsKey(null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> table.remove(null)
        );
    }

    @Test
    void tableShouldResizeAndPreserveEntries() {
        HashTable<Integer, String> table = new HashTable<>(2);

        for (int i = 0; i < 20; i++) {
            table.put(i, "Value " + i);
        }

        assertEquals(20, table.size());

        for (int i = 0; i < 20; i++) {
            assertEquals("Value " + i, table.get(i));
        }
    }

    @Test
    void emptyTableShouldReportCorrectlyAfterRemovingAllEntries() {
        HashTable<String, Integer> table = new HashTable<>();

        table.put("A", 1);
        table.put("B", 2);
        table.put("C", 3);

        table.remove("A");
        table.remove("B");
        table.remove("C");

        assertTrue(table.isEmpty());
        assertEquals(0, table.size());
    }

    @Test
    void negativeHashCodeShouldBeHandled() {
        HashTable<NegativeHashKey, String> table = new HashTable<>(4);

        NegativeHashKey key = new NegativeHashKey("negative");

        table.put(key, "works");

        assertTrue(table.containsKey(key));
        assertEquals("works", table.get(key));
    }

    /**
     * Custom key whose hashCode is intentionally identical for every key.
     * This forces all entries into the same bucket and verifies
     * separate-chaining collision resolution.
     */
    private static class CollisionKey {

        private final String value;

        private CollisionKey(String value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (!(obj instanceof CollisionKey other)) {
                return false;
            }

            return value.equals(other.value);
        }
    }

    /**
     * Custom key with a negative hash code.
     */
    private static class NegativeHashKey {

        private final String value;

        private NegativeHashKey(String value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            return -123456;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (!(obj instanceof NegativeHashKey other)) {
                return false;
            }

            return value.equals(other.value);
        }
    }
}
