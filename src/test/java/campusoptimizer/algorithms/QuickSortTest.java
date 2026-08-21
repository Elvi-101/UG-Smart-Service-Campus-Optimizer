package campusoptimizer.algorithms;

import campusoptimizer.datastructures.DynamicArray;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    // ---------- Normal cases ----------

    @Test
    void sortsRandomOrderIntegersAscending() {
        Integer[] arr = {8, 3, 9, 1, 6, 2, 7, 4, 5};
        QuickSort.sort(arr);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, arr);
    }

    @Test
    void sortsStringsAscending() {
        String[] arr = {"Legon", "Achimota", "Madina", "Circle"};
        QuickSort.sort(arr);
        assertArrayEquals(new String[]{"Achimota", "Circle", "Legon", "Madina"}, arr);
    }

    @Test
    void sortsDynamicArrayInPlace() {
        DynamicArray<Integer> list = new DynamicArray<>();
        list.add(5);
        list.add(1);
        list.add(4);
        list.add(2);

        QuickSort.sort(list);

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(4, list.get(2));
        assertEquals(5, list.get(3));
    }

    // ---------- Boundary cases ----------

    @Test
    void alreadySortedArrayStaysSorted_worstCaseShape() {
        // This shape triggers QuickSort's O(n^2) worst case with a
        // last-element pivot -- useful evidence for the complexity
        // trace/discussion, not just correctness.
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7};
        QuickSort.sort(arr);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6, 7}, arr);
    }

    @Test
    void reverseSortedArraySortsCorrectly() {
        Integer[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        QuickSort.sort(arr);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, arr);
    }

    @Test
    void arrayWithAllDuplicateValuesSortsCorrectly() {
        Integer[] arr = {4, 4, 4, 4, 4};
        QuickSort.sort(arr);
        assertArrayEquals(new Integer[]{4, 4, 4, 4, 4}, arr);
    }

    @Test
    void arrayWithSomeDuplicateValuesSortsCorrectly() {
        Integer[] arr = {3, 1, 3, 2, 1};
        QuickSort.sort(arr);
        assertArrayEquals(new Integer[]{1, 1, 2, 3, 3}, arr);
    }

    @Test
    void singleElementArrayIsUnchanged() {
        Integer[] arr = {42};
        QuickSort.sort(arr);
        assertArrayEquals(new Integer[]{42}, arr);
    }

    @Test
    void emptyArrayDoesNotThrow() {
        Integer[] arr = {};
        assertDoesNotThrow(() -> QuickSort.sort(arr));
        assertEquals(0, arr.length);
    }

    // ---------- Invalid input cases ----------

    @Test
    void nullArrayDoesNotThrow() {
        Integer[] arr = null;
        assertDoesNotThrow(() -> QuickSort.sort(arr));
    }

    @Test
    void twoElementArraySortsCorrectlyBothOrders() {
        Integer[] descending = {2, 1};
        QuickSort.sort(descending);
        assertArrayEquals(new Integer[]{1, 2}, descending);

        Integer[] ascending = {1, 2};
        QuickSort.sort(ascending);
        assertArrayEquals(new Integer[]{1, 2}, ascending);
    }
}