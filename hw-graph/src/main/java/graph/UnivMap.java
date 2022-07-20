package graph;

import org.w3c.dom.Node;
import java.util.List;
//import graph.*;

/**
 * This class represents the UW campus map, containing information such as the names
 * of the buildings and the distance from one building to another.
 * This class can be a tool to find the connections among buildings and available
 * paths between two buildings.
 *
 * Abstract Invariant:
 *  To find a path, the starting building must be different from the destination.
 */
public class UnivMap {

    // fields, abstraction function, rep invariant , fields, methods, etc

    /**
     * Adds a new building to the university map
     * @param A Node representing the building to be added to the map
     * @spec.requires A != null
     * @spec.modifies this.UnivMap
     * @spec.effects Adds a new Node A to UnivMap
     */
    public void AddNode(Node A) {
        throw new RuntimeException("Not implemented yet.");
    }

    /**
     * Removes an existing building from the university map
     * @param A Node representing building to be removed from university map
     * @spec.requires A != null and UnivMap.contains(A)
     * @spec.modifies this.UnivMap
     * @spec.effects Removes the Node A from UnivMap
     */
    public void RemoveNode(Node A) {
        throw new RuntimeException("Not implemented yet.");
    }

    /**
     * Adds a new route from building A to B on the university map
     * @param A Node representing building A on the university map
     * @param B Node representing building B on the university map
     * @param str label of the Edge from A to B to be added to the university map,
     * @spec.requires A != null, B != null, !A.equals(B), and dist is greater than 0
     * @spec.modifies this.UnivMap
     * @spec.effects Adds a new Edge from A to B to UnivMap
     */
    public void AddEdge(Node A, Node B, String str) {
        throw new RuntimeException("Not implemented yet.");
    }

    /**
     * Removes the route from building A to B from the university map
     * @param A Node representing building A on the university map
     * @param B Node representing building B on the university map
     * @spec.requires A != null, B != null, UnivMap.contains(A), and UnivMap.contains(A)
     * @spec.modifies this.UnivMap
     * @spec.effects Removes the Edge from A to B from UnivMap
     */
    public void RemoveEdge(Node A, Node B) {
        throw new RuntimeException("Not implemented yet.");
    }

    /**
     * Checks if building A exists on the map
     * @param A Node representing the building A to be checked on the map
     * @return a boolean that will be true if A exists and false otherwise
     * @spec.requires A != null
     */
    public boolean contains(Node A) {
        throw new RuntimeException("Not implemented yet.");
    }

    /**
     * Lists all the buildings that can be reached starting from building A
     * @param A the Node representing the starting building
     * @return A List of Nodes that are children of Node A
     * @spec.requires A != null and UnivMap.contains(A)
     */
    public List ListChildren(Node A) {
        throw new RuntimeException("Not implemented yet.");
    }

    /**
     * Lists all the parent Nodes of A
     * @param A the Node we want to find the parents of
     * @return A List of all the Nodes that are parent(s) of Node A
     * @spec.requires UnivMap.contains(A)
     */
    public List ListParents(Node A) {
        throw new RuntimeException("Not implemented yet.");
    }

    /**
     * Finds an available path that can be taken from building A to building B
     * @param A the source, or starting point
     * @param B the destination
     * @return a List of Edges from A to B
     * @spec.requires UnivMap.contains(A), UnivMap.contains(B), and !A.equals(B)
     */
    public List FindPath(Node A, Node B) {
        throw new RuntimeException("Not implemented yet.");
    }

//    /**
//     * Finds the distance from building A to building B
//     * @param A the source, or starting point
//     * @param B the destination
//     * @return an integer representing the distance from A to B
//     * @spec.requires UnivMap.contains(A), UnivMap.contains(B), and !A.equals(B)
//     */
//    public int FindDistance(Node A, Node B) {
//        throw new RuntimeException("Not implemented yet.");
//    }

}
