package graph;

import org.w3c.dom.Node;

import java.util.Map;

/**
 * This class represents the mathematical concept of a line segment. Think of
 * this as a pair (start-point, end-point), containing the starting and ending
 * points of the line.
 *
 * Abstract Invariant:
 *  A line's start-point must be different from its end-point.
 */
public class UnivMap {

    private Map UnivMap;

    /**
     * (What it does)
     * @param
     * @spec.requires
     * @spec.modifies this.UnivMap
     * @spec.effects
     */
    public void AddNode(Node A) {
    }


    /**
     * Removes a node to the university map
     * @param A Node representing building to be removed from university map
     * @spec.requires A != null && UnivMap.contains(A)
     * @spec.modifies this.UnivMap
     * @spec.effects Removes the Node A from UniMap
     */
    public void RemoveNode(Node A) {
        // Note: all the edges from and to A will be removed as well
    }

    /**
     * Adds a new node to the university map
     * @param A Node representing a building to be added to the university map
     * @spec.requires A != null
     * @spec.modifies this.UnivMap
     * @spec.effects Adds a new node A to UniMap
     */
    public void AddEdge(Node A, Node B, int dist) {
    }

    /**
     * (What it does)
     * @param
     * @spec.requires
     * @spec.modifies this.UnivMap
     * @spec.effects
     */
    public void RemoveEdge(Node A, Node B) {
    }

    /**
     * (What it does)
     * @param
     * @spec.requires
     * @spec.modifies this.UnivMap
     * @spec.effects
     */
    public void contains(Node A) {
    }

    /**
     * (What it does)
     * @param
     * @spec.requires
     * @spec.modifies this.UnivMap
     * @spec.effects
     */
    public void ListChildren(Node A) {
    }

    /**
     * (What it does)
     * @param
     * @spec.requires
     * @spec.modifies this.UnivMap
     * @spec.effects
     */
    public void ListParents(Node A) {
    }

    /**
     * (What it does)
     * @param
     * @spec.requires
     * @spec.modifies this.UnivMap
     * @spec.effects
     */
    public void FindPath(Node A, Node B) {
    }


}
