package campusoptimizer.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RoadValidator {

    public static void validateRoads() {

        String countSql = "SELECT COUNT(*) AS total FROM roads";

        String invalidReferenceSql = """
                SELECT COUNT(*) AS invalid_count
                FROM roads r
                LEFT JOIN locations l1
                    ON r.from_location_id = l1.location_id
                LEFT JOIN locations l2
                    ON r.to_location_id = l2.location_id
                WHERE l1.location_id IS NULL
                   OR l2.location_id IS NULL
                """;

        try (Connection connection = DatabaseConnection.getConnection();
                Statement statement = connection.createStatement()) {

            // Count roads
            try (ResultSet resultSet = statement.executeQuery(countSql)) {

                if (resultSet.next()) {

                    int total = resultSet.getInt("total");

                    System.out.println();
                    System.out.println("ROAD DATABASE VALIDATION");
                    System.out.println("------------------------------");
                    System.out.println(
                            "Roads stored in database: " + total);

                    if (total == 110) {
                        System.out.println(
                                "Road count validation: PASSED");
                    } else {
                        System.out.println(
                                "Road count validation: FAILED - expected 110");
                    }
                }
            }

            // Validate location references
            try (ResultSet resultSet = statement.executeQuery(invalidReferenceSql)) {

                if (resultSet.next()) {

                    int invalidReferences = resultSet.getInt("invalid_count");

                    System.out.println(
                            "Invalid location references: "
                                    + invalidReferences);

                    if (invalidReferences == 0) {
                        System.out.println(
                                "Foreign key validation: PASSED");
                    } else {
                        System.out.println(
                                "Foreign key validation: FAILED");
                    }
                }
            }

        } catch (Exception e) {

            System.out.println("Road validation failed.");
            e.printStackTrace();
        }
    }
}