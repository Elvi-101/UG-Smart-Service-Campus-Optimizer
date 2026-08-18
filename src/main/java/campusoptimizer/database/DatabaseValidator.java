package campusoptimizer.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseValidator {

    public static void validateLocations() {

        String sql = "SELECT COUNT(*) AS total FROM locations";

        try (
                Connection connection = DatabaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {

                int total = resultSet.getInt("total");

                System.out.println();
                System.out.println("DATABASE VALIDATION");
                System.out.println("------------------------------");
                System.out.println("Locations stored in database: " + total);

                if (total == 50) {
                    System.out.println("Location validation: PASSED");
                } else {
                    System.out.println(
                            "Location validation: FAILED - expected 50");
                }
            }

        } catch (Exception e) {

            System.out.println("Database validation failed.");
            e.printStackTrace();
        }
    }
}