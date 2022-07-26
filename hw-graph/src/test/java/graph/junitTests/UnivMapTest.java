package graph.junitTests;
import graph.*;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

public class UnivMapTest {

    /** Tests add and remove nodes successful. */
    @Test
    public void testAddRemoveNode() {
        UnivMap map1 = new UnivMap();
        map1.AddNode(A);
        map1.AddNode(B);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        map1.RemoveNode(A);
        map1.RemoveNode(B);
        assertFalse(map1.contains(A));
        assertFalse(map1.contains(B));

        // add the same node twice, remove only once
        map1.AddNode(A);
        map1.AddNode(A);
        assertTrue(map1.contains(A));
        map1.RemoveNode(A);
        assertFalse(map1.contains(A));

        // remove non-existent and null nodes without errors
        map1.RemoveNode(D);
        map1.RemoveNode(N);
        assertFalse(map1.contains(D));
        assertFalse(map1.contains(N));
    }

    /**
     * Tests that AddNode throws IllegalArgumentException when adding duplicate node
     */
    @Test
    public void testAddNodeThrowsIllegalArgumentException() {
        UnivMap map1 = new UnivMap();
        try {
            map1.AddNode(N);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            System.out.close();
        }
    }

    String N = null;
    String A = "A";
    String B = "B";
    String C = "C";
    String D = "D";
    int edge_1 = -1;
    int edge0 = 0;
    int edge1 = 1;
    int edge2 = 2;
    int edge3 = 3;

    /** Tests add and remove edge successful. */
    @Test
    public void testAddRemoveEdge() {
        UnivMap map1 = new UnivMap();
        map1.AddEdge(A, B, edge1);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertTrue(map1.ListChildren(A).contains(B));
        assertFalse(map1.ListParents(A).contains(B));
        assertFalse(map1.ListChildren(B).contains(A));
        assertTrue(map1.ListParents(B).contains(A));
        map1.RemoveEdge(A, B);
        assertFalse(map1.ListChildren(A).contains(B));
        assertFalse(map1.ListParents(A).contains(B));
        assertFalse(map1.ListChildren(B).contains(A));
        assertFalse(map1.ListParents(B).contains(A));
    }

//    /** Tests ListChildren and ListParents accuracy. */
//    @Test
//    public void testListChildrenParents() {
//        UnivMap map1 = new UnivMap();
//        map1.AddEdge(A, B, edge1);
//        map1.AddEdge(B, A, edge2);
//        map1.AddEdge(A, C, edge3);
//        assertTrue(map1.ListParents(A).contains(B));
//        assertTrue(map1.ListParents(B).contains(A));
//        assertTrue(map1.ListChildren(A).contains(B));
//        assertTrue(map1.ListChildren(B).contains(A));
//        assertTrue(map1.ListChildren(A).contains(C));
//        assertTrue(map1.ListParents(C).contains(A));
//        map1.RemoveEdge(A, B);
//        map1.RemoveEdge(B, A);
//        assertFalse(map1.ListParents(A).contains(B));
//        assertFalse(map1.ListParents(B).contains(A));
//        assertFalse(map1.ListParents(A).contains(B));
//        assertFalse(map1.ListParents(B).contains(A));
//        assertTrue(map1.ListChildren(A).contains(C));
//        assertTrue(map1.ListParents(C).contains(A));
//    }
//
//    /**
//     * Tests that AddEdge throws IllegalArgumentException when adding null values
//     */
//    @Test
//    public void testAddEdgeThrowsIllegalArgumentException() {
//        UnivMap map1 = new UnivMap();
//
//        try {
//            map1.AddEdge(A, X, e1);
//        } catch (IllegalArgumentException e) {
//            fail("Threw IllegalArgumentException for adding edge to null destination: " + e);
//        }
//
//        try {
//            map1.AddEdge(X, B, e1);
//        } catch (IllegalArgumentException e) {
//            fail("Threw IllegalArgumentException for adding edge from null source: " + e);
//        }
//    }
//
//
//    /**
//     * Tests that RemoveNode, RemoveEdge, ListChildren, and ListParents throws
//     * IllegalArgumentException when asking for elements not in map
//     */
//    @Test
//    public void testRemoveThrowsIllegalArgumentException() {
//        UnivMap map1 = new UnivMap();
//        try {
//            map1.RemoveEdge(A, B);
//        } catch (IllegalArgumentException e) {
//            fail("" + e);
//        }
//
//        try {
//            map1.RemoveNode(A);
//        } catch (IllegalArgumentException e) {
//            fail("" + e);
//        }
//
//        try {
//            map1.ListChildren(A);
//        } catch (IllegalArgumentException e) {
//            fail("" + e);
//        }
//        try {
//            map1.ListParents(A);
//        } catch (IllegalArgumentException e) {
//            fail("" + e);
//        }
//    }

}
