package graph;

import java.util.List;
import java.util.*;


import org.w3c.dom.Node;

/**
 * UnivMap is a mutable representation of the UW campus map with nodes and edges.
 * The nodes represent the buildings and an edge represents the connection of two buildings.
 *
 * Abstract Invariant:
 *  All nodes and edges must not be null, and
 *  no duplicate edges from the same source to the same destination must be unique
 */
public class UnivMap {
    private static final boolean DEBUG = false;

    // The map of nodes and their outgoing edges and destinations
    private final Map<String, HashMap<String, List<Integer>>> UnivMap;
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
    //  duplicate nodes and duplicate edges with the same source and destination
    private void checkRep() {
        // cheap tests:
        assert !this.contains(null);
        // expensive tests:
        if (DEBUG) {
            for (String source: UnivMap.keySet()) { // take each node
                for (String destination: UnivMap.get(source).keySet()) { // go to destinations
                    List<Integer> edges = UnivMap.get(source).get(destination);
                    int n = edges.size();
                    for (int i = 0; i < n; i++) { // check each edge
                        for (int j = i + 1; j < n; j++) { // compare to the rest of edges
                            assert edges.get(i) > 0;
                            assert edges.get(i).equals(edges.get(j));
                        }
                    }
                }
            }
        }
    }

    /**
     * Overview: A UnivMap is a mutable map of nodes and edges
     * @spec.effects Constructs an empty UnivMap
     */
    public UnivMap() {
        this.UnivMap = new HashMap<>();
    }

//    static class Edge {
//        int label;
//        public Edge(int label) {
//            this.label = label;
//        }
//    }

    /**
     * Adds a new node to this map
     * @param A the new node to be added to the map
     * @spec.requires A != null
     * @spec.modifies this
     * @spec.effects this = this + {node A}
     */
    public void AddNode(String A) {
        checkRep();
        // RI: A != null
        // AF(this) = a node in UnivMap named A
        UnivMap.put(A, new HashMap<>());
        checkRep();
    }

    /**
     * Adds a new route from building A to B on the campus map
     * @param source the source of the new edge
     * @param destination the destination of this new edge
     * @param label the Edge from source to destination to be added to the map
     * @throws IllegalArgumentException if label <= 0, label already exists,
     *         source == destination, source == null, or destination == null.
     * @spec.requires source and destination are different from each other and not null,
     *                label is positive and is not a duplicate from source to destination
     * @spec.modifies this
     * @spec.effects this = this + {edge(source to destination, label)}
     */
    public void AddEdge(String source, String destination, int label)
            throws IllegalArgumentException {
        checkRep();
        if (label <= 0) {
            throw new IllegalArgumentException("Label much be positive.");
        } else if (equals(source, destination)) {
            throw new IllegalArgumentException("Source and destination must be different.");
        }
        if (!this.contains(source)) {
            this.AddNode(source);
        }
        if (!this.contains(destination)) {
            this.AddNode(destination);
        }
        if (UnivMap.get(source).containsKey(destination)) {
            List<Integer> existingEdges = UnivMap.get(source).get(destination);
            for (int edge : existingEdges) {
                if (edge == label) {
                    throw new IllegalArgumentException("Duplicate edges.");
                }
            }
            UnivMap.get(source).get(destination).add(label);
        } else {
            List<Integer> newEdgeList = new ArrayList<>();
            newEdgeList.add(label);
            UnivMap.get(source).put(destination, newEdgeList);
        }
        checkRep();
    }

    /**
     * Removes a node (if exists) from the map
     * @param A the node to be removed from this map
     * @spec.modifies this
     * @spec.effects this = this - {node A}
     */
    public void RemoveNode(String A) {
        checkRep();
        // remove edges that go to A
        for (String str: UnivMap.keySet()) {
            UnivMap.get(str).remove(A);
        }
        // remove A and its outgoing edges
        UnivMap.remove(A);
        checkRep();
    }

    /**
     * Removes from the map the edge (if exists) from source node to destination node
     * @param source the source of the edge
     * @param destination the destination of the edge
     * @throws IllegalArgumentException if source.equals(destination)
     * @spec.requires source != null, destination != null,
     *                UnivMap.contains(source), and UnivMap.contains(destination)
     * @spec.modifies this.UnivMap
     * @spec.effects this = this - {edge(source to destination, label)}
     */
    public void RemoveEdge(String source, String destination) throws IllegalArgumentException {
        checkRep();
        if (equals(source, destination)) {
            throw new IllegalArgumentException("Source and destination are the same.");
        }
        if (!this.contains(source)) {
            throw new IllegalArgumentException("Source not in map.");
        }
        UnivMap.get(source).remove(destination);
        checkRep();
    }


    /**
     * Lists all the nodes that can be directly reached from source
     * @param A the source node
     * @throws IllegalArgumentException if A is null or !UnivMap.contains(A)
     * @return A List of nodes that are direct destinations from source A
     * @spec.requires A != null and UnivMap.contains(A)
     */
    public List<String> ListChildren(String A) throws IllegalArgumentException {
        checkRep();
        if (A == null) {
            throw new IllegalArgumentException("Null node received.");
        }
        if (!this.contains(A)) {
            throw new IllegalArgumentException("Node does not exist.");
        }
        List<String> output = new ArrayList<>(UnivMap.get(A).keySet());
        // for (String str: UnivMap.get(A).keySet()) {output.add(str);}
        // output.addAll(UnivMap.get(A).keySet());
        checkRep();
        return output;
    }

    /**
     * Lists all the nodes that can directly reach the given destination node
     * @param A the destination node we want to find the parents of
     * @throws IllegalArgumentException if A is null or !UnivMap.contains(A)
     * @return List of all the Nodes that are parents of node A
     * @spec.requires A != null and UnivMap.contains(A)
     */
    public List<String> ListParents(String A) throws IllegalArgumentException {
        checkRep();
        if (A == null) {
            throw new IllegalArgumentException("Null node received.");
        }
        if (!this.contains(A)) {
            throw new IllegalArgumentException("Node does not exist.");
        }
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
     * Checks if building A exists on the map
     * @param A Node representing the building A to be checked on the map
     * @throws IllegalArgumentException if A is null
     * @return a boolean that will be true if A exists and false otherwise
     * @spec.requires A != null
     */
    public boolean contains(String A) {
        return UnivMap.containsKey(A);
    }

    private boolean equals(String A, String B) {
        checkRep();
        return A.hashCode() == B.hashCode();
    }
}
