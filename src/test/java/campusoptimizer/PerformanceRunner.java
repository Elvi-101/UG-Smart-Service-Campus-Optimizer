
package campusoptimizer;

import campusoptimizer.algorithms.BinarySearch;
import campusoptimizer.algorithms.QuickSort;

import java.util.Arrays;
import java.util.Random;

public class PerformanceRunner {

    private static final int[] SIZES = { 100, 1000, 5000, 10000 };

    public static void main(String[] args) {
        Random random = new Random(42);

        System.out.println("Algorithm,Input Size,Execution Time (ns)");

        for (int size : SIZES) {
            Integer[] data = new Integer[size];

            for (int i = 0; i < size; i++) {
                data[i] = random.nextInt(size * 10);
            }

            Integer[] sortData = Arrays.copyOf(data, data.length);

            long start = System.nanoTime();
            QuickSort.sort(sortData);
            long sortTime = System.nanoTime() - start;

            Integer[] sortedData = Arrays.copyOf(sortData, sortData.length);
            int target = sortedData[size / 2];

            start = System.nanoTime();
            BinarySearch.search(sortedData, target);
            long searchTime = System.nanoTime() - start;

            System.out.println("QuickSort," + size + "," + sortTime);
            System.out.println("BinarySearch," + size + "," + searchTime);
        }
    }
}
