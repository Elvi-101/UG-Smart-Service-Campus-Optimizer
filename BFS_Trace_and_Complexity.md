# BFS Trace Table & Complexity Analysis
## Module: BFS.java (operating on the shared campusoptimizer.Graph) — Smart Campus Service Operations Optimizer
## Author: Akpabli Desmond (22325879)

## 0. Note on the Graph class

The repo already has a `Graph` class (built for Dijkstra) representing
campus locations as vertices and routes as weighted edges
(`Graph.Edge(destination, weight)`), stored as an adjacency list. BFS
does not need weights, so `BFS.java` simply reads `edge.destination` from
each `Graph.Edge` and ignores the weight. This means BFS and Dijkstra
share one Graph, which is what "integration" in Week 3 expects.

## 1. Example graph

```
Library --- Cafeteria --- Gym
   |             |
Hostel        Clinic
```

Built with:
```java
Graph g = new Graph();
g.addRoute("Library", "Cafeteria", 3);
g.addRoute("Library", "Hostel", 5);
g.addRoute("Cafeteria", "Gym", 6);
g.addRoute("Cafeteria", "Clinic", 2);
```
(weights shown are walking time in minutes — irrelevant to BFS, relevant to Dijkstra)

## 2. Trace table — BFS.traverse(g, "Library")

| Step | Action | Current | Queue (front → back) | Visited set | Visit order so far |
|------|--------|---------|------------------------|-------------|---------------------|
| 0 | Initialize | — | [Library] | {Library} | [] |
| 1 | Dequeue Library, visit, enqueue unvisited neighbors (Cafeteria, Hostel) | Library | [Cafeteria, Hostel] | {Library, Cafeteria, Hostel} | [Library] |
| 2 | Dequeue Cafeteria, visit, enqueue unvisited neighbors (Gym, Clinic); Library already visited, skipped | Cafeteria | [Hostel, Gym, Clinic] | {+Cafeteria, Gym, Clinic} | [Library, Cafeteria] |
| 3 | Dequeue Hostel, visit; neighbor Library already visited, skipped | Hostel | [Gym, Clinic] | {same} | [Library, Cafeteria, Hostel] |
| 4 | Dequeue Gym, visit; neighbor Cafeteria already visited, skipped | Gym | [Clinic] | {same} | [Library, Cafeteria, Hostel, Gym] |
| 5 | Dequeue Clinic, visit; neighbor Cafeteria already visited, skipped | Clinic | [] | {same} | [Library, Cafeteria, Hostel, Gym, Clinic] |
| 6 | Queue empty, terminate | — | [] | {all 5} | [Library, Cafeteria, Hostel, Gym, Clinic] |

**Final BFS order:** Library, Cafeteria, Hostel, Gym, Clinic

## 3. Trace table — disconnected graph

Add an isolated location `StoreRoom` (`g.addLocation("StoreRoom")`, no route).

- `BFS.traverse(g, "Library")` alone visits only {Library, Cafeteria, Hostel, Gym, Clinic}. `StoreRoom` is unreachable from Library, so it's correctly excluded — no exception, no infinite loop.
- `BFS.traverseAll(g)` loops over `g.getAllLocations()` and runs `traverse()` from any not-yet-visited location:

| Component pass | Start chosen | Component visited |
|---|---|---|
| 1 | Library (first unvisited) | [Library, Cafeteria, Hostel, Gym, Clinic] |
| 2 | StoreRoom (next unvisited) | [StoreRoom] |

## 4. Weighted-edge vs BFS hop count

Because `Graph` stores weights but BFS ignores them, BFS's shortest path
can differ from Dijkstra's: BFS minimizes *number of hops*, Dijkstra
minimizes *total weight*. `BFSTest.shortestPathByHops_findsFewestHopsNotLowestWeight`
demonstrates this directly — a 1-hop route with weight 100 is preferred
by BFS over a 2-hop route with total weight 2, since BFS only counts
edges, not their weight. Worth stating clearly in the oral defense so it
doesn't look like a bug.

## 5. Time complexity

**O(V + E)**

- Every location is enqueued/dequeued exactly once → O(V).
- Every edge is examined once per direction while scanning a location's neighbor list → O(E).
- Adjacency list keeps this efficient even though Graph.Edge carries an
  extra weight field — reading `edge.destination` is O(1) per edge, so
  the weight field doesn't change the asymptotic complexity.

Contrast: an adjacency-matrix representation would force O(V) work per
vertex just to find its neighbors, giving O(V²) overall — worse for a
sparse campus graph.

## 6. Space complexity

**O(V)**

- `visited` set: up to V entries.
- `queue`: up to V entries worst case (e.g. a hub location connected to many others at once).
- `visitOrder` list: V entries.
- (Graph's own adjacency-list storage is O(V + E), but that's the graph's cost, not BFS's auxiliary space.)

## 7. Edge cases tested (see BFSTest.java)

- Single location, no routes
- Linear chain (level-order correctness)
- Graph containing a cycle (no infinite loop, no duplicate visits)
- Heavily-weighted edge still treated as 1 hop (BFS ignores weight)
- Start location not present in graph → throws `NoSuchElementException`
- Empty graph
- Disconnected graph — `traverse()` only reaches its own component
- `traverseAll()` — confirms every component is eventually covered
- Shortest path by hops: fewest-hops vs lowest-weight distinction, same start/target, unreachable target, missing location

## 8. Oral defense notes

1. **Problem solved**: gives fast reachability and "fewest stops" routing over the campus graph, complementing Dijkstra's "lowest cost" routing on the same underlying Graph.
2. **Why BFS here specifically**: when a service dispatch just needs "how many stops away" rather than a weighted cost, BFS is simpler and cheaper than running Dijkstra.
3. **Why this fits the existing Graph**: reuses the team's Graph/Edge structure rather than duplicating a second graph representation — one shared structure for both algorithms, straightforward for Week 3 integration.
4. **Complexity**: O(V+E) time, O(V) auxiliary space.
5. **Walkthrough**: section 2's 5-location example.
6. **Edge cases**: section 7.
7. **Integration**: BFS.java depends only on Graph's public methods (`hasLocation`, `getNeighbors`, `getAllLocations`) — no changes needed to Graph.java itself.
8. **Larger inputs**: still O(V+E); worth an experiment with increasing V/E for the Week 4 performance graphs, run alongside Dijkstra's for comparison.
9. **Live modification**: adding a location/route and rerunning BFS is O(1) to update the graph, O(V+E) to re-traverse — can demo live with an examiner-chosen start.

## 9. Heads-up for the team: default-package issue

`Graph.java`, `Dijkstra.java`, and a few other files (`PriorityQueue.java`,
`DijkstraTraced.java`, plus their matching `*Test.java` main-method files)
currently have **no `package campusoptimizer;` declaration**, even though
they sit in the `campusoptimizer` folder — while `Main.java` does declare
`package campusoptimizer;`. Classes in a named package cannot import
classes from the default (unnamed) package, so once someone wires
Graph/Dijkstra/BFS into `Main`'s demo menu, it won't compile.

`BFS.java` was written to match Graph's *current* default-package setup
so it compiles today. The real fix — adding `package campusoptimizer;` as
the first line of Graph.java, Dijkstra.java, PriorityQueue.java, and
BFS.java together in one coordinated commit — should happen before Week 3
integration, ideally agreed with whoever owns Graph.java/Dijkstra.java
first since it touches their files.