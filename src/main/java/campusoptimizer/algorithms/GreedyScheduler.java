package campusoptimizer.algorithms;

import campusoptimizer.datastructures.DynamicArray;
import campusoptimizer.datastructures.Heap;

import java.util.Comparator;

/**
 * GreedyScheduler selects campus service requests based on priority.
 *
 * At every step, the algorithm chooses the highest-priority
 * request available.
 */
public class GreedyScheduler<T> {

    private final Heap<T> heap;

    /**
     * Creates a greedy scheduler using the given priority comparator.
     */
    public GreedyScheduler(Comparator<? super T> comparator) {
        heap = new Heap<>(comparator);
    }

    /**
     * Adds a request to the scheduler.
     */
    public void addRequest(T request) {
        heap.insert(request);
    }

    /**
     * Selects the next highest-priority request.
     */
    public T selectNext() {
        return heap.extract();
    }

    /**
     * Produces the complete schedule.
     *
     * The greedy strategy repeatedly selects the
     * highest-priority available request.
     */
    public DynamicArray<T> createSchedule() {

        DynamicArray<T> schedule = new DynamicArray<>();

        while (!heap.isEmpty()) {
            schedule.add(heap.extract());
        }

        return schedule;
    }

    /**
     * Returns the number of pending requests.
     */
    public int pendingRequests() {
        return heap.size();
    }

    /**
     * Checks whether there are no pending requests.
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }
}
