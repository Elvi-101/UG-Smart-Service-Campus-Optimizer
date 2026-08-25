package campusoptimizer.database;

import campusoptimizer.Graph;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GraphLoader {

    /**
     * Builds a Graph from the locations and roads tables in the database.
     * Vertices are location IDs (as strings); edge weight is distance.
     */
    public static Graph loadGraph() {

        Graph graph = new Graph();

        try (Connection connection = DatabaseConnection.getConnection()) {

            // Load all locations as vertices first, so isolated locations
            // (no roads yet) still appear in the graph.
            String locationSql = "SELECT location_id FROM locations";

            try (PreparedStatement statement = connection.prepareStatement(locationSql);
                    ResultSet result = statement.executeQuery()) {

                while (result.next()) {
                    graph.addLocation(String.valueOf(result.getInt("location_id")));
                }
            }

            // Load all roads as edges.
            String roadSql = "SELECT from_location_id, to_location_id, distance FROM roads";

            try (PreparedStatement statement = connection.prepareStatement(roadSql);
                    ResultSet result = statement.executeQuery()) {

                while (result.next()) {
                    String from = String.valueOf(result.getInt("from_location_id"));
                    String to = String.valueOf(result.getInt("to_location_id"));
                    int distance = (int) Math.round(result.getDouble("distance"));

                    graph.addRoute(from, to, distance);
                }
            }

        } catch (Exception e) {
            System.out.println("Graph loading failed.");
            e.printStackTrace();
        }

        return graph;
    }
}