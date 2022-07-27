package graph;

import java.util.List;
import java.util.*;

/**
 * UnivMap is a mutable representation of the UW campus map with nodes and edges.
 * The nodes represent the buildings and an edge represents the connection of two buildings.
 *
 * Abstract Invariant:
 *  All nodes must not be null, all edges are positive, and
 *  only one edge from the same source to the same destination
 */
public class UnivMap {
    private static final boolean DEBUG = true;

    // The map of nodes and their outgoing edges and destinations
    private final Map<String, List<Edge>> UnivMap;
    // Representation Invariant:
    //  !UnivMap.keySet().contains(null), and
    //  UnivMap.get(node 1).get(edge i).label > 0,
    //      UnivMap.get(node 2).get(edge i).label > 0, ...,
    //      UnivMap.get(node n).get(edge i).label > 0,
    //      where n = map.size() and 0 <= i < UnivMap.get(node).size()
    //
    // Abstraction Function:
    //  AF(this) = a UnivMap, map, such that
    //   map.keySet() = node 1, node 2, ..., node n
    //   map.get(node 1) = list of outgoing edges from node 1,
    //      map.get(node 2) = list of outgoing edges from node 2, ...,
    //      map.get(node n) = list of outgoing edges from node n,
    //   where n = map.size()

    // Checks representation invariant for the entire map, including
    //  checking nulls nodes, non-positive edges, and
    //      duplicate edges from the same source to the same destination
    private void checkRep() {
        // cheap tests:
        assert !this.contains(null): "Null node found in map.";
        // expensive tests:
        if (DEBUG) {
            for (String source: UnivMap.keySet()) { // take each node
                List<Edge> edges = UnivMap.get(source);
                int n = edges.size();
                for (int i = 0; i < n; i++) {
                    for (int j = i + 1; j < n; j++) {
                        // verify that edges are all positive
                        assert edges.get(i).label > 0: "Non-positive edge";
                        // verify that edges contains no duplicates
                        assert !edges.get(i).equals(edges.get(j)): "Dup edges";
                    }
                }
            }
        }
    }

    /**
     * Overview: A UnivMap is a mutable map of nodes and edges
     * @spec.effects an empty UnivMap is constructed
     */
    public UnivMap() {
        // RI: same as the class
        // AF(this) = a newly constructed UnivMap
        UnivMap = new HashMap<>();
    }

    static class Edge {
        String edgeTo;
        int label;
        public Edge(String edgeTo, int label) {
            this.edgeTo = edgeTo;
            this.label = label;
        }
    }

    /**
     * Adds a node to this map, if it does not already exist
     * @param A the new node to be added to the map
     * @throws IllegalArgumentException if node A is null
     * @spec.requires A != null
     * @spec.modifies this
     * @spec.effects node A is added to this
     */
    public void AddNode(String A) throws IllegalArgumentException {
        checkRep();
        // RI: A != null
        // AF(A) = a node in UnivMap named A
        if (A == null) {
            throw new IllegalArgumentException("Attempted to add null node.");
        }
        UnivMap.put(A, new ArrayList<>());
        checkRep();
    }

    /**
     * Adds an edge from source to destination in this map, if there isn't one.
     *  If an edge already exists, replace it only if the new edge has a smaller value
     * @param source the source of the edge
     * @param destination the destination of this edge
     * @param label weight of the Edge from source to destination to be added to the map
     * @throws IllegalArgumentException if label is not positive, source == destination,
     *         source == null, or destination == null.
     * @spec.requires source and destination are different and not null, and label is positive
     * @spec.modifies this, if the new edge is accepted
     * @spec.effects edge named label from source to destination is added to this
     */
    public void AddEdge(String source, String destination, int label)
            throws IllegalArgumentException {
        checkRep();
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Null nodes received.");
        }
        if (label <= 0) {
            throw new IllegalArgumentException("Label must be positive.");
        }
        if (source.equals(destination)) {
            throw new IllegalArgumentException("Source = destination.");
        }
        if (!this.contains(source)) {
            this.AddNode(source);
        }
        if (!this.contains(destination)) {
            this.AddNode(destination);
        }

        checkRep();

        // RI: label > 0, !source.equals(destination), source != null, destination != null
        // AF(label) = an edge (named label) from source to destination in this
        if (!ListNeighborsFrom(source).contains(destination)) {
            UnivMap.get(source).add(new Edge(destination, label));
        } else {
            int index = UnivMap.get(source).indexOf(destination);
            int oldLabel = UnivMap.get(source).get(index).label;
            UnivMap.get(source).get(index).label = Math.min(oldLabel, label);
        }
        checkRep();
    }

    /**
     * Removes a node (if exists) from the map
     * @param A the node to be removed from this map
     * @spec.modifies this
     * @spec.effects node A and all its incoming and outgoing edges are removed from this
     */
    public void RemoveNode(String A) {
        checkRep();
        // RI: same as RI of the class
        // AF(A) = a node named A

        // remove all edges that go to A
        for (String node: UnivMap.keySet()) {
            UnivMap.get(node).remove(A);
        }
        // remove A and its outgoing edges
        UnivMap.remove(A);
        checkRep();
    }

    /**
     * Removes from the map the edge (if exists) from source node to destination node
     * @param source the source node of the edge
     * @param destination the destination node of the edge
     * @throws IllegalArgumentException if source.equals(destination)
     * @throws NoSuchElementException if this doesn't contain source
     * @spec.requires !source.equals(destination), and UnivMap.contains(source)
     * @spec.modifies this.UnivMap
     * @spec.effects edge named label from source to destination is removed from this
     */
    public void RemoveEdge(String source, String destination)
            throws IllegalArgumentException, NoSuchElementException {
        checkRep();
        if (source.equals(destination)) {
            throw new IllegalArgumentException("Source and destination are the same.");
        }
        if (!this.contains(source)) {
            throw new NoSuchElementException("Source not in map.");
        }

        // RI: !equals(source, destination), this.contains(source)
        // AF(this) = edge from source to destination
        UnivMap.get(source).remove(destination);
        checkRep();
    }


    /**
     * Lists all the nodes that can be directly reached from node A
     * @param A the source node
     * @throws IllegalArgumentException if A is null
     * @throws NoSuchElementException if !UnivMap.contains(A)
     * @return A list of nodes that are direct destinations from source A
     * @spec.requires A != null and UnivMap.contains(A)
     */
    public List<String> ListNeighborsFrom(String A)
            throws IllegalArgumentException, NoSuchElementException {
        checkRep();
        if (A == null) {
            throw new IllegalArgumentException("Null node received.");
        }
        if (!this.contains(A)) {
            throw new NoSuchElementException("Node does not exist.");
        }

        checkRep();

        // RI: A != null, this.contains(A)
        // AF(A) = a node named A in this map
        // AF(output) = List of all neighbor nodes going from A in this map
        List<String> output = new ArrayList<>();
        for (Edge neighbor: UnivMap.get(A)) {
            output.add(neighbor.edgeTo);
        }
        checkRep();
        return output;
    }

    /**
     * Lists all the nodes that can directly reach the given destination node
     * @param A the destination node we want to find the parents of
     * @throws IllegalArgumentException if A is null
     * @throws NoSuchElementException if !UnivMap.contains(A)
     * @return A list of all the Nodes that can reach node A
     * @spec.requires A != null and UnivMap.contains(A)
     */
    public List<String> ListNeighborsTo(String A)
            throws IllegalArgumentException, NoSuchElementException{
        checkRep();
        if (A == null) {
            throw new IllegalArgumentException("Null node received.");
        }
        if (!this.contains(A)) {
            throw new NoSuchElementException("Node does not exist.");
        }
        checkRep();

        // RI: A != null, this.contains(A)
        // AF(A) = a node named A in this map
        // AF(output) = List of all parent nodes of A in this map
        List<String> output = new ArrayList<>();
        for (String node: UnivMap.keySet()) {
            if (UnivMap.get(node).contains(A)) {
                output.add(node);
            }
        }
        checkRep();
        return output;
    }

    /**
     * Returns the distance from source node to destination node,
     *  returns -1 if source cannot directly reach destination
     * @param source the source node of the edges
     * @param destination the destination node of the edges
     * @return An integer representing the direct distance from source to destination,
     *         -1 if there is no edge from source to destination
     */
    public int DistanceTo(String source, String destination) throws IllegalArgumentException {
        // RI: Same as the class
        // AF(this) = List of all labels from source node to destination node
        if (!this.contains(source)) {
            throw new IllegalArgumentException("Source not in map.");
        }
        int indexEdgeTo = UnivMap.get(source).indexOf(destination);
        if (indexEdgeTo != -1) { // then there is a path
            int output = UnivMap.get(source).get(indexEdgeTo).label;
            return output;
        }
        return -1; // there is no path
    }

    /**
     * Checks if this map contains node A
     * @param A node to be checked on the map
     * @return a boolean that is true if A exists in UnivMap and false otherwise
     */
    public boolean contains(String A) {
        // RI: same as the class
        // AF(this) = boolean whether A is in map
        return UnivMap.containsKey(A);
    }

//    // private method to check if A and B are the same
//    private boolean equals(String A, String B) {
//        checkRep();
//        // RI: same as the class
//        // AF(this) = boolean whether A and B are the same
//        return A.hashCode() == B.hashCode();
//    }
}
