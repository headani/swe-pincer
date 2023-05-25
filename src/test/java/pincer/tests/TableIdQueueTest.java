package pincer.tests;

import hu.unideb.inf.swe.pincer.util.TableIdQueue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TableIdQueueTest {

    static TableIdQueue queue;

    @BeforeAll
    static void setup() {
        queue = TableIdQueue.getInstance();
    }

    @Test
    void testIdOrder() {

        queue.add(1);
        queue.add(2);
        queue.add(3);

        Integer result1 = queue.remove();
        Integer result2 = queue.remove();
        Integer result3 = queue.remove();

        assertEquals(1, result1);
        assertEquals(2, result2);
        assertEquals(3, result3);
    }
}
