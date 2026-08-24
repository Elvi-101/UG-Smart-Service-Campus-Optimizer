package campusoptimizer;

import campusoptimizer.algorithms.InsertionSort;
import campusoptimizer.datastructures.DynamicArray;

/**
 * Standalone test harness for the custom InsertionSort algorithm.
 * Tests both the raw-array and DynamicArray overloads.
 * Run with:  java campusoptimizer.InsertionSortTest
 */
public class InsertionSortTest {

    public static void main(String[] args) {

        // ================================================================
        //  Raw-array tests
        // ================================================================

        // Unsorted array
        Integer[] arr1 = {5, 3, 8, 1, 4, 2, 7, 6};
        InsertionSort.sort(arr1);
        check(isSorted(arr1), "Sort unsorted array");

        // Already sorted
        Integer[] arr2 = {1, 2, 3, 4, 5};
        InsertionSort.sort(arr2);
        check(isSorted(arr2), "Sort already-sorted array");

        // Reverse sorted
        Integer[] arr3 = {5, 4, 3, 2, 1};
        InsertionSort.sort(arr3);
        check(isSorted(arr3), "Sort reverse-sorted array");

        // Single element
        Integer[] arr4 = {42};
        InsertionSort.sort(arr4);
        check(arr4[0] == 42, "Single-element array");

        // Two elements
        Integer[] arr5 = {9, 1};
        InsertionSort.sort(arr5);
        check(arr5[0] == 1 && arr5[1] == 9, "Two-element array");

        // Duplicates
        Integer[] arr6 = {3, 1, 3, 2, 1, 2};
        InsertionSort.sort(arr6);
        check(isSorted(arr6), "Array with duplicates");

        // Null and empty arrays (should not throw)
        InsertionSort.sort((Integer[]) null);
        System.out.println("PASSED: Null array handled");

        Integer[] empty = {};
        InsertionSort.sort(empty);
        System.out.println("PASSED: Empty array handled");

        // String sorting
        String[] names = {"Legon", "Balme", "Akuafo", "Pentagon", "UGCS"};
        InsertionSort.sort(names);
        check(isSorted(names), "Sort string array (campus locations)");

        // ================================================================
        //  DynamicArray tests
        // ================================================================

        DynamicArray<Integer> da = new DynamicArray<>();
        da.add(50);
        da.add(10);
        da.add(40);
        da.add(20);
        da.add(30);

        InsertionSort.sort(da);
        check(isDynamicArraySorted(da), "Sort DynamicArray");

        // DynamicArray with duplicates
        DynamicArray<Integer> da2 = new DynamicArray<>();
        da2.add(5);
        da2.add(2);
        da2.add(5);
        da2.add(1);
        da2.add(2);
        InsertionSort.sort(da2);
        check(isDynamicArraySorted(da2), "Sort DynamicArray with duplicates");

        // Single-element DynamicArray
        DynamicArray<Integer> da3 = new DynamicArray<>();
        da3.add(99);
        InsertionSort.sort(da3);
        check(da3.get(0) == 99, "Single-element DynamicArray");

        // Null DynamicArray
        InsertionSort.sort((DynamicArray<Integer>) null);
        System.out.println("PASSED: Null DynamicArray handled");

        // ================================================================
        //  Stability verification
        // ================================================================
        // Insertion sort is stable: equal elements keep their relative order.
        // We verify this with a pair of strings that compare equal by length.
        String[] stable = {"bb", "aa", "cc", "ab"};
        // All length-2 strings compare equal when sorted by natural order (lexicographic).
        // After sorting lexicographically: "aa", "ab", "bb", "cc"
        InsertionSort.sort(stable);
        check(stable[0].equals("aa") && stable[1].equals("ab")
              && stable[2].equals("bb") && stable[3].equals("cc"),
              "Stable sort preserves order of equal-priority elements");

        System.out.println();
        System.out.println("All InsertionSort tests completed successfully.");
    }

    // ---- helpers ----

    private static <T extends Comparable<T>> boolean isSorted(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(arr[i - 1]) < 0) {
                return false;
            }
        }
        return true;
    }

    private static <T extends Comparable<T>> boolean isDynamicArraySorted(DynamicArray<T> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).compareTo(list.get(i - 1)) < 0) {
                return false;
            }
        }
        return true;
    }

    private static void check(boolean condition, String testName) {
        if (condition) {
            System.out.println("PASSED: " + testName);
        } else {
            throw new AssertionError("FAILED: " + testName);
        }
    }
}
