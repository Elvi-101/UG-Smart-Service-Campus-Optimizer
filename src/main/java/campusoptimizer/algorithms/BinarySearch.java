package campusoptimizer.algorithms;

import campusoptimizer.datastructures.DynamicArray;

/**
 * BinarySearch - a custom binary search implementation.
 *
 * Searches a sorted collection by repeatedly comparing the target
 * with the middle element and eliminating half of the remaining
 * search space.
 *
 * Time Complexity:
 * - Best case: O(1)
 * - Average case: O(log n)
 * - Worst case: O(log n)
 *
 * Space Complexity:
 * - O(1) auxiliary space because the implementation is iterative.
 */
public final class BinarySearch {

    private BinarySearch() {
        // Static utility class - no instances.
    }

    /**
     * Searches a sorted array for the target value.
     *
     * @param arr sorted array in ascending order
     * @param target value to search for
     * @return index of the target if found, otherwise -1
     */
    public static <T extends Comparable<T>> int search(T[] arr, T target) {
        if (arr == null || arr.length == 0 || target == null) {
            return -1;
        }

        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            int comparison = arr[mid].compareTo(target);

            if (comparison == 0) {
                return mid;
            }

            if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

    /**
     * Searches a sorted DynamicArray for the target value.
     *
     * @param list sorted DynamicArray in ascending order
     * @param target value to search for
     * @return index of the target if found, otherwise -1
     */
    public static <T extends Comparable<T>> int search(
            DynamicArray<T> list, T target) {

        if (list == null || list.isEmpty() || target == null) {
            return -1;
        }

        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            int comparison = list.get(mid).compareTo(target);

            if (comparison == 0) {
                return mid;
            }

            if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }
}
