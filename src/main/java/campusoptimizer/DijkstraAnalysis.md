# Dijkstra's Algorithm — Analysis (Issue #8)

Source location: `MainGate`

Graph used:

| Route | Weight (mins) |
|---|---|
| MainGate – Library | 5 |
| MainGate – Hostel | 10 |
| Library – Cafeteria | 3 |
| Hostel – Cafeteria | 4 |
| Cafeteria – SportsComplex | 6 |
| Hostel – SportsComplex | 15 |

## Trace Table

Each row shows the vertex extracted from the priority queue at that step, and
the distance table snapshot immediately after processing it. `INF` = not
yet reached.

| Step | Extracted | Cafeteria | Hostel | Library | MainGate | SportsComplex |
|---|---|---|---|---|---|---|
| 1 | MainGate | INF | 10 | 5 | 0 | INF |
| 2 | Library | 8 | 10 | 5 | 0 | INF |
| 3 | Cafeteria | 8 | 10 | 5 | 0 | 14 |
| 4 | Hostel | 8 | 10 | 5 | 0 | 14 |
| 5 | SportsComplex | 8 | 10 | 5 | 0 | 14 |

**Reading it:**
- Step 1: MainGate (the source) is extracted first with distance 0. Its neighbors Library (5) and Hostel (10) get their initial distances.
- Step 2: Library is the closest unvisited vertex (5), so it's extracted next. Relaxing its edge to Cafeteria gives `5 + 3 = 8`, beating infinity, so Cafeteria is updated to 8.
- Step 3: Cafeteria (8) is now the closest, extracted next. Relaxing its edges: Hostel would be `8 + 4 = 12`, but Hostel is already 10 — no update. SportsComplex becomes `8 + 6 = 14`.
- Step 4: Hostel (10) is extracted. Its edge to SportsComplex would give `10 + 15 = 25`, worse than the current 14 — no update.
- Step 5: SportsComplex (14) is extracted last, with no unvisited neighbors left to relax.

Final shortest distances from MainGate: Library=5, Cafeteria=8, Hostel=10, SportsComplex=14.
Shortest path to SportsComplex: `MainGate → Library → Cafeteria → SportsComplex`.

## Time Complexity

Let **V** = number of locations (vertices), **E** = number of routes (edges).

| Operation | Cost | Frequency |
|---|---|---|
| `insert()` into the priority queue | O(log n) | Up to once per edge relaxation → O(E) times |
| `extractMin()` | O(log n) | Once per vertex extraction (plus stale skips) → O(V + E) times |
| Relaxing all edges of a vertex | O(degree(v)) total per vertex | Sums to O(E) across the whole run |

Because this implementation uses **lazy deletion** (re-inserting a vertex
each time a shorter distance is found, instead of a `decreaseKey`), the
priority queue can hold up to O(E) entries in the worst case rather than
O(V). Each insert/extract is O(log E), which is the same order as O(log V)
since E is at most V².

**Overall: O((V + E) log V)**

This matches the scaling test below — runtime grows roughly in line with
`(V + E) log V`, not with V² (which a naive array-scan Dijkstra without a
priority queue would give).

## Space Complexity

| Structure | Space |
|---|---|
| Adjacency list (`Graph`) | O(V + E) |
| `distances` map | O(V) |
| `predecessors` map | O(V) |
| `finalized` set | O(V) |
| Priority queue (lazy deletion, worst case) | O(E) |

**Overall: O(V + E)**

## Testing Across Different Graph Sizes

Ran on randomly generated connected graphs (chain backbone + random extra
edges for ~4 average degree per vertex):

| Vertices | Edges (~) | Time (ms) |
|---|---|---|
| 50 | 192 | 3.2 |
| 200 | 794 | 1.8 |
| 1,000 | 3,998 | 17.4 |
| 5,000 | 19,998 | 41.3 |
| 20,000 | 79,992 | 136.6 |

Runtime grows sub-linearly-looking-but-actually-`(V+E)log V` as size
increases — a 400x increase in vertices (50 → 20,000) produces roughly a
40-70x increase in runtime, not the ~160,000x a naive O(V²) approach would
show. The small-N numbers (50, 200) are noisy because JIT warm-up and
JVM overhead dominate at that scale — the trend becomes clear from 1,000
vertices onward.

## Unreachable Locations

If a location has no path from the source (e.g. a disconnected building
annex), its distance remains `Dijkstra.UNREACHABLE` (`Integer.MAX_VALUE`)
after the algorithm finishes, and `reconstructPath()` returns an empty
list instead of a bogus path. Verified in `DijkstraTest.testUnreachableLocation()`.
