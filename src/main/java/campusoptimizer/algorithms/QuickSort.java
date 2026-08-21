package campusoptimizer.algorithms;

import campusoptimizer.datastructures.DynamicArray;


public final class QuickSort {

    private QuickSort() {
        // static utility class - no instances
    }

    /** Sorts a plain array in place, ascending order. */
    public static <T extends Comparable<T>> void sort(T[] arr) {
        if (arr == null || arr.length < 2) {
            return; // already "sorted" - 0 or 1 elements
        }
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * Convenience overload: sorts the contents of a DynamicArray in place.
     *
     * Deliberately does NOT call list.toArray() here. toArray() hands back
     * an array that is really an Object[] under the hood (Java can't create
     * a true T[] due to generic type erasure). Assigning that into a
     * variable of type T[] where T is bounded (T extends Comparable<T>)
     * makes the compiler insert a runtime cast to Comparable[], which then
     * throws ClassCastException because the actual object is Object[], not
     * Comparable[]. Sorting via get/set on the DynamicArray directly avoids
     * creating any such array and sidesteps the problem entirely.
     */
    public static <T extends Comparable<T>> void sort(DynamicArray<T> list) {
        if (list == null || list.size() < 2) {
            return;
        }
        quickSortList(list, 0, list.size() - 1);
    }

    private static <T extends Comparable<T>> void quickSortList(DynamicArray<T> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionList(list, low, high);
            quickSortList(list, low, pivotIndex - 1);
            quickSortList(list, pivotIndex + 1, high);
        }
    }

    private static <T extends Comparable<T>> int partitionList(DynamicArray<T> list, int low, int high) {
        T pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (list.get(j).compareTo(pivot) <= 0) {
                i++;
                swapList(list, i, j);
            }
        }
        swapList(list, i + 1, high);
        return i + 1;
    }

    private static <T> void swapList(DynamicArray<T> list, int a, int b) {
        T temp = list.get(a);
        list.set(a, list.get(b));
        list.set(b, temp);
    }

    private static <T extends Comparable<T>> void quickSort(T[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    /**
     * Lomuto partition: picks arr[high] as pivot, places every element
     * <= pivot before it, then puts the pivot in its final sorted
     * position and returns that index.
     */
    private static <T extends Comparable<T>> int partition(T[] arr, int low, int high) {
        T pivot = arr[high];
        int i = low - 1; // boundary of the "<= pivot" region

        for (int j = low; j < high; j++) {
            if (arr[j].compareTo(pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static <T> void swap(T[] arr, int a, int b) {
        T temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}