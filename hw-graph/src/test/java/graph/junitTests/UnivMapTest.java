package graph.junitTests;
import graph.*;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

public class UnivMapTest {

    String N = null;
    String A = "A";
    String B = "B";
    String C = "C";
    String D = "D";
    int edge_minus1 = -1;
    int edge0 = 0;
    int edge1 = 1;
    int edge2 = 2;

    /** Tests add and remove nodes successful. */
    @Test
    public void testAddRemoveNode() {
        UnivMap map1 = new UnivMap();
        map1.AddNode(A);
        assertTrue(map1.contains(A));
        map1.AddNode(B);
        assertTrue(map1.contains(B));
        map1.RemoveNode(A);
        assertFalse(map1.contains(A));
        map1.RemoveNode(B);
        assertFalse(map1.contains(B));

        // add the same node twice, test remove only needed once
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
            fail("Expected IllegalArgumentException not occurred.");
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
        }
    }

    /** Tests add and remove edges operate correctly.
     */
    @Test
    public void testAddRemoveEdge() {
        UnivMap map1 = new UnivMap();

        // Case 1: add & remove one label
        map1.AddEdge(A, B, edge1);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        int i = map1.DistanceTo(A, B);
        assertEquals(edge1, map1.DistanceTo(A, B));
        assertEquals(edge_minus1, map1.DistanceTo(B, A));

        map1.RemoveEdge(A, B);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertEquals(edge_minus1, map1.DistanceTo(A, B));
        assertEquals(edge_minus1, map1.DistanceTo(B, A));

        // Case 2: Add 2 edges in the same direction
        map1 = new UnivMap();

        map1.AddEdge(A, B, edge1);
        map1.AddEdge(A, B, edge2);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertEquals(Math.min(edge1, edge2), map1.DistanceTo(A, B));
        assertEquals(edge_minus1, map1.DistanceTo(B, A));

        map1.RemoveEdge(A, B);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertEquals(edge_minus1, map1.DistanceTo(A, B));
        assertEquals(edge_minus1, map1.DistanceTo(B, A));

        // Case 3: Add 2 edges in the opposite direction
        map1 = new UnivMap();

        map1.AddEdge(A, B, edge1);
        map1.AddEdge(B, A, edge2);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertEquals(edge1, map1.DistanceTo(A, B));
        assertEquals(edge2, map1.DistanceTo(B, A));

        map1.RemoveEdge(A, B);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertEquals(edge_minus1, map1.DistanceTo(A, B));
        assertEquals(edge2, map1.DistanceTo(B, A));
    }

    /** Tests add and remove edges throw exceptions when needed.
     */
    @Test
    public void testAddRemoveEdgeThrowsIllegalArgumentException() {
        UnivMap map1 = new UnivMap();
        try {
            map1.AddEdge(A, B, edge_minus1);
            fail("Expected IllegalArgumentException not occurred.");
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
        } try {
            map1.AddEdge(A, B, edge0);
            fail("Expected IllegalArgumentException not occurred.");
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
        } try {
            map1.AddEdge(N, A, edge1);
            fail("Expected IllegalArgumentException not occurred.");
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
        } try {
            map1.AddEdge(A, N, edge1);
            fail("Expected IllegalArgumentException not occurred.");
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
        } try {
            map1.AddEdge(A, A, edge1);
            fail("Expected IllegalArgumentException not occurred.");
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
        } try {
            map1.RemoveEdge(A, A);
            fail("Expected IllegalArgumentException not occurred.");
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
        } try {
            map1.RemoveEdge(C, D);
            fail("Expected NoSuchElementException not occurred.");
        } catch (NoSuchElementException e) {
            e.getStackTrace();
        } finally {
            System.out.close();
        }
    }

    /** Tests ListNeighborsTo and ListNeighborsTo accuracy. */
    @Test
    public void testListNeighbors() {
        UnivMap map1 = new UnivMap();

        // Case 1: add & remove one label
        map1.AddEdge(A, B, edge1);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertTrue(map1.ListNeighborsFrom(A).contains(B));
        assertFalse(map1.ListNeighborsTo(A).contains(B));
        assertFalse(map1.ListNeighborsFrom(B).contains(A));
        assertTrue(map1.ListNeighborsTo(B).contains(A));

        map1.RemoveEdge(A, B);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertFalse(map1.ListNeighborsFrom(A).contains(B));
        assertFalse(map1.ListNeighborsTo(A).contains(B));
        assertFalse(map1.ListNeighborsFrom(B).contains(A));
        assertFalse(map1.ListNeighborsTo(B).contains(A));

        // Case 2: Add 2 edges in the same direction
        map1.AddEdge(A, B, edge1);
        map1.AddEdge(A, B, edge2);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertTrue(map1.ListNeighborsFrom(A).contains(B));
        assertFalse(map1.ListNeighborsTo(A).contains(B));
        assertFalse(map1.ListNeighborsFrom(B).contains(A));
        assertTrue(map1.ListNeighborsTo(B).contains(A));

        map1.RemoveEdge(A, B);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertTrue(map1.ListNeighborsFrom(A).contains(B));
        assertFalse(map1.ListNeighborsTo(A).contains(B));
        assertFalse(map1.ListNeighborsFrom(B).contains(A));
        assertTrue(map1.ListNeighborsTo(B).contains(A));


        // Case 3: Add 2 edges in the opposite direction
        map1 = new UnivMap();

        map1.AddEdge(A, B, edge1);
        map1.AddEdge(B, A, edge2);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertTrue(map1.ListNeighborsFrom(A).contains(B));
        assertTrue(map1.ListNeighborsTo(A).contains(B));
        assertTrue(map1.ListNeighborsFrom(B).contains(A));
        assertTrue(map1.ListNeighborsTo(B).contains(A));

        map1.RemoveEdge(A, B);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertFalse(map1.ListNeighborsFrom(A).contains(B));
        assertTrue(map1.ListNeighborsTo(A).contains(B));
        assertTrue(map1.ListNeighborsFrom(B).contains(A));
        assertFalse(map1.ListNeighborsTo(B).contains(A));
    }

    /**
     * Tests that ListChildren and ListParents throws IllegalArgumentException when
     * passing in null value or non-existent nodes in map
     */
    @Test
    public void testRemoveThrowsIllegalArgumentException() {
        UnivMap map1 = new UnivMap();
        try {
            map1.ListNeighborsTo(N);
            fail("Expected IllegalArgumentException not occurred.");
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
        } try {
            map1.ListNeighborsTo(A);
            fail("Expected NoSuchElementException not occurred.");
        } catch (NoSuchElementException e) {
            e.getStackTrace();
        } try {
            map1.ListNeighborsTo(N);
            fail("Expected IllegalArgumentException not occurred.");
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
        } try {
            map1.ListNeighborsTo(A);
            fail("Expected NoSuchElementException not occurred.");
        } catch (NoSuchElementException e) {
            e.getStackTrace();
        }
    }

}
