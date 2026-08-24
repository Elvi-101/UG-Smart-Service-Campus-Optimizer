package campusoptimizer.datastructures;

/**
 * HashTable - a custom hash table implementation using separate chaining
 * for collision resolution.
 *
 * Built from scratch for the Smart Campus Service Operations Optimizer.
 * Does not use Java's built-in HashMap or other map implementations.
 *
 * Average time complexity:
 * - put: O(1)
 * - get: O(1)
 * - remove: O(1)
 *
 * Worst-case complexity for all three operations is O(n) when many keys
 * collide into the same bucket.
 *
 * @param <K> the type of key
 * @param <V> the type of value
 */
public class HashTable<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    /**
     * A single key-value entry stored inside a bucket.
     */
    private static class Entry<K, V> {
        private final K key;
        private V value;
        private Entry<K, V> next;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Entry<K, V>[] buckets;
    private int size;

    /**
     * Creates a hash table with the default capacity.
     */
    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates a hash table with the specified initial capacity.
     */
    @SuppressWarnings("unchecked")
    public HashTable(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Initial capacity must be greater than zero");
        }

        buckets = (Entry<K, V>[]) new Entry[initialCapacity];
        size = 0;
    }

    /**
     * Returns the number of key-value pairs stored.
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if the hash table contains no entries.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Inserts a key-value pair.
     *
     * If the key already exists, its value is replaced.
     */
    public void put(K key, V value) {
        validateKey(key);

        int index = indexFor(key);

        Entry<K, V> current = buckets[index];

        while (current != null) {
            if (keysEqual(current.key, key)) {
                current.value = value;
                return;
            }

            current = current.next;
        }

        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;

        size++;

        if ((double) size / buckets.length > LOAD_FACTOR) {
            resize();
        }
    }

    /**
     * Searches for a value using its key.
     *
     * @throws java.util.NoSuchElementException if the key does not exist
     */
    public V get(K key) {
        validateKey(key);

        Entry<K, V> entry = findEntry(key);

        if (entry == null) {
            throw new java.util.NoSuchElementException(
                    "Key not found: " + key);
        }

        return entry.value;
    }

    /**
     * Returns true if the specified key exists in the table.
     */
    public boolean containsKey(K key) {
        validateKey(key);
        return findEntry(key) != null;
    }

    /**
     * Removes the entry associated with the specified key.
     *
     * @throws java.util.NoSuchElementException if the key does not exist
     */
    public V remove(K key) {
        validateKey(key);

        int index = indexFor(key);

        Entry<K, V> current = buckets[index];
        Entry<K, V> previous = null;

        while (current != null) {
            if (keysEqual(current.key, key)) {

                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }

                size--;
                return current.value;
            }

            previous = current;
            current = current.next;
        }

        throw new java.util.NoSuchElementException(
                "Key not found: " + key);
    }

    /**
     * Computes the bucket index for a key.
     *
     * Math.floorMod ensures the index is never negative even when
     * key.hashCode() returns a negative value.
     */
    private int indexFor(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    /**
     * Finds an entry associated with a key.
     */
    private Entry<K, V> findEntry(K key) {
        int index = indexFor(key);

        Entry<K, V> current = buckets[index];

        while (current != null) {
            if (keysEqual(current.key, key)) {
                return current;
            }

            current = current.next;
        }

        return null;
    }

    /**
     * Handles collisions by storing multiple entries in the same bucket.
     */
    private boolean keysEqual(K first, K second) {
        return first.equals(second);
    }

    /**
     * Resizes the bucket array when the load factor becomes too high.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] oldBuckets = buckets;

        buckets = (Entry<K, V>[]) new Entry[oldBuckets.length * 2];

        for (Entry<K, V> bucket : oldBuckets) {
            Entry<K, V> current = bucket;

            while (current != null) {
                Entry<K, V> next = current.next;

                int newIndex = indexFor(current.key);

                current.next = buckets[newIndex];
                buckets[newIndex] = current;

                current = next;
            }
        }
    }

    /**
     * Null keys are not permitted.
     */
    private void validateKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException(
                    "HashTable does not accept null keys");
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");

        boolean first = true;

        for (Entry<K, V> bucket : buckets) {
            Entry<K, V> current = bucket;

            while (current != null) {
                if (!first) {
                    result.append(", ");
                }

                result.append(current.key)
                      .append("=")
                      .append(current.value);

                first = false;
                current = current.next;
            }
        }

        return result.append("}").toString();
    }
}
