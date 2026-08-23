package campusoptimizer.datastructures;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class HeapTest {

    @Test
    void insertAndPeekReturnsHighestPriority() {
        Heap<Integer> heap = new Heap<>(Comparator.naturalOrder());

        heap.insert(5);
        heap.insert(10);
        heap.insert(3);
        heap.insert(8);

        assertEquals(10, heap.peek());
    }

    @Test
    void extractReturnsElementsInPriorityOrder() {
        Heap<Integer> heap = new Heap<>(Comparator.naturalOrder());

        heap.insert(5);
        heap.insert(10);
        heap.insert(3);
        heap.insert(8);

        assertEquals(10, heap.extract());
        assertEquals(8, heap.extract());
        assertEquals(5, heap.extract());
        assertEquals(3, heap.extract());
    }

    @Test
    void heapSizeUpdatesCorrectly() {
        Heap<Integer> heap = new Heap<>(Comparator.naturalOrder());

        heap.insert(10);
        heap.insert(5);

        assertEquals(2, heap.size());

        heap.extract();

        assertEquals(1, heap.size());
    }

    @Test
    void newHeapIsEmpty() {
        Heap<Integer> heap = new Heap<>(Comparator.naturalOrder());

        assertTrue(heap.isEmpty());
        assertEquals(0, heap.size());
    }

    @Test
    void heapIsEmptyAfterAllElementsAreExtracted() {
        Heap<Integer> heap = new Heap<>(Comparator.naturalOrder());

        heap.insert(10);
        heap.insert(5);

        heap.extract();
        heap.extract();

        assertTrue(heap.isEmpty());
    }

    @Test
    void peekOnEmptyHeapThrowsException() {
        Heap<Integer> heap = new Heap<>(Comparator.naturalOrder());

        assertThrows(NoSuchElementException.class, heap::peek);
    }

    @Test
    void extractOnEmptyHeapThrowsException() {
        Heap<Integer> heap = new Heap<>(Comparator.naturalOrder());

        assertThrows(NoSuchElementException.class, heap::extract);
    }

    @Test
    void heapHandlesDuplicateValues() {
        Heap<Integer> heap = new Heap<>(Comparator.naturalOrder());

        heap.insert(10);
        heap.insert(10);
        heap.insert(5);

        assertEquals(10, heap.extract());
        assertEquals(10, heap.extract());
        assertEquals(5, heap.extract());
    }
}
