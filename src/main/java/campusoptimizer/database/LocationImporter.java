package campusoptimizer.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class LocationImporter {

    private static final String CSV_FILE = "data/campus-data.csv";

    private static final String INSERT_SQL = """
            INSERT OR REPLACE INTO locations
            (location_id, name, area, type, latitude, longitude)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    public static void importLocations() {

        int importedCount = 0;

        try (
                Connection connection = DatabaseConnection.getConnection();
                BufferedReader reader = new BufferedReader(
                        new FileReader(CSV_FILE));
                PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {

            // Skip the CSV header
            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] values = line.split(",", -1);

                if (values.length != 6) {
                    System.out.println(
                            "Skipping invalid row: " + line);
                    continue;
                }

                int locationId = Integer.parseInt(values[0].trim());
                String name = values[1].trim();
                String area = values[2].trim();
                String type = values[3].trim();
                double latitude = Double.parseDouble(values[4].trim());
                double longitude = Double.parseDouble(values[5].trim());

                statement.setInt(1, locationId);
                statement.setString(2, name);
                statement.setString(3, area);
                statement.setString(4, type);
                statement.setDouble(5, latitude);
                statement.setDouble(6, longitude);

                statement.executeUpdate();

                importedCount++;
            }

            System.out.println(
                    "Campus locations imported successfully: "
                            + importedCount);

        } catch (Exception e) {

            System.out.println("Location import failed.");
            e.printStackTrace();
        }
    }
}