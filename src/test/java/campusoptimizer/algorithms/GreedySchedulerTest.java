package campusoptimizer.algorithms;

import campusoptimizer.datastructures.DynamicArray;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class GreedySchedulerTest {

    @Test
    void schedulerSelectsHighestPriorityFirst() {
        GreedyScheduler<Integer> scheduler =
                new GreedyScheduler<>(Comparator.naturalOrder());

        scheduler.addRequest(5);
        scheduler.addRequest(10);
        scheduler.addRequest(3);
        scheduler.addRequest(8);

        assertEquals(10, scheduler.selectNext());
    }

    @Test
    void schedulerCreatesCorrectPriorityOrder() {
        GreedyScheduler<Integer> scheduler =
                new GreedyScheduler<>(Comparator.naturalOrder());

        scheduler.addRequest(5);
        scheduler.addRequest(10);
        scheduler.addRequest(3);
        scheduler.addRequest(8);

        DynamicArray<Integer> schedule = scheduler.createSchedule();

        assertEquals(4, schedule.size());
        assertEquals(10, schedule.get(0));
        assertEquals(8, schedule.get(1));
        assertEquals(5, schedule.get(2));
        assertEquals(3, schedule.get(3));
    }

    @Test
    void schedulerStartsEmpty() {
        GreedyScheduler<Integer> scheduler =
                new GreedyScheduler<>(Comparator.naturalOrder());

        assertTrue(scheduler.isEmpty());
        assertEquals(0, scheduler.pendingRequests());
    }

    @Test
    void pendingRequestsUpdatesCorrectly() {
        GreedyScheduler<Integer> scheduler =
                new GreedyScheduler<>(Comparator.naturalOrder());

        scheduler.addRequest(10);
        scheduler.addRequest(5);
        scheduler.addRequest(8);

        assertEquals(3, scheduler.pendingRequests());

        scheduler.selectNext();

        assertEquals(2, scheduler.pendingRequests());
    }

    @Test
    void createScheduleEmptiesScheduler() {
        GreedyScheduler<Integer> scheduler =
                new GreedyScheduler<>(Comparator.naturalOrder());

        scheduler.addRequest(10);
        scheduler.addRequest(5);
        scheduler.addRequest(8);

        scheduler.createSchedule();

        assertTrue(scheduler.isEmpty());
    }

    @Test
    void selectNextOnEmptySchedulerThrowsException() {
        GreedyScheduler<Integer> scheduler =
                new GreedyScheduler<>(Comparator.naturalOrder());

        assertThrows(NoSuchElementException.class,
                scheduler::selectNext);
    }

    @Test
    void schedulerHandlesDuplicatePriorities() {
        GreedyScheduler<Integer> scheduler =
                new GreedyScheduler<>(Comparator.naturalOrder());

        scheduler.addRequest(10);
        scheduler.addRequest(10);
        scheduler.addRequest(5);

        DynamicArray<Integer> schedule = scheduler.createSchedule();

        assertEquals(10, schedule.get(0));
        assertEquals(10, schedule.get(1));
        assertEquals(5, schedule.get(2));
    }
}
