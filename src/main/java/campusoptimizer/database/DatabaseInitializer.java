package campusoptimizer.database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseInitializer {

    public static void initialize() {

        try (
                Connection connection = DatabaseConnection.getConnection();
                Statement statement = connection.createStatement()) {

            InputStream inputStream = DatabaseInitializer.class
                    .getClassLoader()
                    .getResourceAsStream("schema.sql");

            if (inputStream == null) {
                throw new RuntimeException("schema.sql not found in resources.");
            }

            String schema;

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                schema = reader.lines()
                        .collect(Collectors.joining("\n"));
            }

            String[] statements = schema.split(";");

            for (String sql : statements) {

                sql = sql.trim();

                if (!sql.isEmpty()) {
                    statement.execute(sql);
                }
            }

            System.out.println("Database schema initialized successfully.");

        } catch (Exception e) {

            System.out.println("Database initialization failed.");
            e.printStackTrace();
        }
    }
}