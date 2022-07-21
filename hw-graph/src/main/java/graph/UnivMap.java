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
 *  Each Node and each edge needs to be unique.
 *  An edge's source must be different from its destination.
 */
public class UnivMap {

    // fields, abstraction function, rep invariant , fields, methods, etc

    /**
     * Adds a new building to the university map
     * @param A Node representing the building to be added to the map
     * @spec.requires A != null
     * @spec.modifies this
     * @spec.effects Adds a new Node A to UnivMap
     */
    public void AddNode(Node A) {
        throw new RuntimeException("Not implemented yet.");
    }

    /**
     * Removes an existing building from the university map
     * @param A Node representing building to be removed from university map
     * @spec.requires A != null and UnivMap.contains(A)
     * @spec.modifies this
     * @spec.effects Removes the Node A from UnivMap
     */
    public void RemoveNode(Node A) {
        throw new RuntimeException("Not implemented yet.");
    }

    /**
     * Adds a new route from building A to B on the university map
     * @param source the source of the new edge
     * @param destination the destination of this new edge
     * @param label label of the Edge from A to B to be added to the university map
     * @spec.requires source != null, destination != null, !source.equals(destination),
     *                and !UnivMap.contains(label)
     * @spec.modifies this.UnivMap
     * @spec.effects Adds a new Edge from A to B to university map
     */
    public void AddEdge(Node source, Node destination, String label) {
        throw new RuntimeException("Not implemented yet.");
    }

    /**
     * Removes the route from building A to B from the university map
     * @param source Node representing the source of the edge
     * @param destination Node representing the destination of the edge
     * @spec.requires source != null, destination != null,
     *                UnivMap.contains(source), and UnivMap.contains(destination)
     * @spec.modifies this.UnivMap
     * @spec.effects Removes the Edge from A to B from UnivMap
     */
    public void RemoveEdge(Node source, Node destination) {
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

}
