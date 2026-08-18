package campusoptimizer.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RoadImporter {

    private static final String CSV_FILE = "data/roads.csv";

    private static final String INSERT_SQL = """
            INSERT OR REPLACE INTO roads
            (road_id, from_location_id, to_location_id, distance,
             travel_time, road_condition_weight)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    public static void importRoads() {

        int importedCount = 0;

        try (
                Connection connection = DatabaseConnection.getConnection();
                BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE));
                PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {

            // Skip CSV header
            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] values = line.split(",", -1);

                if (values.length != 4) {
                    System.out.println(
                            "Skipping invalid road row: " + line);
                    continue;
                }

                int roadId = Integer.parseInt(values[0].trim());
                int fromLocationId = Integer.parseInt(values[1].trim());
                int toLocationId = Integer.parseInt(values[2].trim());
                double distance = Double.parseDouble(values[3].trim());

                /*
                 * Travel time is estimated from distance.
                 *
                 * Distance is stored in metres.
                 * Average campus travel speed = 5 m/s.
                 */
                double travelTime = distance / 5.0;

                /*
                 * 1.0 represents normal road condition.
                 */
                double roadConditionWeight = 1.0;

                statement.setInt(1, roadId);
                statement.setInt(2, fromLocationId);
                statement.setInt(3, toLocationId);
                statement.setDouble(4, distance);
                statement.setDouble(5, travelTime);
                statement.setDouble(6, roadConditionWeight);

                statement.executeUpdate();

                importedCount++;
            }

            System.out.println(
                    "Roads imported successfully: " + importedCount);

        } catch (Exception e) {

            System.out.println("Road import failed.");
            e.printStackTrace();
        }
    }
}