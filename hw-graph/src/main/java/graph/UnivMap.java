package graph;

import java.util.List;
import java.util.*;

/**
 * UnivMap is a mutable representation of the UW campus map with nodes and edges.
 * The nodes represent the buildings and an edge represents the connection of two buildings,
 * and each edge only goes from one node to another, but not the other way around.
 *
 * Abstract Invariant:
 *  All nodes and edges must not be null, and
 *  all edges have positive labels, and
 *  no duplicate edges from the same source to the same destination
 */
public class UnivMap {
    private static final boolean DEBUG = true;

    // The map of nodes and their outgoing edges and destinations
    private final Map<String, Map<String, List<Integer>>> UnivMap;
    // Representation Invariant:
    //  !UnivMap.keySet().contains(null), and
    //  !UnivMap.get(node 1).keySet().contains(null),
    //      !UnivMap.get(node 2).keySet().contains(null), ...
    //      !UnivMap.get(node n).keySet().contains(null), and
    //  UnivMap.get(source).get(destination): edge 1 != edge 2 != ... != edge r
    //  where n = map.size(), r = UnivMap.get(source).get(destination).size(),
    //  source and destination are nodes in the map
    //
    // Abstraction Function:
    //  AF(this) = a UnivMap, map, such that
    //   map.keySet() = node 1, node 2, ..., node n
    //   map.get(node 1).keySet() = outgoing edges from node 1,
    //      map.get(node 2).keySet() = outgoing edges from node 2, ...,
    //      map.get(node n).keySet() = outgoing edges from node n,
    //   where n = map.size()

    // Checks representation invariant for the entire map, including
    //  checking nulls for all nodes and their outgoing edges, and checking
    //  non-positive and duplicate edges with the same source and destination
    private void checkRep() {
        // cheap tests:
        assert !this.contains(null): "Null node";
        // expensive tests:
        if (DEBUG) {
            for (String source: UnivMap.keySet()) { // take each node
                for (String destination: UnivMap.get(source).keySet()) { // go to its destinations
                    List<Integer> edges = getLabels(source, destination);
                    int n = edges.size();
                    for (int i = 0; i < n; i++) {
                        for (int j = i + 1; j < n; j++) {
                            // verify that edges contains no nulls
                            assert edges.get(i) != null: "Null edge";
                            // verify that edges are all positive
                            assert edges.get(i) > 0: "Non-positive edge";
                            // verify that edges contains no duplicates
                            assert !edges.get(i).equals(edges.get(j)): "Dup edges";
                        }
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
        this.UnivMap = new HashMap<>();
    }

//    static class Edge {
//        String destination;
//        List<Integer> labels;
//        public Edge(String destination, List<Integer> labels) {
//            this.destination = destination;
//            this.labels = labels;
//        }
//    }

    /**
     * Adds a node to this map, if it does not already exist
     * @param A the new node to be added to the map
     * @spec.requires A != null
     * @spec.modifies this
     * @spec.effects node A is added to this
     */
    public void AddNode(String A) throws IllegalArgumentException {
        checkRep();
        // RI: A != null
        // AF(A) = a node in UnivMap named A
        if (A == null) {
            throw new IllegalArgumentException("Tried to add a null node.");
        }
        UnivMap.put(A, new HashMap<>());
        checkRep();
    }

    /**
     * Adds a new route from building A to B on the campus map
     * @param source the source of the new edge
     * @param destination the destination of this new edge
     * @param label the Edge from source to destination to be added to the map
     * @throws IllegalArgumentException if label is not positive, source == destination,
     *         source == null, or destination == null.
     * @spec.requires source and destination are different from each other and not null,
     *                label is positive and is not a duplicate from source to destination
     * @spec.modifies this
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

        // RI: label > 0, !equals(source, destination), source != null, destination != null
        // AF(label) = an edge (named label) from source to destination in this
        if (UnivMap.get(source).containsKey(destination)) {
            List<Integer> existingEdges = getLabels(source, destination);
            for (int edge : existingEdges) {
                if (edge == label) {
                    throw new IllegalArgumentException("Duplicate edges.");
                }
            }
            UnivMap.get(source).get(destination).add(label);
        } else {
            List<Integer> newEdgeList = new ArrayList<>(label);
            newEdgeList.add(label);
            UnivMap.get(source).put(destination, newEdgeList);
        }
        checkRep();
    }

    /**
     * Removes a node (if exists) from the map
     * @param A the node to be removed from this map
     * @spec.modifies this
     * @spec.effects node A and all incoming and outgoing edges are removed from this
     */
    public void RemoveNode(String A) {
        checkRep();
        // RI: same as RI of the class
        // AF(A) = a node named A

        // remove edges that go to A
        for (String str: UnivMap.keySet()) {
            UnivMap.get(str).remove(A);
        }
        // remove A and its outgoing edges
        UnivMap.remove(A);
        checkRep();
    }

    /**
     * Removes from the map an edge (if exists) from source node to destination node
     * @param source the source node of the edge
     * @param destination the destination node of the edge
     * @param label the label of the edge
     * @throws IllegalArgumentException if source.equals(destination)
     * @throws NoSuchElementException if this doesn't contain source
     * @spec.requires source != null, destination != null, and UnivMap.contains(source)
     * @spec.modifies this.UnivMap
     * @spec.effects edge named label from source to destination is removed from this
     */
    public void RemoveEdge(String source, String destination, int label)
            throws IllegalArgumentException, NoSuchElementException {
        checkRep();
        if (source.equals(destination)) {
            throw new IllegalArgumentException("Source and destination are the same.");
        }
        if (!this.contains(source)) {
            throw new NoSuchElementException("Source not in map.");
        }

        // RI: !equals(source, destination), this.contains(source)
        // AF(this) = an edge from source to destination with given label
        List<Integer> ListLabels = getLabels(source, destination);
        for (int i = ListLabels.size() - 1; i >= 0; i--) {
            if (ListLabels.get(i).equals(label)) {
                UnivMap.get(source).get(destination).remove(i);
                if (getLabels(source, destination).size() == 0) {
                    // get rid of empty list with no edges
                    UnivMap.get(source).remove(destination);
                }
            }
        }
        checkRep();
    }


    /**
     * Lists all the nodes that can be directly reached from source
     * @param A the source node
     * @throws IllegalArgumentException if A is null
     * @throws NoSuchElementException if !UnivMap.contains(A)
     * @return A List of nodes that are direct destinations from source A
     * @spec.requires A != null and UnivMap.contains(A)
     */
    public List<String> ListChildren(String A)
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
        // AF(A) = a node in UnivMap named A
        // AF(output) = List<all children nodes of A in UnivMap>
        List<String> output = new ArrayList<>(UnivMap.get(A).keySet());
        // for (String str: UnivMap.get(A).keySet()) {output.add(str);}
        // output.addAll(UnivMap.get(A).keySet());
        checkRep();
        return output;
    }

    /**
     * Lists all the nodes that can directly reach the given destination node
     * @param A the destination node we want to find the parents of
     * @throws IllegalArgumentException if A is null
     * @throws NoSuchElementException if !UnivMap.contains(A)
     * @return List of all the Nodes that are parents of node A
     * @spec.requires A != null and UnivMap.contains(A)
     */
    public List<String> ListParents(String A)
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
        // AF(output) = List<all parent nodes of A in UnivMap>
        List<String> output = new ArrayList<>();
        for (String str: UnivMap.keySet()) {
            if (UnivMap.get(str).containsKey(A)) {
                output.add(str);
            }
        }
        checkRep();
        return output;
    }

    /**
     * Lists all the nodes that can directly reach the given destination node
     * @param source the source node of the edges
     * @param destination the destination node of the edges
     * @return Copy of list of all edges from source node to destination node
     */
    public List<Integer> getLabels(String source, String destination) {
        // RI: Same as the class
        // AF(this) = List of all labels from source node to destination node
        if (!this.contains(source) || !UnivMap.get(source).containsKey(destination)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(UnivMap.get(source).get(destination));
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
