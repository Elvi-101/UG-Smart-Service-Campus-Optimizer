# OPT-03: Deque & Dynamic Programming Analysis

## 1. Deque

The Deque (Double-Ended Queue) was implemented from scratch using
a doubly linked list.

### Operations

- insertFront() - O(1)
- insertRear() - O(1)
- removeFront() - O(1)
- removeRear() - O(1)
- peekFront() - O(1)
- peekRear() - O(1)
- size() - O(1)

### Space Complexity

O(n), where n is the number of elements stored in the Deque.

## 2. Dynamic Programming

The optimization problem was modeled as a 0/1 Knapsack problem.

Each campus service has:

- A capacity/resource requirement
- An expected benefit

The objective is to select services that maximize total benefit
without exceeding the available capacity.

### Recurrence

If the current service fits:

DP[i][c] = max(
    DP[i-1][c],
    value[i-1] + DP[i-1][c-weight[i-1]]
)

Otherwise:

DP[i][c] = DP[i-1][c]

### Base Case

DP[0][c] = 0

No services means zero benefit.

### Optimal Substructure

The optimal solution for a larger problem can be constructed
from optimal solutions to smaller subproblems.

### Overlapping Subproblems

The same capacity states are encountered repeatedly.
The DP table stores previously calculated results so they do not
need to be calculated again.

### Trace Result

For capacity 10 and the test services:

Weights: 2, 3, 4, 5
Benefits: 30, 40, 50, 70

The optimal total benefit is:

140

### Complexity

Time Complexity: O(n × C)

Space Complexity: O(n × C)

Where:

n = number of services
C = available capacity

## 3. Testing

Deque tests verified:

- Front insertion
- Rear insertion
- Front deletion
- Rear deletion
- Front/rear access
- Empty Deque handling
  
All Deque tests passed.

Dynamic Programming tests verified:

- Normal optimization
- Zero capacity
- No services
- Service exceeding capacity
- Single service

All Dynamic Programming tests passed.