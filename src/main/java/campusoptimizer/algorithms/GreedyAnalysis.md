# Greedy Scheduling Analysis

## Problem

The Smart Campus Service Operations Optimizer receives different service
requests that require attention. These requests may include maintenance,
security, network, or other campus service issues.

The objective is to determine which request should be handled next.

## Greedy Strategy

The greedy strategy selects the highest-priority service request available
at each step.

The algorithm does not reconsider previously selected requests. Once the
highest-priority request is selected, it is added to the final schedule.

The selection process continues until all requests have been scheduled.

## Example

| Request | Priority |
|---|---:|
| Broken Light | 2 |
| Wi-Fi Problem | 4 |
| Water Leakage | 9 |
| Security Emergency | 10 |

The greedy algorithm selects requests in this order:

1. Security Emergency — Priority 10
2. Water Leakage — Priority 9
3. Wi-Fi Problem — Priority 4
4. Broken Light — Priority 2

## Trace Table

| Step | Available Highest Priority | Selected Request | Remaining Requests |
|---|---|---|---:|
| 1 | Security Emergency (10) | Security Emergency | 3 |
| 2 | Water Leakage (9) | Water Leakage | 2 |
| 3 | Wi-Fi Problem (4) | Wi-Fi Problem | 1 |
| 4 | Broken Light (2) | Broken Light | 0 |

## Why the Greedy Approach Is Appropriate

The problem requires selecting the most important available campus service
request at each step.

A greedy approach is appropriate because the locally optimal decision is to
select the request with the highest priority. Repeating this decision produces
a priority-based service schedule.

A Heap supports this strategy efficiently because the highest-priority
element is always kept at the root.

## Time Complexity

Let n be the number of service requests.

- Inserting one request into the Heap: O(log n)
- Extracting the highest-priority request: O(log n)
- Creating a complete schedule for n requests: O(n log n)

## Space Complexity

The Heap stores n requests.

Therefore, the space complexity is:

O(n)

## Conclusion

The Heap and Greedy Scheduler work together to prioritize campus service
requests efficiently. The Heap maintains the priority order, while the Greedy
algorithm repeatedly selects the highest-priority request until all requests
have been scheduled.
