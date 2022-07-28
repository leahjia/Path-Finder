package graph;

import java.util.List;
import java.util.*;

/**
 * UnivMap represents a mutable map of nodes and edges, where each node and each edge
 *  is represented by a non-null String, and each edge with the same source node and
 *  destination node has a unique label.
 */
public class UnivMap {
    // RI: either true or false
    // AF(this) = true when running expensive rep invariant tests, false when running
    //            only cheap tests
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
    //  AF(this) = a map such that
    //   map.keySet() = {node 1, node 2, ..., node i}, and
    //   map.get(node i) = an inner map, where each key represents each child node of node i,
    //      and map.get(node i).get(child j) = {edge 1, edge 2, ..., edge k}.
    //      i = String representation of node in UnivMap,
    //      j = String representation of child node of node i, and
    //      k = label of edge from node i to node j.
    private final Map<String, Map<String, List<String>>> UnivMap;

    // Checks representation invariant for the entire map, including
    //  checking nulls for all nodes, their outgoing edges, and duplicate edges
    //  with the same source and destination.
    private void checkRep() {
        // cheap tests:
        assert !this.contains(null): "Null node";
        // expensive tests:
        if (DEBUG) {
            for (String source: getNodes()) { // take each node
                for (String destination: UnivMap.get(source).keySet()) { // go to each child
                    List<String> edges = getLabels(source, destination); // go to list of edges
                    // verify no null edges
                    assert !edges.contains(null): "Null edge";
                    int n = edges.size();
                    for (int i = 0; i < n; i++) {
                        for (int j = i + 1; j < n; j++) {
                            // verify no duplicates edges
                            assert !edges.get(i).equals(edges.get(j)): "Duplicate edges";
                        }
                    }
                }
            }
        }
    }

    /**
     * Constructs an empty UnivMap
     * @spec.effects this = a new empty UnivMap
     */
    public UnivMap() {
        this.UnivMap = new HashMap<>();
    }

    /**
     * Adds a node to this map if it does not already exist
     * @param A the node to be added to this map
     * @throws IllegalArgumentException if A is null
     * @spec.requires A != null
     * @spec.modifies this
     * @spec.effects this with node A in it
     */
    public void AddNode(String A) throws IllegalArgumentException {
        checkRep();
        if (A == null) {
            throw new IllegalArgumentException("Tried to add a null node.");
        }
        UnivMap.put(A, new HashMap<>());
        checkRep();
    }

    /**
     * Adds an edge from a source node to a destination node in this map
     * @param src the source node of the edge
     * @param dst the destination node of this edge
     * @param label the edge to be added from src to dst in the map
     * @throws IllegalArgumentException if label is null, src is null, dst is null,
     *         or the same label already exists from src to dst
     * @spec.requires src, dst, and label are not null, and edges from src to dst
     *                do not already contain label.
     * @spec.modifies this
     * @spec.effects edges from src to dst in this now contain label
     */
    public void AddEdge(String src, String dst, String label) throws IllegalArgumentException {
        checkRep();
        if (src == null || dst == null || label == null) {
            throw new IllegalArgumentException("Null node/label received.");
        }
        if (!this.contains(src)) {
            this.AddNode(src);
        }
        if (!this.contains(dst)) {
            this.AddNode(dst);
        }

        checkRep();
        if (UnivMap.get(src).containsKey(dst)) {
            // there is already a list of edges
            if (getLabels(src, dst).contains(label)) {
                throw new IllegalArgumentException("Duplicate edges.");
            }
            // adds label to existing edge list
            UnivMap.get(src).get(dst).add(label);
        } else {
            // creates an edge list for this label
            List<String> newEdgeList = new ArrayList<>();
            newEdgeList.add(label);
            UnivMap.get(src).put(dst, newEdgeList);
        }
        checkRep();
    }

    /**
     * Removes a node (if exists) and all edges from and to this node from the map
     * @param A the node to be removed from this map
     * @spec.modifies this
     * @spec.effects node A and all its incoming and outgoing edges are removed from this
     */
    public void RemoveNode(String A) {
        checkRep();
        // remove edges that go to A
        for (String str: getNodes()) {
            UnivMap.get(str).remove(A);
        }
        // remove A and its outgoing edges
        UnivMap.remove(A);
        checkRep();
    }

    /**
     * Removes from the map an edge (if exists) from source node to destination node
     * @param src the source node of the edge
     * @param dst the destination node of the edge
     * @param label the label of the edge
     * @spec.modifies this
     * @spec.effects edge named label from source to destination is removed from this
     */
    public void RemoveEdge(String src, String dst, String label)  {
        checkRep();
        List<String> labels = getLabels(src, dst);
        if (labels.contains(label)) {
            UnivMap.get(src).get(dst).remove(labels.indexOf(label));
            if (labels.size() == 1) { // actual size is 0 after remove
                // get rid of empty list with no edges
                UnivMap.get(src).remove(dst);
            }
        }
        checkRep();
    }


    /**
     * Lists all the nodes that can be directly reached from given source node
     * @param A the source node
     * @throws IllegalArgumentException if A is null
     * @throws NoSuchElementException if !UnivMap.contains(A)
     * @return A list of nodes that are direct destinations from source A
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
        return new ArrayList<>(UnivMap.get(A).keySet());
    }

    /**
     * Lists all the nodes that can directly reach given destination node
     * @param A the destination node we want to find the parents of
     * @throws IllegalArgumentException if A is null
     * @throws NoSuchElementException if !UnivMap.contains(A)
     * @return List of all the nodes that can directly reach A
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

        List<String> output = new ArrayList<>();
        for (String str: getNodes()) {
            if (UnivMap.get(str).containsKey(A)) {
                output.add(str);
            }
        }
        checkRep();
        return output;
    }

    /**
     * Lists all the nodes in this
     * @return List of all nodes in this map
     */
    public List<String> getNodes() {
        return new ArrayList<>(UnivMap.keySet());
    }

    /**
     * Lists all the edges from given source node to given destination node
     * @param src the source node of the edges
     * @param dst the destination node of the edges
     * @return list of all edges from src to dst
     */
    public List<String> getLabels(String src, String dst) {
        if (!this.contains(src) || !UnivMap.get(src).containsKey(dst)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(UnivMap.get(src).get(dst));
    }

    /**
     * Checks if this map contains node A
     * @param A the node to be checked in this
     * @return a boolean that is true if this contains A and false otherwise
     */
    public boolean contains(String A) {
        return UnivMap.containsKey(A);
    }

}
