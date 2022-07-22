package graph.junitTests;
import graph.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class UnivMapTest {

    private final Map<String, UnivMap> graph = new HashMap<String, UnivMap>();

    /** Tests doing something. */
    @Test
    public void testToString() {
        assertEquals(graph.toString(), "{}");
    }

}
