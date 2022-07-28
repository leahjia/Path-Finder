package graph;

import java.util.List;
import java.util.*;

/**
 * UnivMap is a mutable representation of the campus map with nodes and edges, where
 *  the nodes represent the buildings and edges represent routes from one node to another.
 *
 * Abstract Invariant:
 *  All nodes and edges must not be null, and edges with the same source and destination
 *   have unique labels
 */
public class UnivMap {
    private static final boolean DEBUG = true;

    // The map of nodes and their outgoing edges and destinations
    // Representation Invariant:
    //  !UnivMap.contains(null), and
    //  !UnivMap.get(node 1).contains(null), !UnivMap.get(node 2).contains(null), ...
    //      !UnivMap.get(node n).contains(null), where n = map.size(), and
    //  UnivMap.get(source).get(destination).get(0)
    //      != UnivMap.get(source).get(destination).get(1) != ...
    //      != UnivMap.get(source).get(destination).get(i - 1),
    //      where i = UnivMap.get(source).get(destination).size(),
    //
    // Abstraction Function:
    //  AF(this) = a UnivMap, map, such that
    //   map.keySet() = node 1, node 2, ..., node n, and
    //   map.get(node 1) = list of outgoing edges from node 1,
    //      map.get(node 2) = list of outgoing edges from node 2, ...,
    //      map.get(node n) = list of outgoing edges from node n,
    //   where n = map.size()
    private final Map<String, Map<String, List<String>>> UnivMap;

    // Checks representation invariant for the entire map, including
    //  checking nulls for all nodes, their outgoing edges, and duplicate edges
    //  with the same source and destination
    private void checkRep() {
        // cheap tests:
        assert !this.contains(null): "Null node";
        // expensive tests:
        if (DEBUG) {
            for (String source: UnivMap.keySet()) { // take each node
                for (String destination: UnivMap.get(source).keySet()) { // go to each child
                    List<String> edges = getLabels(source, destination); // go to list of edges
                    // verify that edges contains no nulls
                    assert !edges.contains(null): "Null edge";
                    int n = edges.size();
                    for (int i = 0; i < n; i++) {
                        for (int j = i + 1; j < n; j++) {
                            // verify no duplicates edges
                            assert !edges.get(i).equals(edges.get(j)): "Dup edges";
                        }
                    }
                }
            }
        }
    }

    /**
     * Constructs an empty UnivMap which is a mutable HashMap
     * @spec.effects this = a new empty HashMap
     */
    public UnivMap() {
        this.UnivMap = new HashMap<>();
    }

    /**
     * Adds a node to this map, if it does not already exist
     * @param A the new node to be added to the map
     * @throws IllegalArgumentException if A is null
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
     * @throws IllegalArgumentException if label is null, source is null, or destination is null.
     * @spec.requires source and destination are different from each other and not null,
     *                label is not null and is not a duplicate from source to destination
     * @spec.modifies this
     * @spec.effects edge named label from source to destination is added to this
     */
    public void AddEdge(String source, String destination, String label)
            throws IllegalArgumentException {
        checkRep();
        if (source == null || destination == null || label == null) {
            throw new IllegalArgumentException("Null node/label received.");
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
            List<String> existingEdges = getLabels(source, destination);
            for (String edge : existingEdges) {
                if (equals(edge, label)) {
                    throw new IllegalArgumentException("Duplicate edges.");
                }
            }
            UnivMap.get(source).get(destination).add(label);
        } else {
            List<String> newEdgeList = new ArrayList<>();
            newEdgeList.add(label);
            UnivMap.get(source).put(destination, newEdgeList);
        }
//        UnivMap.get(source).get(destination).add(label);
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
     * @throws NoSuchElementException if this doesn't contain source
     * @spec.requires source != null, destination != null, and UnivMap.contains(source)
     * @spec.modifies this.UnivMap
     * @spec.effects edge named label from source to destination is removed from this
     */
    public void RemoveEdge(String source, String destination, String label)
            throws IllegalArgumentException, NoSuchElementException {
        checkRep();
        if (!this.contains(source)) {
            throw new NoSuchElementException("Source not in map.");
        }

        // RI: !equals(source, destination), this.contains(source)
        // AF(this) = an edge from source to destination with given label
        List<String> ListLabels = getLabels(source, destination);
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
     * Lists all the nodes in this
     * @return Copy of list of all nodes in this map
     */
    public List<String> getNodes() {
        // RI: Same as the class
        // AF(this) = List of all nodes in this map
        return new ArrayList<>(UnivMap.keySet());
    }

    /**
     * Lists all the nodes that can directly reach the given destination node
     * @param source the source node of the edges
     * @param destination the destination node of the edges
     * @return Copy of list of all edges from source node to destination node
     */
    public List<String> getLabels(String source, String destination) {
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

    // private method to check if A and B are the same
    private boolean equals(String A, String B) {
        checkRep();
        // RI: same as the class
        // AF(this) = boolean whether A and B are the same
        return A.hashCode() == B.hashCode();
    }
}
