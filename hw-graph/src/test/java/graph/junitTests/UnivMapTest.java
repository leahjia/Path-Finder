package graph.junitTests;
import graph.*;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

public class UnivMapTest {
    @Rule public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    String N = null;
    String A = "A";
    String B = "B";
    String C = "C";
    String D = "D";
    String edge1 = "e1";
    String edge2 = "e2";

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
        map1 = new UnivMap();
        map1.RemoveNode(D);
        map1.RemoveNode(N);
        assertFalse(map1.contains(D));
        assertFalse(map1.contains(N));
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
        assertTrue(map1.getLabels(A, B).contains(edge1));
        assertFalse(map1.getLabels(B, A).contains(edge1));

        map1.RemoveEdge(A, B, edge1);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertFalse(map1.getLabels(A, B).contains(edge1));
        assertFalse(map1.getLabels(B, A).contains(edge1));

        // Case 2: Add 2 edges in the same direction
        map1.AddEdge(A, B, edge1);
        map1.AddEdge(A, B, edge2);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertTrue(map1.getLabels(A, B).contains(edge1));
        assertFalse(map1.getLabels(B, A).contains(edge1));
        assertTrue(map1.getLabels(A, B).contains(edge2));
        assertFalse(map1.getLabels(B, A).contains(edge2));

        // remove one of the two edges
        map1.RemoveEdge(A, B, edge1);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertFalse(map1.getLabels(A, B).contains(edge1));
        assertFalse(map1.getLabels(B, A).contains(edge1));
        assertTrue(map1.getLabels(A, B).contains(edge2));
        assertFalse(map1.getLabels(B, A).contains(edge2));

        // Case 3: Add 2 edges in the opposite direction
        map1 = new UnivMap();

        map1.AddEdge(A, B, edge1);
        map1.AddEdge(B, A, edge2);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertTrue(map1.getLabels(A, B).contains(edge1));
        assertFalse(map1.getLabels(B, A).contains(edge1));
        assertFalse(map1.getLabels(A, B).contains(edge2));
        assertTrue(map1.getLabels(B, A).contains(edge2));

        // remove one of the two edges
        map1.RemoveEdge(A, B, edge1);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertFalse(map1.getLabels(A, B).contains(edge1));
        assertFalse(map1.getLabels(B, A).contains(edge1));
        assertFalse(map1.getLabels(A, B).contains(edge2));
        assertTrue(map1.getLabels(B, A).contains(edge2));
    }

    /** Tests add and remove edges throw exceptions when needed.
     */
    @Test
    public void testNodeAndEdgeThrowsIllegalArgumentException() {
        UnivMap map1 = new UnivMap();
        try {
            // add null node
            map1.AddNode(N);
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
        } try {
            // add null edge
            map1.AddEdge(A, B, N);
            fail("Expected IllegalArgumentException not occurred.");
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
        } try {
            // add null src
            map1.AddEdge(N, A, edge1);
            fail("Expected IllegalArgumentException not occurred.");
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
        } try {
            // add null dst
            map1.AddEdge(A, N, edge1);
            fail("Expected IllegalArgumentException not occurred.");
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
        } try {
            // add dup edges
            map1.AddEdge(A, B, edge1);
            map1.AddEdge(A, B, edge1);
            fail("Expected IllegalArgumentException not occurred.");
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
        } try {
            // remove non-existent edge from A to B
            map1.AddEdge(A, B, edge1);
            map1.RemoveEdge(A, B, edge2);
            fail("Expected IllegalArgumentException not occurred.");
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
        } finally {
            System.out.close();
        }
    }

    /** Tests ListChildren and ListParents operations. */
    @Test
    public void testListChildrenParents() {
        UnivMap map1 = new UnivMap();

        // Case 1: add & remove one label
        map1.AddEdge(A, B, edge1);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertTrue(map1.ListChildren(A).contains(B));
        assertFalse(map1.ListParents(A).contains(B));
        assertFalse(map1.ListChildren(B).contains(A));
        assertTrue(map1.ListParents(B).contains(A));

        map1.RemoveEdge(A, B, edge1);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertFalse(map1.ListChildren(A).contains(B));
        assertFalse(map1.ListParents(A).contains(B));
        assertFalse(map1.ListChildren(B).contains(A));
        assertFalse(map1.ListParents(B).contains(A));

        // Case 2: Add 2 edges in the same direction
        map1.AddEdge(A, B, edge1);
        map1.AddEdge(A, B, edge2);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertTrue(map1.ListChildren(A).contains(B));
        assertFalse(map1.ListParents(A).contains(B));
        assertFalse(map1.ListChildren(B).contains(A));
        assertTrue(map1.ListParents(B).contains(A));

        map1.RemoveEdge(A, B, edge1);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertTrue(map1.ListChildren(A).contains(B));
        assertFalse(map1.ListParents(A).contains(B));
        assertFalse(map1.ListChildren(B).contains(A));
        assertTrue(map1.ListParents(B).contains(A));


        // Case 3: Add 2 edges in the opposite direction
        map1 = new UnivMap();

        map1.AddEdge(A, B, edge1);
        map1.AddEdge(B, A, edge2);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertTrue(map1.ListChildren(A).contains(B));
        assertTrue(map1.ListParents(A).contains(B));
        assertTrue(map1.ListChildren(B).contains(A));
        assertTrue(map1.ListParents(B).contains(A));

        map1.RemoveEdge(A, B, edge1);
        assertTrue(map1.contains(A));
        assertTrue(map1.contains(B));
        assertFalse(map1.ListChildren(A).contains(B));
        assertTrue(map1.ListParents(A).contains(B));
        assertTrue(map1.ListChildren(B).contains(A));
        assertFalse(map1.ListParents(B).contains(A));
    }

    /**
     * Tests that ListChildren and ListParents throws IllegalArgumentException when
     * passing in null value or non-existent nodes in map
     */
    @Test
    public void testListThrowsIllegalArgumentException() {
        UnivMap map1 = new UnivMap();
        try {
            map1.ListChildren(N);
            fail("Expected IllegalArgumentException not occurred.");
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
        } try {
            map1.ListChildren(A);
            fail("Expected NoSuchElementException not occurred.");
        } catch (NoSuchElementException e) {
            e.getStackTrace();
        } try {
            map1.ListParents(N);
            fail("Expected IllegalArgumentException not occurred.");
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
        } try {
            map1.ListParents(A);
            fail("Expected NoSuchElementException not occurred.");
        } catch (NoSuchElementException e) {
            e.getStackTrace();
        }
    }

}
