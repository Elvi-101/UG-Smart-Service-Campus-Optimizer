package campusoptimizer.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ServiceRequestImporter {

    private static final String CSV_FILE = "data/service-requests.csv";

    public static void importServiceRequests() {

        String sql = """
                INSERT OR REPLACE INTO service_requests
                (
                    request_id,
                    location_id,
                    service_type,
                    priority,
                    status,
                    reported_at
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
                BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE));

                Connection connection = DatabaseConnection.getConnection();

                PreparedStatement statement = connection.prepareStatement(sql)) {

            // Skip CSV header
            String header = reader.readLine();

            if (header == null) {
                throw new RuntimeException(
                        "service-requests.csv is empty.");
            }

            int count = 0;
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",", -1);

                if (data.length != 6) {
                    throw new RuntimeException(
                            "Invalid service request row: " + line);
                }

                int requestId = Integer.parseInt(data[0].trim());
                int locationId = Integer.parseInt(data[1].trim());

                String serviceType = data[2].trim();
                String priority = data[3].trim();
                String status = data[4].trim();
                String reportedAt = data[5].trim();

                statement.setInt(1, requestId);
                statement.setInt(2, locationId);
                statement.setString(3, serviceType);
                statement.setString(4, priority);
                statement.setString(5, status);
                statement.setString(6, reportedAt);

                statement.executeUpdate();

                count++;
            }

            System.out.println(
                    "Service requests imported successfully: "
                            + count);

        } catch (Exception e) {

            System.out.println(
                    "Service request import failed.");

            e.printStackTrace();
        }
    }
}