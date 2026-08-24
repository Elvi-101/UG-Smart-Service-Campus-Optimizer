package campusoptimizer.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ResourceValidator {

    public static void validateResources() {

        System.out.println();
        System.out.println("RESOURCE DATABASE VALIDATION");
        System.out.println("------------------------------");

        try (Connection connection = DatabaseConnection.getConnection()) {

            // Check total number of resources
            String countSql = "SELECT COUNT(*) FROM resources";

            try (PreparedStatement statement = connection.prepareStatement(countSql);
                    ResultSet result = statement.executeQuery()) {

                int count = result.getInt(1);

                System.out.println(
                        "Resources stored in database: " + count);

                if (count >= 30) {
                    System.out.println(
                            "Resource count validation: PASSED");
                } else {
                    System.out.println(
                            "Resource count validation: FAILED");
                }
            }

            // Check invalid location references
            String locationSql = """
                    SELECT COUNT(*)
                    FROM resources r
                    LEFT JOIN locations l
                    ON r.location_id = l.location_id
                    WHERE l.location_id IS NULL
                    """;

            try (PreparedStatement statement = connection.prepareStatement(locationSql);
                    ResultSet result = statement.executeQuery()) {

                int invalidLocations = result.getInt(1);

                System.out.println(
                        "Invalid location references: "
                                + invalidLocations);

                if (invalidLocations == 0) {
                    System.out.println(
                            "Location foreign key validation: PASSED");
                } else {
                    System.out.println(
                            "Location foreign key validation: FAILED");
                }
            }

            // Check duplicate resource IDs
            String duplicateSql = """
                    SELECT COUNT(*)
                    FROM (
                        SELECT resource_id
                        FROM resources
                        GROUP BY resource_id
                        HAVING COUNT(*) > 1
                    )
                    """;

            try (PreparedStatement statement = connection.prepareStatement(duplicateSql);
                    ResultSet result = statement.executeQuery()) {

                int duplicates = result.getInt(1);

                System.out.println(
                        "Duplicate resource IDs: " + duplicates);

                if (duplicates == 0) {
                    System.out.println(
                            "Resource ID uniqueness validation: PASSED");
                } else {
                    System.out.println(
                            "Resource ID uniqueness validation: FAILED");
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Resource validation failed.");

            e.printStackTrace();
        }
    }
}