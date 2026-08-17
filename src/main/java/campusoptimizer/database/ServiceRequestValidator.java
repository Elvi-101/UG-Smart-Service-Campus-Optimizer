package campusoptimizer.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ServiceRequestValidator {

    public static void validateServiceRequests() {

        System.out.println();
        System.out.println("SERVICE REQUEST DATABASE VALIDATION");
        System.out.println("------------------------------");

        try (Connection connection = DatabaseConnection.getConnection()) {

            // Count imported requests
            String countSql = "SELECT COUNT(*) FROM service_requests";

            try (PreparedStatement statement = connection.prepareStatement(countSql);
                    ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    int count = resultSet.getInt(1);

                    System.out.println(
                            "Service requests stored in database: "
                                    + count);

                    if (count == 30) {
                        System.out.println(
                                "Request count validation: PASSED");
                    } else {
                        System.out.println(
                                "Request count validation: FAILED");
                    }
                }
            }

            // Check invalid location references
            String invalidSql = """
                    SELECT COUNT(*)
                    FROM service_requests sr
                    LEFT JOIN locations l
                    ON sr.location_id = l.location_id
                    WHERE l.location_id IS NULL
                    """;

            try (PreparedStatement statement = connection.prepareStatement(invalidSql);
                    ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    int invalidReferences = resultSet.getInt(1);

                    System.out.println(
                            "Invalid location references: "
                                    + invalidReferences);

                    if (invalidReferences == 0) {
                        System.out.println(
                                "Location foreign key validation: PASSED");
                    } else {
                        System.out.println(
                                "Location foreign key validation: FAILED");
                    }
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Service request validation failed.");

            e.printStackTrace();
        }
    }
}