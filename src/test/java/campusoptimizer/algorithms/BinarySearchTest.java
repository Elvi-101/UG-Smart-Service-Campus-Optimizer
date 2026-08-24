package campusoptimizer.algorithms;

import campusoptimizer.datastructures.DynamicArray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTest {

    @Test
    void findsValueInMiddleOfArray() {
        Integer[] data = {10, 20, 30, 40, 50};

        assertEquals(2, BinarySearch.search(data, 30));
    }

    @Test
    void findsFirstValue() {
        Integer[] data = {10, 20, 30, 40, 50};

        assertEquals(0, BinarySearch.search(data, 10));
    }

    @Test
    void findsLastValue() {
        Integer[] data = {10, 20, 30, 40, 50};

        assertEquals(4, BinarySearch.search(data, 50));
    }

    @Test
    void returnsMinusOneWhenValueDoesNotExist() {
        Integer[] data = {10, 20, 30, 40, 50};

        assertEquals(-1, BinarySearch.search(data, 35));
    }

    @Test
    void handlesEmptyArray() {
        Integer[] data = {};

        assertEquals(-1, BinarySearch.search(data, 10));
    }

    @Test
    void handlesSingleElementArray() {
        Integer[] data = {25};

        assertEquals(0, BinarySearch.search(data, 25));
        assertEquals(-1, BinarySearch.search(data, 10));
    }

    @Test
    void handlesDuplicateValues() {
        Integer[] data = {10, 20, 20, 20, 30};

        int result = BinarySearch.search(data, 20);

        assertTrue(result >= 1 && result <= 3);
        assertEquals(20, data[result]);
    }

    @Test
    void handlesNullArray() {
        assertEquals(-1, BinarySearch.search((Integer[]) null, 10));
    }

    @Test
    void handlesNullTarget() {
        Integer[] data = {10, 20, 30};

        assertEquals(-1, BinarySearch.search(data, null));
    }

    @Test
    void searchesDynamicArray() {
        DynamicArray<Integer> data = new DynamicArray<>();

        data.add(10);
        data.add(20);
        data.add(30);
        data.add(40);
        data.add(50);

        assertEquals(3, BinarySearch.search(data, 40));
    }

    @Test
    void returnsMinusOneWhenDynamicArrayValueDoesNotExist() {
        DynamicArray<Integer> data = new DynamicArray<>();

        data.add(10);
        data.add(20);
        data.add(30);

        assertEquals(-1, BinarySearch.search(data, 25));
    }

    @Test
    void handlesEmptyDynamicArray() {
        DynamicArray<Integer> data = new DynamicArray<>();

        assertEquals(-1, BinarySearch.search(data, 10));
    }

    @Test
    void handlesNullDynamicArray() {
        assertEquals(-1, BinarySearch.search(
                (DynamicArray<Integer>) null, 10));
    }
}
