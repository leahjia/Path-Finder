package graph.junitTests;
import graph.*;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

public class UnivMapTest {

//    private final UnivMap map1 = new UnivMap();
    String X = null;
    String A = "A";
    String B = "B";
    String e1 = "e1";
    String e2 = "e2";

    /** Tests add and remove node successful. */
    @Test
    public void testAddNode() {
        UnivMap map1 = new UnivMap();
        map1.AddNode(A);
        map1.AddNode(B);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        map1.RemoveNode(A);
        map1.RemoveNode(B);
        assertFalse(map1.contains(A));
        assertFalse(map1.contains(B));
    }

    /** Tests add and remove edge successful. */
    @Test
    public void testAddRemoveEdge() {
        UnivMap map1 = new UnivMap();
        map1.AddEdge(A, B, e1);
        map1.AddEdge(B, A, e2);
        assertTrue(map1.ListParents(A).contains(B));
        assertTrue(map1.ListParents(B).contains(A));
        assertTrue(map1.ListChildren(A).contains(B));
        assertTrue(map1.ListChildren(B).contains(A));
        map1.RemoveEdge(e1);
        map1.RemoveEdge(e2);
        assertFalse(map1.ListParents(A).contains(B));
        assertFalse(map1.ListParents(B).contains(A));
        assertFalse(map1.ListParents(A).contains(B));
        assertFalse(map1.ListParents(B).contains(A));
    }

    /** Tests ListChildren and ListParents accuracy. */
    @Test
    public void testListChildrenParents() {
        UnivMap map1 = new UnivMap();
        map1.AddEdge(A, B, e1);
        map1.AddEdge(B, A, e2);
        assertTrue(map1.ListChildren(A).contains(B));
        assertTrue(map1.ListChildren(B).contains(A));
        assertTrue(map1.ListParents(A).contains(B));
        assertTrue(map1.ListParents(B).contains(A));
    }

    /**
     * Tests that AddNode throws IllegalArgumentException when adding duplicate node
     */
    @Test
    public void testAddNodeThrowsIllegalArgumentException() {
        UnivMap map1 = new UnivMap();
        try {
            map1.AddNode(X);
        } catch (IllegalArgumentException e) {
            fail("Threw IllegalArgumentException for adding null Node: " + e);
        }
    }

    /**
     * Tests that AddEdge throws IllegalArgumentException when adding null values
     */
    @Test
    public void testAddEdgeThrowsIllegalArgumentException() {
        UnivMap map1 = new UnivMap();

        try {
            map1.AddEdge(A, B, X);
        } catch (IllegalArgumentException e) {
            fail("Threw IllegalArgumentException for adding null label: "
                    + e);
        }

        try {
            map1.AddEdge(A, X, e1);
        } catch (IllegalArgumentException e) {
            fail("Threw IllegalArgumentException for adding edge to null destination: "
                    + e);
        }

        try {
            map1.AddEdge(X, B, e1);
        } catch (IllegalArgumentException e) {
            fail("Threw IllegalArgumentException for adding edge from null source: "
                    + e);
        }
    }


    /**
     * Tests that RemoveNode, RemoveEdge, ListChildren, and ListParents throws
     * IllegalArgumentException when asking for elements not in map
     */
    @Test
    public void testRemoveThrowsIllegalArgumentException() {
        UnivMap map1 = new UnivMap();
        try {
            map1.RemoveEdge(e1);
        } catch (IllegalArgumentException e) {
            fail("Threw IllegalArgumentException for non-existent edge: "
                    + e);
        }

        try {
            map1.RemoveNode(A);
        } catch (IllegalArgumentException e) {
            fail("Threw IllegalArgumentException for non-existent node: "
                    + e);
        }

        try {
            map1.ListChildren(A);
        } catch (IllegalArgumentException e) {
            fail("Threw IllegalArgumentException for non-existent node: "
                    + e);
        }
        try {
            map1.ListParents(A);
        } catch (IllegalArgumentException e) {
            fail("Threw IllegalArgumentException for non-existent node: "
                    + e);
        }
    }

}
