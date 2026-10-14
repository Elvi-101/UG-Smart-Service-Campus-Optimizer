
package campusoptimizer.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseCountCheck {

    public static void main(String[] args) {
        String[] tables = {
            "locations",
            "roads",
            "service_requests",
            "resources"
        };

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            for (String table : tables) {
                String sql = "SELECT COUNT(*) FROM " + table;

                try (ResultSet result = statement.executeQuery(sql)) {
                    if (result.next()) {
                        System.out.println(
                            table + ": " + result.getInt(1) + " records");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Database count check failed.");
            e.printStackTrace();
        }
    }
}
