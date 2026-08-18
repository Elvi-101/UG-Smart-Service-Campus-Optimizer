package campusoptimizer;

import campusoptimizer.database.DatabaseConnection;
import campusoptimizer.database.DatabaseInitializer;
import campusoptimizer.database.LocationImporter;
import campusoptimizer.database.DatabaseValidator;
import campusoptimizer.database.RoadImporter;
import campusoptimizer.database.RoadValidator;
import campusoptimizer.database.ServiceRequestImporter;
import campusoptimizer.database.ServiceRequestValidator;
import campusoptimizer.database.ResourceImporter;
import campusoptimizer.database.ResourceValidator;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        System.out.println("==========================================");
        System.out.println("UG SMART SERVICE OPERATIONS OPTIMIZER");
        System.out.println("==========================================");
        System.out.println("System starting...");

        // =====================================================
        // 1. TEST DATABASE CONNECTION
        // =====================================================

        try (Connection connection = DatabaseConnection.getConnection()) {

            System.out.println("Database connection successful!");
            System.out.println("Database: SQLite");
            System.out.println("Connection established.");

        } catch (Exception e) {

            System.out.println("Database connection failed.");
            e.printStackTrace();
            return;
        }

        // =====================================================
        // 2. INITIALIZE DATABASE SCHEMA
        // =====================================================

        DatabaseInitializer.initialize();

        // =====================================================
        // 3. IMPORT CAMPUS LOCATIONS
        // =====================================================

        System.out.println();
        System.out.println("IMPORTING CAMPUS LOCATIONS");
        System.out.println("------------------------------");

        LocationImporter.importLocations();

        // Validate locations
        DatabaseValidator.validateLocations();

        // =====================================================
        // 4. IMPORT CAMPUS ROADS
        // =====================================================

        System.out.println();
        System.out.println("IMPORTING CAMPUS ROADS");
        System.out.println("------------------------------");

        RoadImporter.importRoads();

        // Validate roads
        RoadValidator.validateRoads();

        // =====================================================
        // 5. IMPORT SERVICE REQUESTS
        // =====================================================

        System.out.println();
        System.out.println("IMPORTING SERVICE REQUESTS");
        System.out.println("------------------------------");

        ServiceRequestImporter.importServiceRequests();

        // Validate service requests
        ServiceRequestValidator.validateServiceRequests();

        // =====================================================
        // 6. IMPORT CAMPUS RESOURCES
        // =====================================================

        System.out.println();
        System.out.println("IMPORTING CAMPUS RESOURCES");
        System.out.println("------------------------------");

        ResourceImporter.importResources();

        // Validate resources
        ResourceValidator.validateResources();

        // =====================================================
        // 7. COMPLETION
        // =====================================================

        System.out.println();
        System.out.println("==========================================");
        System.out.println("DATABASE IMPORT PROCESS COMPLETED");
        System.out.println("==========================================");
    }
}