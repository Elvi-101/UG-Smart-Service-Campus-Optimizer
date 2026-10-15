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
import campusoptimizer.database.GraphLoader;

import campusoptimizer.datastructures.Heap;
import campusoptimizer.datastructures.DynamicArray;
import campusoptimizer.algorithms.GreedyScheduler;
import campusoptimizer.algorithms.BinarySearch;
import campusoptimizer.algorithms.QuickSort;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("==========================================");
        System.out.println("UG SMART SERVICE OPERATIONS OPTIMIZER");
        System.out.println("==========================================");
        System.out.println("System starting...");

        if (!setupDatabase()) {
            return;
        }

        loadAndValidateData();

        runMenu();
    }

    // =====================================================
    // DATABASE SETUP
    // =====================================================

    private static boolean setupDatabase() {

        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Database connection successful!");
            System.out.println("Database: SQLite");
            System.out.println("Connection established.");
        } catch (Exception e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
            return false;
        }

        DatabaseInitializer.initialize();
        return true;
    }

    private static void loadAndValidateData() {

        System.out.println();
        System.out.println("IMPORTING CAMPUS LOCATIONS");
        System.out.println("------------------------------");
        LocationImporter.importLocations();
        DatabaseValidator.validateLocations();

        System.out.println();
        System.out.println("IMPORTING CAMPUS ROADS");
        System.out.println("------------------------------");
        RoadImporter.importRoads();
        RoadValidator.validateRoads();

        System.out.println();
        System.out.println("IMPORTING SERVICE REQUESTS");
        System.out.println("------------------------------");
        ServiceRequestImporter.importServiceRequests();
        ServiceRequestValidator.validateServiceRequests();

        System.out.println();
        System.out.println("IMPORTING CAMPUS RESOURCES");
        System.out.println("------------------------------");
        ResourceImporter.importResources();
        ResourceValidator.validateResources();

        System.out.println();
        System.out.println("==========================================");
        System.out.println("DATABASE IMPORT PROCESS COMPLETED");
        System.out.println("==========================================");
    }

    // =====================================================
    // CONSOLE MENU
    // =====================================================

    private static void runMenu() {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("==========================================");
            System.out.println("MAIN MENU");
            System.out.println("==========================================");
            System.out.println("1. Find route between two locations (BFS - fewest hops)");
            System.out.println("2. Find fastest route between two locations (Dijkstra)");
            System.out.println("3. Assign resources to requests (Greedy)");
            System.out.println("4. Dispatch order by priority (Heap)");
            System.out.println("5. Search a service request (Binary Search)");
            System.out.println("6. Sort service requests by time (QuickSort)");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> runBFSDemo(scanner);
                case "2" -> runDijkstraDemo(scanner);
                case "3" -> runGreedySchedulerDemo();
                case "4" -> runHeapDemo();
                case "5" -> runBinarySearchDemo(scanner);
                case "6" -> runQuickSortDemo();
                case "7" -> {
                    System.out.println("Exiting. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }

    // =====================================================
    // 1. BFS DEMO
    // =====================================================

    private static void runBFSDemo(Scanner scanner) {
        Graph graph = GraphLoader.loadGraph();

        System.out.print("Enter start location ID: ");
        String start = scanner.nextLine().trim();
        System.out.print("Enter destination location ID: ");
        String end = scanner.nextLine().trim();

        try {
            List<String> path = BFS.shortestPathByHops(graph, start, end);

            if (path.isEmpty()) {
                System.out.println("No path found between " + start + " and " + end + ".");
            } else {
                System.out.println("Path (" + (path.size() - 1) + " hops): " + String.join(" -> ", path));
            }
        } catch (NoSuchElementException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // =====================================================
    // 2. DIJKSTRA DEMO
    // =====================================================

    private static void runDijkstraDemo(Scanner scanner) {
        Graph graph = GraphLoader.loadGraph();

        System.out.print("Enter start location ID: ");
        String start = scanner.nextLine().trim();
        System.out.print("Enter destination location ID: ");
        String end = scanner.nextLine().trim();

        if (!graph.hasLocation(start) || !graph.hasLocation(end)) {
            System.out.println("One or both locations not found in the graph.");
            return;
        }

        Dijkstra.Result result = Dijkstra.run(graph, start);
        List<String> path = result.reconstructPath(end);

        if (path.isEmpty()) {
            System.out.println("No path found between " + start + " and " + end + ".");
        } else {
            int distance = result.distances.get(end);
            System.out.println("Fastest route (distance " + distance + "): " + String.join(" -> ", path));
        }
    }

    // =====================================================
    // 3. GREEDY SCHEDULER DEMO
    // =====================================================

    private static void runGreedySchedulerDemo() {

        record RequestRecord(int requestId, String serviceType, String priority) {
        }
        record ResourceRecord(int resourceId, String resourceName, String resourceType) {
        }

        Map<String, Integer> priorityRank = Map.of(
                "HIGH", 3,
                "MEDIUM", 2,
                "LOW", 1);

        Comparator<RequestRecord> byPriority = (a, b) -> priorityRank.getOrDefault(a.priority(), 0)
                - priorityRank.getOrDefault(b.priority(), 0);

        GreedyScheduler<RequestRecord> scheduler = new GreedyScheduler<>(byPriority);
        List<ResourceRecord> availableResources = new ArrayList<>();

        String requestSql = "SELECT request_id, service_type, priority FROM service_requests WHERE status = 'OPEN'";
        String resourceSql = "SELECT resource_id, resource_name, resource_type FROM resources WHERE availability = 'AVAILABLE'";

        try (Connection connection = DatabaseConnection.getConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(requestSql);
                    ResultSet result = statement.executeQuery()) {

                while (result.next()) {
                    scheduler.addRequest(new RequestRecord(
                            result.getInt("request_id"),
                            result.getString("service_type"),
                            result.getString("priority")));
                }
            }

            try (PreparedStatement statement = connection.prepareStatement(resourceSql);
                    ResultSet result = statement.executeQuery()) {

                while (result.next()) {
                    availableResources.add(new ResourceRecord(
                            result.getInt("resource_id"),
                            result.getString("resource_name"),
                            result.getString("resource_type")));
                }
            }

        } catch (Exception e) {
            System.out.println("Failed to load scheduling data.");
            e.printStackTrace();
            return;
        }

        System.out.println("Greedy resource assignment (highest priority first):");
        System.out.println("------------------------------");

        int position = 1;

        while (!scheduler.isEmpty()) {

            RequestRecord request = scheduler.selectNext();

            ResourceRecord assigned = null;
            for (ResourceRecord candidate : availableResources) {
                if (candidate.resourceType().equalsIgnoreCase(request.serviceType())) {
                    assigned = candidate;
                    break;
                }
            }

            if (assigned != null) {
                availableResources.remove(assigned);
                System.out.printf("%d. Request #%d (%s, %s) -> assigned %s%n",
                        position++, request.requestId(), request.serviceType(),
                        request.priority(), assigned.resourceName());
            } else {
                System.out.printf("%d. Request #%d (%s, %s) -> NO RESOURCE AVAILABLE%n",
                        position++, request.requestId(), request.serviceType(), request.priority());
            }
        }
    }

    // =====================================================
    // 4. HEAP DISPATCH DEMO
    // =====================================================

    private static void runHeapDemo() {

        record RequestRecord(int requestId, String serviceType, String priority) {
        }

        Map<String, Integer> priorityRank = Map.of(
                "HIGH", 3,
                "MEDIUM", 2,
                "LOW", 1);

        Comparator<RequestRecord> byPriority = (a, b) -> priorityRank.getOrDefault(a.priority(), 0)
                - priorityRank.getOrDefault(b.priority(), 0);

        Heap<RequestRecord> dispatchQueue = new Heap<>(byPriority);

        String sql = "SELECT request_id, service_type, priority FROM service_requests WHERE status = 'OPEN'";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                dispatchQueue.insert(new RequestRecord(
                        result.getInt("request_id"),
                        result.getString("service_type"),
                        result.getString("priority")));
            }

        } catch (Exception e) {
            System.out.println("Failed to load service requests.");
            e.printStackTrace();
            return;
        }

        System.out.println("Dispatch order (highest priority first):");
        System.out.println("------------------------------");

        int position = 1;

        while (!dispatchQueue.isEmpty()) {
            RequestRecord next = dispatchQueue.extract();
            System.out.printf("%d. Request #%d - %s (%s)%n",
                    position++, next.requestId(), next.serviceType(), next.priority());
        }
    }

    // =====================================================
    // 5. BINARY SEARCH DEMO
    // =====================================================

    private static void runBinarySearchDemo(Scanner scanner) {

        String sql = "SELECT request_id FROM service_requests ORDER BY request_id";

        List<Integer> idList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                idList.add(result.getInt("request_id"));
            }

        } catch (Exception e) {
            System.out.println("Failed to load service requests.");
            e.printStackTrace();
            return;
        }

        Integer[] sortedIds = idList.toArray(new Integer[0]);

        System.out.print("Enter request ID to search for: ");
        int target = Integer.parseInt(scanner.nextLine().trim());

        int index = BinarySearch.search(sortedIds, target);

        if (index == -1) {
            System.out.println("Request #" + target + " not found.");
        } else {
            System.out.println("Request #" + target + " found at sorted index " + index + ".");
        }
    }

    // =====================================================
    // 6. QUICKSORT DEMO
    // =====================================================

    private static void runQuickSortDemo() {

        record RequestRecord(int requestId, String reportedAt) implements Comparable<RequestRecord> {
            @Override
            public int compareTo(RequestRecord other) {
                return this.reportedAt.compareTo(other.reportedAt);
            }
        }

        String sql = "SELECT request_id, reported_at FROM service_requests";

        DynamicArray<RequestRecord> requests = new DynamicArray<>();

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                requests.add(new RequestRecord(
                        result.getInt("request_id"),
                        result.getString("reported_at")));
            }

        } catch (Exception e) {
            System.out.println("Failed to load service requests.");
            e.printStackTrace();
            return;
        }

        System.out.println("Sorting " + requests.size() + " requests by report time...");

        QuickSort.sort(requests);

        System.out.println("Sorted requests (earliest first):");
        System.out.println("------------------------------");

        int limit = Math.min(requests.size(), 10);
        for (int i = 0; i < limit; i++) {
            RequestRecord r = requests.get(i);
            System.out.printf("%d. Request #%d - %s%n", i + 1, r.requestId(), r.reportedAt());

            if (requests.size() > 10) {
                System.out.println("... (" + (requests.size() - 10) + " more)");
            }
        }
    }
}