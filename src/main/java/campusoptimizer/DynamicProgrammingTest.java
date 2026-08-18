package campusoptimizer;

public class DynamicProgrammingTest {

    public static void main(String[] args) {

        // Test 1: Normal optimization
        int result1 = DynamicProgramming.optimize(
                10,
                new int[]{2, 3, 4, 5},
                new int[]{30, 40, 50, 70}
        );

        check(result1 == 140, "Normal optimization");

        // Test 2: Zero capacity
        int result2 = DynamicProgramming.optimize(
                0,
                new int[]{2, 3, 4},
                new int[]{30, 40, 50}
        );

        check(result2 == 0, "Zero capacity");

        // Test 3: No services
        int result3 = DynamicProgramming.optimize(
                10,
                new int[]{},
                new int[]{}
        );

        check(result3 == 0, "No services");

        // Test 4: Service too large
        int result4 = DynamicProgramming.optimize(
                2,
                new int[]{5},
                new int[]{100}
        );

        check(result4 == 0, "Service exceeds capacity");

        // Test 5: Single service
        int result5 = DynamicProgramming.optimize(
                5,
                new int[]{5},
                new int[]{50}
        );

        check(result5 == 50, "Single service");

        System.out.println();
        System.out.println("All Dynamic Programming tests completed successfully.");
    }

    private static void check(boolean condition, String testName) {

        if (condition) {
            System.out.println("PASSED: " + testName);
        } else {
            throw new AssertionError("FAILED: " + testName);
        }
    }
}