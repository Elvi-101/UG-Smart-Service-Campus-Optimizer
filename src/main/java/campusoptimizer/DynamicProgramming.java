package campusoptimizer;

/**
 * Dynamic Programming solution for the Smart Campus Service Operations Optimizer.
 *
 * Problem:
 * Select campus service requests that give the highest total benefit
 * without exceeding the available operational capacity.
 *
 * This is a 0/1 Knapsack Dynamic Programming problem.
 */
public class DynamicProgramming {

    /**
     * Finds the maximum benefit that can be obtained.
     *
     * @param capacity maximum available capacity
     * @param weights capacity required by each service
     * @param values benefit of each service
     * @return maximum possible benefit
     */
    public static int optimize(int capacity, int[] weights, int[] values) {

        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative.");
        }

        if (weights == null || values == null) {
            throw new IllegalArgumentException("Weights and values cannot be null.");
        }

        if (weights.length != values.length) {
            throw new IllegalArgumentException(
                    "Weights and values must have the same length."
            );
        }

        int n = weights.length;

        // dp[i][c] = maximum benefit using first i services
        // with capacity c.
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {

            int weight = weights[i - 1];
            int value = values[i - 1];

            if (weight < 0) {
                throw new IllegalArgumentException(
                        "Service weight cannot be negative."
                );
            }

            for (int c = 0; c <= capacity; c++) {

                // Do not select the current service.
                dp[i][c] = dp[i - 1][c];

                // Select the current service if it fits.
                if (weight <= c) {
                    dp[i][c] = Math.max(
                            dp[i][c],
                            value + dp[i - 1][c - weight]
                    );
                }
            }
        }

        return dp[n][capacity];
    }

    /**
     * Prints the DP trace table.
     */
    public static void printTraceTable(
            int capacity,
            int[] weights,
            int[] values) {

        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {

            for (int c = 0; c <= capacity; c++) {

                dp[i][c] = dp[i - 1][c];

                if (weights[i - 1] <= c) {
                    dp[i][c] = Math.max(
                            dp[i][c],
                            values[i - 1]
                                    + dp[i - 1][c - weights[i - 1]]
                    );
                }
            }
        }

        System.out.println("DP TRACE TABLE");
        System.out.println("------------------------------");

        for (int i = 0; i <= n; i++) {

            System.out.print("Service " + i + ": ");

            for (int c = 0; c <= capacity; c++) {
                System.out.print(dp[i][c] + " ");
            }

            System.out.println();
        }

        System.out.println("------------------------------");
        System.out.println("Optimal benefit: " + dp[n][capacity]);
    }

    /**
     * Demonstrates the algorithm using campus service requests.
     */
    public static void main(String[] args) {

        /*
         * Example campus services:
         *
         * Service 1: Road maintenance
         * Service 2: Water supply
         * Service 3: Cleaning
         * Service 4: Security support
         *
         * weights = operational capacity required
         * values  = expected benefit
         */

        int capacity = 10;

        int[] weights = {
                2, 3, 4, 5
        };

        int[] values = {
                30, 40, 50, 70
        };

        int optimalBenefit = optimize(
                capacity,
                weights,
                values
        );

        System.out.println("SMART CAMPUS SERVICE OPTIMIZER");
        System.out.println("==============================");
        System.out.println("Available capacity: " + capacity);
        System.out.println("Optimal total benefit: " + optimalBenefit);
        System.out.println();

        printTraceTable(
                capacity,
                weights,
                values
        );
    }
}  