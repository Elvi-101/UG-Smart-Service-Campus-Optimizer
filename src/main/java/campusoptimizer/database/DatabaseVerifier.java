package campusoptimizer.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseVerifier {

    public static void verifyTables() {

        String query = """
                SELECT name
                FROM sqlite_master
                WHERE type = 'table'
                ORDER BY name
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println();
            System.out.println("DATABASE TABLES");
            System.out.println("------------------------------");

            while (resultSet.next()) {
                System.out.println("- " + resultSet.getString("name"));
            }

            System.out.println("------------------------------");

        } catch (Exception e) {

            System.out.println("Database verification failed.");
            e.printStackTrace();
        }
    }
}