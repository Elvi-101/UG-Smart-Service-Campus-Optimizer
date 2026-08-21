package campusoptimizer.datastructures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {

    // ---------- Normal cases ----------

    @Test
    void addAppendsElementsInOrder() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("Legon");
        arr.add("Commonwealth Hall");
        arr.add("Balme Library");

        assertEquals(3, arr.size());
        assertEquals("Legon", arr.get(0));
        assertEquals("Commonwealth Hall", arr.get(1));
        assertEquals("Balme Library", arr.get(2));
    }

    @Test
    void insertAtMiddleShiftsRemainingElementsRight() {
        DynamicArray<Integer> arr = new DynamicArray<>();
        arr.add(1);
        arr.add(2);
        arr.add(4);
        arr.insert(2, 3); // [1, 2, 3, 4]

        assertEquals(4, arr.size());
        assertEquals(1, arr.get(0));
        assertEquals(2, arr.get(1));
        assertEquals(3, arr.get(2));
        assertEquals(4, arr.get(3));
    }

    @Test
    void setReplacesValueAndReturnsOldValue() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("Old Value");

        String old = arr.set(0, "New Value");

        assertEquals("Old Value", old);
        assertEquals("New Value", arr.get(0));
    }

    @Test
    void removeShiftsRemainingElementsLeftAndReturnsRemovedValue() {
        DynamicArray<Integer> arr = new DynamicArray<>();
        arr.add(10);
        arr.add(20);
        arr.add(30);

        int removed = arr.remove(1); // remove "20"

        assertEquals(20, removed);
        assertEquals(2, arr.size());
        assertEquals(10, arr.get(0));
        assertEquals(30, arr.get(1));
    }

    // ---------- Resize / boundary cases ----------

    @Test
    void resizeTraceGrowsCapacityWhenFullDefaultCapacityIsTen() {
        DynamicArray<Integer> arr = new DynamicArray<>(); // default capacity 10
        assertEquals(10, arr.capacity());

        for (int i = 0; i < 10; i++) {
            arr.add(i);
        }
        assertEquals(10, arr.capacity()); // still exactly full, no resize yet

        arr.add(10); // 11th element forces a resize
        assertEquals(20, arr.capacity()); // doubled: 10 -> 20
        assertEquals(11, arr.size());
    }

    @Test
    void resizeGrowsFromExplicitSmallCapacity() {
        DynamicArray<Integer> arr = new DynamicArray<>(2);
        assertEquals(2, arr.capacity());

        arr.add(1);
        arr.add(2);
        assertEquals(2, arr.capacity());

        arr.add(3); // forces resize: 2 -> 4
        assertEquals(4, arr.capacity());
    }

    @Test
    void addingToZeroCapacityArrayStillWorks() {
        DynamicArray<Integer> arr = new DynamicArray<>(0);
        assertEquals(1, arr.capacity()); // constructor guards capacity 0 up to 1

        arr.add(5);
        arr.add(6);

        assertEquals(2, arr.size());
        assertEquals(5, arr.get(0));
        assertEquals(6, arr.get(1));
    }

    @Test
    void emptyArrayHasSizeZeroAndIsEmptyTrue() {
        DynamicArray<Integer> arr = new DynamicArray<>();
        assertEquals(0, arr.size());
        assertTrue(arr.isEmpty());
    }

    @Test
    void singleElementArrayBehavesCorrectly() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("only");

        assertEquals(1, arr.size());
        assertEquals("only", arr.get(0));
        assertEquals("only", arr.remove(0));
        assertTrue(arr.isEmpty());
    }

    @Test
    void duplicateValuesAreAllStoredIndependently() {
        DynamicArray<String> arr = new DynamicArray<>();
        arr.add("URGENT");
        arr.add("URGENT");
        arr.add("URGENT");

        assertEquals(3, arr.size());
        arr.remove(1);
        assertEquals(2, arr.size());
        assertEquals("URGENT", arr.get(0));
        assertEquals("URGENT", arr.get(1));
    }

    // ---------- Invalid input cases ----------

    @Test
    void getWithNegativeIndexThrows() {
        DynamicArray<Integer> arr = new DynamicArray<>();
        arr.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> arr.get(-1));
    }

    @Test
    void getWithIndexEqualToSizeThrows() {
        DynamicArray<Integer> arr = new DynamicArray<>();
        arr.add(1);
        arr.add(2);
        assertThrows(IndexOutOfBoundsException.class, () -> arr.get(2));
    }

    @Test
    void getOnEmptyArrayThrows() {
        DynamicArray<Integer> arr = new DynamicArray<>();
        assertThrows(IndexOutOfBoundsException.class, () -> arr.get(0));
    }

    @Test
    void insertWithIndexBeyondSizePlusOneThrows() {
        DynamicArray<Integer> arr = new DynamicArray<>();
        arr.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> arr.insert(5, 99));
    }

    @Test
    void removeOnEmptyArrayThrows() {
        DynamicArray<Integer> arr = new DynamicArray<>();
        assertThrows(IndexOutOfBoundsException.class, () -> arr.remove(0));
    }

    @Test
    void negativeInitialCapacityThrows() {
        assertThrows(IllegalArgumentException.class, () -> new DynamicArray<Integer>(-5));
    }

    // ---------- Iterator ----------

    @Test
    void iteratorVisitsAllElementsInOrder() {
        DynamicArray<Integer> arr = new DynamicArray<>();
        arr.add(1);
        arr.add(2);
        arr.add(3);

        int expected = 1;
        for (int value : arr) {
            assertEquals(expected, value);
            expected++;
        }
    }
}