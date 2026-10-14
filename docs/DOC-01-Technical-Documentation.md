# Smart Campus Service Operations Optimizer

## 1. Testing and Correctness Evidence

### 1.1 Testing Approach

The project uses Maven and JUnit-based unit tests to evaluate the behavior of its algorithms and data structures. The tests check expected outputs and help identify errors in normal operations and edge cases.

### 1.2 Test Execution

The following command was executed from the project root directory:

```powershell
mvn clean test
```

### 1.3 Test Results

The latest test execution completed successfully.

| Metric | Result |
|---|---:|
| Total tests executed | 82 |
| Failures | 0 |
| Errors | 0 |
| Skipped tests | 0 |
| Build status | BUILD SUCCESS |
| Java target version | 21 |
| Total build time | 16.192 seconds |

### 1.4 Test Distribution

| Test class | Tests executed | Failures |
|---|---:|---:|
| BinarySearchTest | 13 | 0 |
| GreedySchedulerTest | 7 | 0 |
| QuickSortTest | 11 | 0 |
| BFSTest | 13 | 0 |
| DynamicArrayTest | 17 | 0 |
| HashTableTest | 13 | 0 |
| HeapTest | 8 | 0 |
| **Total** | **82** | **0** |

### 1.5 Interpretation

All 82 tests passed, with no failures, errors, or skipped tests. This provides evidence that the tested methods behaved as expected for the test cases executed.

However, passing unit tests alone does not establish complete correctness for every possible input. Additional algorithm trace tables, correctness explanations, edge-case analysis, integration testing, and performance experiments contribute to a more comprehensive evaluation.

**Test evidence:** The test-results screenshot is stored at `docs/image.png`.

---

## 2. Binary Search Algorithm

### 2.1 Purpose

Binary Search finds a target element in a sorted collection. In this project, it is implemented in `BinarySearch.java` and supports arrays and the project's `DynamicArray` data structure.

### 2.2 How It Works

The algorithm maintains two boundaries, `low` and `high`, and calculates the middle index.

- If the middle element equals the target, its index is returned.
- If the target is greater than the middle element, the search continues in the right half.
- If the target is smaller than the middle element, the search continues in the left half.
- If the search range becomes empty, the algorithm returns `-1`.

**Precondition:** The collection must be sorted in ascending order.

### 2.3 Trace Table

Consider the sorted array:

`[10, 20, 30, 40, 50, 60, 70]`

Target: `60`

| Step | Low | High | Mid | Middle value | Action |
|---|---:|---:|---:|---:|---|
| 1 | 0 | 6 | 3 | 40 | Search right half |
| 2 | 4 | 6 | 5 | 60 | Target found |

**Output:** Index `5`.

### 2.4 Correctness Reasoning

Initially, the search range contains every possible index where the target could occur. At each iteration, the algorithm compares the target with the middle element. Because the collection is sorted, the comparison allows the half that cannot contain the target to be discarded.

If the middle element equals the target, the algorithm returns its index. If the search range becomes empty, no matching element exists, so the algorithm returns `-1`.

Therefore, for correctly sorted input and a valid comparison operation, Binary Search returns a matching index when the target exists and `-1` otherwise.

### 2.5 Complexity Analysis

| Case | Time complexity |
|---|---|
| Best case | O(1) |
| Average case | O(log n) |
| Worst case | O(log n) |
| Auxiliary space | O(1) |

The iterative implementation uses constant extra space. Its logarithmic search time comes from reducing the remaining search range by approximately half at each iteration.

### 2.6 Test Evidence

The `BinarySearchTest` class executed 13 tests. All 13 passed, with zero failures, errors, or skipped tests during the latest recorded `mvn clean test` run.

The documented test scenarios include successful searches, missing targets, empty and single-element arrays, duplicate values, null inputs, and searches using `DynamicArray`.

---

## 3. Hash Table Data Structure

### 3.1 Purpose

A Hash Table stores data as key-value pairs and supports operations such as insertion, retrieval, key lookup, and removal. The project implementation is contained in `HashTable.java`.

### 3.2 Internal Structure

The implementation uses an array of buckets. Each bucket can hold a linked chain of entries, with each entry containing a key, a value, and a reference to the next entry.

The default initial capacity is 16, and the load-factor threshold is 0.75.

### 3.3 Hashing and Collision Handling

A key's hash code is converted into a valid bucket index using `Math.floorMod()`, which handles negative hash codes safely.

Different keys can map to the same bucket. The implementation handles collisions through **separate chaining**, where multiple entries are stored in a linked chain at the same bucket.

When an existing key is inserted again, its value is updated rather than creating a duplicate entry.

### 3.4 Resizing

When the number of entries divided by the bucket count exceeds the load-factor threshold, the bucket array is expanded to twice its previous capacity. Existing entries are redistributed using their hash codes and the new bucket count.

Resizing helps reduce long collision chains as the number of stored entries grows.

### 3.5 Main Operations

| Operation | Description |
|---|---|
| `put(key, value)` | Inserts a new key-value pair or updates an existing key. |
| `get(key)` | Retrieves a value; throws `NoSuchElementException` if the key is absent. |
| `containsKey(key)` | Checks whether a key exists. |
| `remove(key)` | Removes an entry and returns its value; throws `NoSuchElementException` if absent. |
| `size()` | Returns the number of entries. |
| `isEmpty()` | Checks whether the table contains no entries. |

Null keys are rejected by the implementation.

### 3.6 Complexity Analysis

| Operation | Average case | Worst case |
|---|---|---|
| Insertion (`put`) | O(1) amortized | O(n), including resizing |
| Retrieval (`get`) | O(1) | O(n) |
| Key lookup (`containsKey`) | O(1) | O(n) |
| Removal (`remove`) | O(1) | O(n) |

Average-case performance assumes that keys are distributed reasonably across the buckets. In the worst case, many keys may occupy the same bucket, requiring a linear scan through the chain.

### 3.7 Correctness and Test Evidence

The implementation maintains the number of entries when inserting, updating, and removing keys. Collision handling preserves multiple distinct keys in the same bucket, while resizing redistributes entries into the expanded bucket array.

The `HashTableTest` class executed 13 tests, all passing with zero failures, errors, or skipped tests during the latest recorded Maven test run.

These results provide evidence for the tested operations. Additional tests and code inspection may be required to establish behavior for all possible key distributions and input cases.

---

## 4. Quick Sort Algorithm

### 4.1 Purpose

Quick Sort arranges elements in order. The project implements an in-place generic sorting algorithm in `QuickSort.java` for arrays and the project's `DynamicArray` data structure.

### 4.2 How It Works

The implementation uses the Lomuto partition scheme:

1. Select the last element in the current range as the pivot.
2. Compare the other elements with the pivot.
3. Move elements less than or equal to the pivot to its left.
4. Place the pivot in its partition position.
5. Recursively sort the elements on the left and right of the pivot.

### 4.3 Trace Table

Consider the array:

`[4, 2, 5, 1, 3]`

The first partition selects `3` as the pivot.

| Step | Action | Array state |
|---|---|---|
| 1 | Initial array | `[4, 2, 5, 1, 3]` |
| 2 | Compare `4` with pivot `3` | `[4, 2, 5, 1, 3]` |
| 3 | Move `2` before the pivot | `[2, 4, 5, 1, 3]` |
| 4 | Compare `5` with pivot `3` | `[2, 4, 5, 1, 3]` |
| 5 | Move `1` before the pivot | `[2, 1, 5, 4, 3]` |
| 6 | Place pivot in its partition position | `[2, 1, 3, 4, 5]` |

The left partition `[2, 1]` and right partition `[4, 5]` are then sorted recursively.

**Final sorted array:** `[1, 2, 3, 4, 5]`

### 4.4 Correctness Reasoning

During partitioning, elements less than or equal to the pivot are moved to its left, while greater elements remain to its right. The pivot is then placed in its partition position.

The algorithm recursively sorts both partitions. When a partition contains zero or one element, it is already sorted. Therefore, assuming the partition operation is implemented correctly and comparisons are consistent, the recursive process produces a sorted array.

### 4.5 Complexity Analysis

| Case | Time complexity |
|---|---|
| Best case | O(n log n) |
| Average case | O(n log n) |
| Worst case | O(nÂ²) |

Quick Sort sorts in place, but recursive calls require stack space. Its auxiliary space is typically O(log n) with balanced partitions and can reach O(n) with highly unbalanced partitions.

### 4.6 Test Evidence

The `QuickSortTest` class executed 11 tests, all passing with zero failures, errors, or skipped tests during the latest recorded Maven test run.

These tests provide evidence for the cases they cover. The test source should be reviewed to identify the exact input scenarios tested.

---

## 5. Breadth-First Search (BFS)

### 5.1 Purpose

Breadth-First Search is a graph traversal algorithm that explores vertices level by level, starting from a specified vertex. In this project, graph traversal can help explore connections between campus locations.

### 5.2 How It Works

BFS uses a queue to process vertices in the order they are discovered.

1. Start at the source vertex and mark it as visited.
2. Add the source vertex to the queue.
3. Remove the vertex at the front of the queue.
4. Visit each unvisited neighbor and add it to the queue.
5. Repeat until the queue is empty.

Marking vertices as visited prevents the algorithm from repeatedly processing the same vertex.

### 5.3 Trace Table

Consider this undirected graph:

- A connects to B and C.
- B connects to D and E.
- C connects to F.

Starting vertex: A.

| Step | Vertex processed | Newly discovered vertices | Queue after processing |
|---|---|---|---|
| 1 | A | B, C | B, C |
| 2 | B | D, E | C, D, E |
| 3 | C | F | D, E, F |
| 4 | D | None | E, F |
| 5 | E | None | F |
| 6 | F | None | Empty |

**Traversal order:** A, B, C, D, E, F.

The exact order of neighboring vertices depends on the graph's neighbor iteration order.

### 5.4 Correctness Reasoning

BFS marks the source vertex as visited and processes discovered vertices in queue order. Each time a vertex is processed, its unvisited neighbors are discovered and added to the queue.

Because vertices are processed in the order they are discovered, all vertices at a given distance in number of edges from the source are processed before vertices at a greater distance. The visited check prevents repeated discovery.

Consequently, BFS visits every vertex reachable from the source, assuming the graph and its neighbor relationships are valid and the queue is processed until empty.

### 5.5 Complexity Analysis

For an adjacency-list graph, BFS has a time complexity of O(V + E), where V is the number of vertices and E is the number of edges. Its auxiliary space complexity is O(V) for the queue and visited tracking.

### 5.6 Test Evidence

The `BFSTest` class executed 13 tests. All 13 passed, with zero failures, errors, or skipped tests during the latest recorded Maven test run.

The test source should be reviewed to identify the exact graph structures, traversal expectations, and edge cases covered.

---

## 6. Dynamic Array Data Structure

### 6.1 Purpose

A Dynamic Array stores elements in indexed positions and can grow when additional capacity is required. The project implements this structure in `DynamicArray.java`.

### 6.2 How It Works

Unlike a fixed-size array, a dynamic array can increase its storage capacity when it becomes full. It maintains a logical size representing the number of stored elements and an underlying array used to hold them.

Elements can be accessed by index, appended, and managed through the operations provided by the implementation.

When the current capacity is insufficient, the structure allocates a larger array and copies the existing elements into it.

### 6.3 Example

Consider adding the elements `10`, `20`, and `30`.

| Operation | Logical contents |
|---|---|
| Initially empty | `[]` |
| Add 10 | `[10]` |
| Add 20 | `[10, 20]` |
| Add 30 | `[10, 20, 30]` |
| Access index 1 | `20` |

Array indexes begin at zero, so index `1` refers to the second element.

### 6.4 Complexity Analysis

| Operation | Typical time complexity |
|---|---|
| Access by index | O(1) |
| Append an element | O(1) amortized |
| Append when resizing is needed | O(n) |
| Search by value, if implemented as a linear scan | O(n) |

These are standard dynamic-array expectations. The exact complexity of each operation should be confirmed against the project's implementation.

### 6.5 Correctness and Test Evidence

The `DynamicArrayTest` class executed 17 tests, all passing with zero failures, errors, or skipped tests during the latest recorded Maven test run.

The test source should be reviewed to confirm which operations, boundary conditions, and resizing behaviors are covered.

### 6.6 Limitations

A dynamic array provides efficient indexed access, but inserting or removing elements in the middle may require shifting other elements. Resizing may also require allocating new storage and copying existing elements.

---

## 7. Heap Data Structure

### 7.1 Purpose

A heap is a tree-based data structure commonly implemented using an array. It maintains an ordering property that makes it efficient to retrieve the highest- or lowest-priority element.

### 7.2 Heap Property

In a max-heap, every parent is greater than or equal to its children, so the largest element is stored at the root.

For an array-based heap using zero-based indexing:

- Parent index: `(i - 1) / 2`
- Left child index: `2 * i + 1`
- Right child index: `2 * i + 2`

Integer division is used when calculating the parent index.

### 7.3 Example of a Max-Heap

Consider the following heap:

```text
        90
       /  \
      70   60
     / \
    20  40
```

Array representation: `[90, 70, 60, 20, 40]`

The largest value, `90`, is at the root.

### 7.4 Main Operations

| Operation | Typical time complexity |
|---|---|
| Retrieve maximum | O(1) |
| Insert an element | O(log n) |
| Remove maximum | O(log n) |
| Build a heap using bottom-up construction | O(n) |

The exact supported operations and heap type should be confirmed against the project's `Heap.java` implementation.

### 7.5 Correctness Reasoning

A heap maintains its ordering property after insertions and removals. During insertion, an element moves upward as necessary. During removal of the root, the last element replaces it and moves downward until the heap property is restored.

When these operations preserve the heap property, the root remains the maximum element in a max-heap or the minimum element in a min-heap.

### 7.6 Test Evidence

The `HeapTest` class executed 8 tests, all passing with zero failures, errors, or skipped tests during the latest recorded Maven test run.

The test source should be checked to establish which operations and edge cases are explicitly covered.

---

## 8. Greedy Scheduler Algorithm

### 8.1 Purpose

The Greedy Scheduler selects the highest-priority available request at each step. It uses a heap and a supplied comparator to determine the order in which requests are selected.

### 8.2 How It Works

The implementation follows these steps:

1. Create a scheduler with a priority comparator.
2. Add service requests to the heap using `addRequest()`.
3. Select the next request using `selectNext()`, which extracts an item from the heap.
4. Repeat the extraction process until no requests remain.
5. Use `createSchedule()` to return all selected requests in a `DynamicArray`.

The scheduler is generic, meaning it can work with different request types. The comparator determines their priority. In the unit tests, `Comparator.naturalOrder()` is used with integers, so larger numbers are selected first.

### 8.3 Trace Table

The following trace is based on the `schedulerCreatesCorrectPriorityOrder()` unit test.

**Input requests:** `5, 10, 3, 8`

**Comparator:** `Comparator.naturalOrder()`

| Step | Operation | Selected request | Remaining requests |
|---|---|---:|---|
| 1 | Add all requests | â€” | 5, 10, 3, 8 |
| 2 | Extract highest priority | 10 | 8, 5, 3 |
| 3 | Extract next priority | 8 | 5, 3 |
| 4 | Extract next priority | 5 | 3 |
| 5 | Extract final request | 3 | Empty |

**Final schedule:** `10, 8, 5, 3`

The remaining-request column shows requests left after each extraction, ordered by priority for readability. It does not represent the heap's internal storage arrangement.

### 8.4 Correctness Considerations

The scheduler's correctness depends on the supplied comparator and the behavior of the heap. Each call to `selectNext()` extracts the next item according to the heap's priority ordering.

The unit tests verify that the scheduler:

- Selects the highest-priority integer first.
- Produces the expected complete schedule.
- Starts with no pending requests.
- Updates the pending-request count after selection.
- Becomes empty after creating a complete schedule.
- Throws a `NoSuchElementException` when selecting from an empty scheduler.
- Handles duplicate priorities.

These tests support the expected behavior of the implementation. They do not, by themselves, prove that the scheduler finds a globally optimal solution to every real-world campus service scheduling problem.

### 8.5 Complexity Analysis

Let n represent the number of pending requests.

- **Adding one request:** O(log n), assuming standard heap insertion.
- **Selecting one request:** O(log n), assuming standard heap extraction.
- **Creating the complete schedule:** O(n log n), because the implementation repeatedly extracts requests from the heap.
- **Heap storage:** O(n).
- **Returned schedule:** O(n) additional space.

These complexities assume the heap's insertion and extraction operations have their standard binary-heap costs.

### 8.6 Test Evidence

The `GreedySchedulerTest` class contains seven unit tests. In the latest recorded `mvn clean test` run, all seven passed, with zero failures, errors, or skipped tests.

The overall project test results were 82 tests passed, with zero failures, errors, or skipped tests, and Maven reported `BUILD SUCCESS`.

---

## 9. System Architecture and Integration

### 9.1 Architecture Overview

The Smart Campus Service Operations Optimizer is a Java application organized into components responsible for data structures, algorithms, database access, and data processing.

The main application entry point is `campusoptimizer.Main`. The application initializes the database and runs the location, road, service request, and resource import and validation processes.

### 9.2 Main Components

| Component | Responsibility |
|---|---|
| `Main` | Coordinates application startup and initialization. |
| Database connection component | Establishes a connection to the SQLite database using JDBC. |
| Database initializer | Creates or initializes the database schema. |
| Data importers | Load location, road, service request, and resource data. |
| Validators | Check imported data against applicable rules. |
| Data structures | Provide storage and organization mechanisms for application data. |
| Algorithms | Perform searching, sorting, graph traversal, and scheduling tasks. |

### 9.3 Database Integration

The application uses SQLite as its database and JDBC to communicate with it. The database stores information about campus locations, roads, service requests, resources, algorithm runs, and audit events.

Primary keys identify individual records, while foreign keys establish relationships between related tables. Constraints help prevent invalid values, such as negative road distances and travel times.

### 9.4 Application Flow

The main application flow is:

1. Start the application through `campusoptimizer.Main`.
2. Establish a database connection.
3. Initialize the database schema.
4. Import location and road data.
5. Import service requests and resource data.
6. Validate the imported information.
7. Use the relevant algorithms and data structures for supported operations.

This sequence describes the startup flow previously identified in the project. The exact runtime behavior should be confirmed against the current source code.

### 9.5 Integration Considerations

The database connection must be configured correctly, the SQLite JDBC driver must be available at runtime, and imported records must satisfy the schema constraints. Data importers and validators must also work with the same schema and expected input formats.

The successful Maven unit-test run provides evidence for the tested components, but it does not independently prove that the entire application integrates correctly with a live database and every dataset.

---

## 10. Database Schema and Dataset

### 10.1 Database Tables

The project uses an SQLite database to store campus locations, roads, service requests, resources, algorithm execution records, and audit events.

| Table | Purpose |
|---|---|
| `locations` | Stores campus location details, including name, area, type, latitude, and longitude. |
| `roads` | Stores connections between locations, including distance, calculated travel time, and road-condition weight. |
| `service_requests` | Stores service requests, including locations, service types, priorities, statuses, and reporting timestamps. |
| `resources` | Stores resource names, types, locations, availability, and statuses. |
| `algorithm_runs` | Stores algorithm execution details, including algorithm name, input size, execution time, memory measurement, and run date. |
| `audit_events` | Stores event types, descriptions, and creation timestamps. |

### 10.2 Relationships and Constraints

The `locations` table uses `location_id` as its primary key. The `roads` table connects locations through `from_location_id` and `to_location_id`. The `service_requests` and `resources` tables also associate records with campus locations.

The database schema includes constraints to prevent negative road distances and travel times, require positive road-condition weights, and prevent negative algorithm input sizes and execution times.

### 10.3 Dataset Import

The project uses four CSV files to populate the main operational tables.

| Dataset file | Database table | Number of data records |
|---|---|---:|
| `campus-data.csv` | `locations` | 50 |
| `roads.csv` | `roads` | 110 |
| `service-requests.csv` | `service_requests` | 300 |
| `resources.csv` | `resources` | 30 |
| **Total** | | **490** |

The record counts exclude CSV header rows. The location importer reads `campus-data.csv`, the road importer reads `roads.csv`, and the service request importer reads `service-requests.csv`. The resource importer reads `resources.csv` from the application's classpath.

The location dataset contains six fields: location ID, name, area, type, latitude, and longitude. The road dataset contains four fields: road ID, starting location ID, destination location ID, and distance. The service request dataset contains six fields: request ID, location ID, service type, priority, status, and reporting timestamp. The resource dataset contains six fields: resource ID, resource name, resource type, location ID, availability, and status.

The road importer calculates travel time using `distance / 5.0`, assuming distance is measured in metres and the assumed average campus travel speed is 5 metres per second. It assigns a road-condition weight of `1.0` to imported roads.

### 10.4 Data Quality and Validation

The project includes database and data-specific validation components for checking imported information. Importers handle malformed records according to their individual implementations. For example, the location importer skips blank lines and rows that do not contain exactly six fields, while the service request and resource importers throw errors for nonblank malformed records.

A JDBC verification utility was executed to count records in the four operational database tables. The results were:

| Database table | Verified records |
|---|---:|
| `locations` | 50 |
| `roads` | 110 |
| `service_requests` | 300 |
| `resources` | 30 |

The database counts match the corresponding CSV data-record counts. This confirms that the current database contains the expected number of records in these four tables. It does not, by itself, prove that every field is valid or that all foreign-key relationships are correct.

### 10.5 Database Evidence

Database verification was performed using a Java utility that connects through `DatabaseConnection` and executes SQL `COUNT(*)` queries against the four operational tables. The utility completed successfully and returned the record counts shown above.

The database schema also includes `algorithm_runs` and `audit_events`. However, record counts for these two tables were not included in the verification performed. Their presence in the schema should therefore not be treated as evidence that their contents have been fully tested.

The project's unit-test results provide evidence for the tested algorithms and data structures, while the database count check provides separate evidence for the number of records in the four operational tables. Further checks of constraints, relationships, and validation outcomes would provide additional evidence of database correctness.

---

## 11. Performance Evaluation

### 11.1 Purpose

Performance evaluation was conducted to observe how the execution time of Quick Sort and Binary Search changes with different input sizes. Four input sizes were tested: 100, 1,000, 5,000, and 10,000 elements.

### 11.2 Experimental Setup

A Java `PerformanceRunner` utility was implemented to measure execution time using `System.nanoTime()`. A random number generator with a fixed seed of `42` was used to generate reproducible input data.

Quick Sort was executed on a copy of the generated data. Binary Search was then performed on a sorted copy of the data. The search target was selected from the middle position of the sorted array.

Execution time was recorded in nanoseconds. The results were saved in `performance-results.csv`, which contains the algorithm name, input size, and execution time. The comparison chart is available at `docs/quicksort-binarysearch-performance.png`.

### 11.3 Experimental Results

The following results were obtained during one execution of the performance runner.

| Algorithm | Input Size | Execution Time (ns) |
|---|---:|---:|
| Quick Sort | 100 | 2,260,600 |
| Binary Search | 100 | 1,968,800 |
| Quick Sort | 1,000 | 1,958,400 |
| Binary Search | 1,000 | 6,400 |
| Quick Sort | 5,000 | 2,603,100 |
| Binary Search | 5,000 | 9,900 |
| Quick Sort | 10,000 | 14,293,500 |
| Binary Search | 10,000 | 7,500 |

### 11.4 Discussion

The results show that Quick Sort's measured execution time varied as the input size increased. Its highest recorded time occurred at 10,000 elements. Binary Search recorded much shorter times for the larger inputs, consistent with its logarithmic search complexity when the input is sorted.

The unusually high Binary Search time for 100 elements suggests that runtime overhead may have influenced the measurements. Java Virtual Machine (JVM) warm-up, system activity, and other runtime factors can affect execution time.

These results provide preliminary performance evidence rather than definitive benchmarks. More reliable comparisons would require warm-up runs, repeated trials, and average or median execution times.

### 11.5 Complexity Analysis

| Algorithm | Best Case | Average Case | Worst Case |
|---|---|---|---|
| Quick Sort | O(n log n) | O(n log n) | O(nÂ²) |
| Binary Search | O(1) | O(log n) | O(log n) |

Quick Sort generally performs efficiently when partitions are reasonably balanced, although poor pivot choices can lead to quadratic worst-case time. Binary Search repeatedly halves the search interval, giving logarithmic average and worst-case time on sorted data.

### 11.6 Limitations

The experiment has the following limitations:

- Only one timing measurement was recorded for each algorithm and input size.
- JVM warm-up and system activity were not controlled.
- The results were saved to `performance-results.csv`, but saving them to the `algorithm_runs` database table has not been confirmed.
- The measurements should not be used alone to prove theoretical complexity.
- The chart presents the recorded measurements and should be interpreted alongside the numerical results.

Future testing should include multiple trials, JVM warm-up, and summary statistics. Saving performance measurements to the `algorithm_runs` table would also provide persistent records for later analysis.

---

## 12. Conclusion and Recommendations

### 12.1 Conclusion

The Smart Campus Service Operations Optimizer demonstrates the application of data structures, algorithms, database management, and performance evaluation to a campus service operations problem.

The project implements data structures and algorithms including Dynamic Array, Hash Table, Heap, Binary Search, Quick Sort, Breadth-First Search (BFS), Dijkstra's algorithm, and a Greedy Scheduler. These components support data storage, searching, sorting, graph traversal, route finding, and priority-based service request selection.

The project uses SQLite to store campus locations, roads, service requests, and resources. The four operational tables were verified to contain 50 locations, 110 roads, 300 service requests, and 30 resources, giving a total of 490 records. The database record counts matched the corresponding CSV dataset counts.

Testing also provided evidence of correctness for the implemented algorithms and data structures. The latest recorded Maven test run completed with 82 tests, zero failures, zero errors, and zero skipped tests. Maven reported `BUILD SUCCESS`, with a total build time of 16.192 seconds.

Performance measurements were collected for Quick Sort and Binary Search at four input sizes. The results are stored in `performance-results.csv`, with a comparison chart at `docs/quicksort-binarysearch-performance.png`. These measurements remain preliminary because each measurement was recorded only once.

Overall, the project brings together algorithm implementation, structured data storage, automated testing, and initial performance evaluation in a single Java application.

### 12.2 Recommendations

The following improvements are recommended for future development:

1. **Improve performance testing:** Run multiple trials, include JVM warm-up, and calculate average or median execution times for more reliable comparisons.
2. **Strengthen database validation:** Test foreign-key relationships, database constraints, duplicate records, and invalid input values.
3. **Expand database evidence:** Verify the contents of the `algorithm_runs` and `audit_events` tables and confirm that performance results can be stored and retrieved correctly.
4. **Improve error handling:** Provide clear error messages and consistent handling of malformed CSV records and database failures.
5. **Expand testing:** Add integration tests covering database initialization, CSV imports, validation, and the interaction between application components.
6. **Improve performance reporting:** Present measured results in tables and graphs while clearly distinguishing experimental observations from theoretical complexity.
7. **Enhance scheduling evaluation:** Test the Greedy Scheduler with realistic service requests and different priority scenarios to assess its usefulness for campus operations.

### 12.3 Future Work

Future development could introduce a user interface for viewing campus locations, monitoring service requests, tracking resource availability, and displaying scheduling results. Additional features could include route optimization, service request history, and automated performance reports.

These improvements would help develop the project from its current implementation into a more complete campus service management system.
