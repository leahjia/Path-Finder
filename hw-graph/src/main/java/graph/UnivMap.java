package graph;

import java.util.List;
import java.util.*;


import org.w3c.dom.Node;

/**
 * UnivMap is a mutable representation of the UW campus map with nodes and edges.
 * The nodes represent the buildings and an edge represents the connection of two buildings.
 *
 * Abstract Invariant:
 *  Each Node and each edge needs to be unique.
 *  An edge's source must be different from its destination.
 */
public class UnivMap {

    // fields, abstraction function, rep invariant , fields, methods, etc
    private final Map<String, Map<String, Integer>> UnivMap;

    public UnivMap() {
        this.UnivMap = new HashMap<>();
    }

//    static class Edge {
//        String destination;
//        int weight;
//        public Edge(String destination, int weight) {
//            this.destination = destination;
//            this.weight = weight;
//        }
//    }

    /**
     * Adds a new building to the campus map
     * @param A Node representing the building to be added to the map
     * @throws IllegalArgumentException if the same node already exist or A is null
     * @spec.requires A != null and !UnivMap.contains(A)
     * @spec.modifies this
     * @spec.effects Adds a new Node A to UnivMap
     */
    public void AddNode(String A) {
        if (!this.contains(A)) {
            UnivMap.put(A, new HashMap<>());
        }
    }

    /**
     * Adds a new route from building A to B on the campus map
     * @param source the source of the new edge
     * @param destination the destination of this new edge
     * @param weight label of the Edge from A to B to be added to the campus map
     * @throws IllegalArgumentException if source is null, destination is null,
     *         label != null, source.equals(destination), or UnivMap.contains(label)
     * @spec.requires source != null, destination != null, label != null,
     *                !source.equals(destination), and !UnivMap.contains(label)
     * @spec.modifies this.UnivMap
     * @spec.effects Adds a new Edge from A to B to campus map
     */
    public void AddEdge(String source, String destination, int weight) {
        if (contains(source) && contains(destination)) {
            UnivMap.get(source).put(destination, weight);
        }
    }

    /**
     * Removes an existing building from the campus map
     * @param A Node representing building to be removed from campus map
     * @throws IllegalArgumentException if A is null or !UnivMap.contains(A)
     * @spec.requires A != null and UnivMap.contains(A)
     * @spec.modifies this
     * @spec.effects Removes the Node A from UnivMap
     */
    public void RemoveNode(String A) {
        if (this.contains(A)) {
            for (String str: UnivMap.keySet()) {
                UnivMap.get(str).remove(A);
            }
            UnivMap.remove(A);
        }
    }

    /**
     * Removes the route from building A to B from the campus map
     * @param source the source of the edge
     * @param destination the destination of the edge
     * @throws IllegalArgumentException if source is null, destination is null,
     *         or source.equals(destination)
     * @spec.requires source != null, destination != null,
     *                UnivMap.contains(source), and UnivMap.contains(destination)
     * @spec.modifies this.UnivMap
     * @spec.effects Removes the Edge from A to B from UnivMap
     */
    public void RemoveEdge(String source, String destination) {
        if (this.contains(source)) {
            UnivMap.get(source).remove(destination);
        }
    }

    /**
     * Checks if building A exists on the map
     * @param A Node representing the building A to be checked on the map
     * @throws IllegalArgumentException if A is null
     * @return a boolean that will be true if A exists and false otherwise
     * @spec.requires A != null
     */
    public boolean contains(String A) {
        return UnivMap.containsKey(A);
    }

    /**
     * Lists all the buildings that can be reached starting from building A
     * @param A the Node representing the starting building
     * @throws IllegalArgumentException if A is null or !UnivMap.contains(A)
     * @return A List of Nodes that are children of Node A
     * @spec.requires A != null and UnivMap.contains(A)
     */
    public List<String> ListChildren(String A) {
        throw new RuntimeException("Not implemented yet.");
    }

    /**
     * Lists all the parent Nodes of A
     * @param A the Node we want to find the parents of
     * @throws IllegalArgumentException if A is null or !UnivMap.contains(A)
     * @return A List of all the Nodes that are parent(s) of Node A
     * @spec.requires A != null and UnivMap.contains(A)
     */
    public List<String> ListParents(String A) {
        throw new RuntimeException("Not implemented yet.");
    }

    private boolean checkRep() {
        return false;
    }

}
