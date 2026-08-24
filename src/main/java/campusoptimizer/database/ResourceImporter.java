package campusoptimizer.database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ResourceImporter {

        public static void importResources() {

                String sql = """
                                INSERT OR REPLACE INTO resources
                                (
                                    resource_id,
                                    resource_name,
                                    resource_type,
                                    location_id,
                                    availability,
                                    status
                                )
                                VALUES (?, ?, ?, ?, ?, ?)
                                """;

                try (
                                InputStream inputStream = ResourceImporter.class
                                                .getClassLoader()
                                                .getResourceAsStream("data/resources.csv")) {

                        if (inputStream == null) {
                                throw new RuntimeException(
                                                "resources.csv not found.");
                        }

                        try (
                                        BufferedReader reader = new BufferedReader(
                                                        new InputStreamReader(inputStream));

                                        Connection connection = DatabaseConnection.getConnection();

                                        PreparedStatement statement = connection.prepareStatement(sql)) {

                                // Skip CSV header
                                reader.readLine();

                                String line;
                                int count = 0;

                                while ((line = reader.readLine()) != null) {

                                        if (line.trim().isEmpty()) {
                                                continue;
                                        }

                                        String[] data = line.split(",");

                                        if (data.length != 6) {
                                                throw new RuntimeException(
                                                                "Invalid resource row: " + line);
                                        }

                                        statement.setInt(1,
                                                        Integer.parseInt(data[0]));

                                        statement.setString(2, data[1]);

                                        statement.setString(3, data[2]);

                                        statement.setInt(4,
                                                        Integer.parseInt(data[3]));

                                        statement.setString(5, data[4]);

                                        statement.setString(6, data[5]);

                                        statement.executeUpdate();

                                        count++;
                                }

                                System.out.println(
                                                "Resources imported successfully: "
                                                                + count);
                        }

                } catch (Exception e) {

                        System.out.println(
                                        "Resource import failed.");

                        e.printStackTrace();
                }
        }
}