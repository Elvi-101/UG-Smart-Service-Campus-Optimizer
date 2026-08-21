package campusoptimizer.algorithms;

import campusoptimizer.datastructures.DynamicArray;

public final class InsertionSort {

    private InsertionSort() {
        // static utility class — no instances
    }

    // ================================================================
    // Raw-array overloads
    // ================================================================

    /**
     * Sorts a plain array in place, ascending order. O(n²) worst/average,
     * O(n) best case (already sorted).
     */
    public static <T extends Comparable<T>> void sort(T[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            T key = arr[i];
            int j = i - 1;

            // Shift elements that are greater than key one position right
            while (j >= 0 && arr[j].compareTo(key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // ================================================================
    // DynamicArray overloads
    // ================================================================

    /**
     * Sorts a DynamicArray in place, ascending order.
     *
     * Uses get/set directly on the DynamicArray to avoid the
     * ClassCastException that can arise from generic type erasure
     * when converting to a raw array (same rationale as QuickSort).
     */
    public static <T extends Comparable<T>> void sort(DynamicArray<T> list) {
        if (list == null || list.size() < 2) {
            return;
        }

        for (int i = 1; i < list.size(); i++) {
            T key = list.get(i);
            int j = i - 1;

            while (j >= 0 && list.get(j).compareTo(key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }
}
