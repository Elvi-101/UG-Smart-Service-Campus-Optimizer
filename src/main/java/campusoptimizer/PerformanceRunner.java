
package campusoptimizer;

import campusoptimizer.algorithms.BinarySearch;
import campusoptimizer.algorithms.QuickSort;

import java.util.Arrays;
import java.util.Random;

public class PerformanceRunner {

    private static final int[] SIZES = {100, 1000, 5000, 10000};

    public static void main(String[] args) {
        Random random = new Random(42);

        System.out.println("Algorithm,Input Size,Execution Time (ns)");

        for (int size : SIZES) {
            Integer[] data = new Integer[size];

            for (int i = 0; i < size; i++) {
                data[i] = random.nextInt(size * 10);
            }

            // Measure Quick Sort
            Integer[] sortData = Arrays.copyOf(data, data.length);

            long sortStart = System.nanoTime();
            QuickSort.sort(sortData);
            long sortTime = System.nanoTime() - sortStart;

            // Measure Binary Search on sorted data
            Integer[] searchData = Arrays.copyOf(sortData, sortData.length);
            int target = searchData[size / 2];

            long searchStart = System.nanoTime();
            BinarySearch.search(searchData, target);
            long searchTime = System.nanoTime() - searchStart;

            System.out.println("QuickSort," + size + "," + sortTime);
            System.out.println("BinarySearch," + size + "," + searchTime);
        }
    }
}
