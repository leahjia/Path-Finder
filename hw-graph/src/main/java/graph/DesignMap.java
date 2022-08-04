package graph;

import java.util.List;
import java.util.*;

/**
 * DesignMap represents a mutable map of nodes and edges, where each node is
 *  represented by a non-null T, and each edge is represented by a non-null E.
 *
 *  Abstract Invariant:
 *  *  Each Node and each edge needs to be unique.
 */
public class DesignMap<T, E> {
    // RI: either true or false
    // AF(this) = true when running expensive rep invariant tests, false when running
    //            only cheap tests
    private static final boolean DEBUG = false;

    // The map of nodes and their outgoing edges and destinations
    // Representation Invariant:
    //  !DesignMap.contains(null), and
    //  !DesignMap.get(node 1).contains(null), !DesignMap.get(node 2).contains(null), ...
    //      !DesignMap.get(node n).contains(null), where n = map.size(), and
    //  DesignMap.get(source).get(destination).get(0)
    //      != DesignMap.get(source).get(destination).get(1) != ...
    //      != DesignMap.get(source).get(destination).get(i - 1),
    //      where i = DesignMap.get(source).get(destination).size(),
    //
    // Abstraction Function:
    //  AF(this) = a map such that
    //   map.keySet() = {node 1, node 2, ..., node i}, and
    //   map.get(node i) = an inner map, where each key represents each child node of node i,
    //      and map.get(node i).get(child j) = {edge 1, edge 2, ..., edge k}.
    //      i = T representation of node in DesignMap,
    //      j = T representation of child node of node i, and
    //      k = label of edge from node i to node j.
    private final Map<T, Map<T, List<E>>> DesignMap;

    // Checks representation invariant for the entire map, including
    //  checking nulls for all nodes, their outgoing edges, and duplicate edges
    //  with the same source and destination.
    private void checkRep() {
        // cheap tests:
        assert !this.contains(null): "Null node";
        // expensive tests:
        if (DEBUG) {
            for (T src: DesignMap.keySet()) { // take each node
                for (T dst: DesignMap.get(src).keySet()) { // go to each child
                    List<E> edges = new ArrayList<>(DesignMap.get(src).get(dst)); // go to list of edges
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
     * Constructs an empty DesignMap
     * @spec.effects this = a new empty DesignMap
     */
    public DesignMap() {
        this.DesignMap = new HashMap<>();
    }

    /**
     * Adds a node to this map if it does not already exist
     * @param A the node to be added to this map
     * @throws IllegalArgumentException if A is null
     * @spec.requires A != null
     * @spec.modifies this
     * @spec.effects this with node A in it
     */
    public void AddNode(T A) throws IllegalArgumentException {
        checkRep();
        if (A == null) {
            throw new IllegalArgumentException("Tried to add a null node.");
        }
        DesignMap.put(A, new HashMap<>());
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
    public void AddEdge(T src, T dst, E label) throws IllegalArgumentException {
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
        if (DesignMap.get(src).containsKey(dst)) {
            // there is already a list of edges
            if (getLabels(src, dst).contains(label)) {
                throw new IllegalArgumentException("Duplicate edges.");
            }
            // adds label to existing edge list
            DesignMap.get(src).get(dst).add(label);
        } else {
            // creates an edge list for this label
            List<E> newEdgeList = new ArrayList<>();
            newEdgeList.add(label);
            DesignMap.get(src).put(dst, newEdgeList);
        }
        checkRep();
    }

    /**
     * Removes a node (if exists) and all edges from and to this node from the map
     * @param A the node to be removed from this map
     * @spec.modifies this
     * @spec.effects node A and all its incoming and outgoing edges are removed from this
     */
    public void RemoveNode(T A) {
        checkRep();
        // remove edges that go to A
        for (T str: getNodes()) {
            DesignMap.get(str).remove(A);
        }
        // remove A and its outgoing edges
        DesignMap.remove(A);
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
    public void RemoveEdge(T src, T dst, E label)  {
        checkRep();
        List<E> labels = getLabels(src, dst);
        if (labels.contains(label)) {
            DesignMap.get(src).get(dst).remove(labels.indexOf(label));
            if (labels.size() == 1) { // actual size is 0 after remove
                // get rid of empty list with no edges
                DesignMap.get(src).remove(dst);
            }
        }
        checkRep();
    }


    /**
     * Lists all the nodes that can be directly reached from given source node
     * @param A the source node
     * @throws IllegalArgumentException if A is null
     * @throws NoSuchElementException if !DesignMap.contains(A)
     * @return A list of nodes that are direct destinations from source A
     * @spec.requires A != null and DesignMap.contains(A)
     */
    public List<T> ListChildren(T A)
            throws IllegalArgumentException, NoSuchElementException {
        checkRep();
        if (A == null) {
            throw new IllegalArgumentException("Null node received.");
        }
        if (!this.contains(A)) {
            throw new NoSuchElementException("Node does not exist.");
        }
        List<T> output = new ArrayList<>(DesignMap.get(A).keySet());
        checkRep();
        return output;
    }

    /**
     * Lists all the nodes that can directly reach given destination node
     * @param A the destination node we want to find the parents of
     * @throws IllegalArgumentException if A is null
     * @throws NoSuchElementException if !DesignMap.contains(A)
     * @return List of all the nodes that can directly reach A
     * @spec.requires A != null and DesignMap.contains(A)
     */
    public List<T> ListParents(T A)
            throws IllegalArgumentException, NoSuchElementException{
        checkRep();
        if (A == null) {
            throw new IllegalArgumentException("Null node received.");
        }
        if (!this.contains(A)) {
            throw new NoSuchElementException("Node does not exist.");
        }

        List<T> output = new ArrayList<>();
        for (T str: getNodes()) {
            if (DesignMap.get(str).containsKey(A)) {
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
    public List<T> getNodes() {
        List<T> output = new ArrayList<>(DesignMap.keySet());
        checkRep();
        return output;
    }

    /**
     * Lists all the edges from given source node to given destination node
     * @param src the source node of the edges
     * @param dst the destination node of the edges
     * @return list of all edges from src to dst
     */
    public List<E> getLabels(T src, T dst) {
        if (!this.contains(src) || !DesignMap.get(src).containsKey(dst)) {
            return new ArrayList<>();
        }
        List<E> output = new ArrayList<>(DesignMap.get(src).get(dst));
        checkRep();
        return output;
    }

    /**
     * Checks if this map contains node A
     * @param A the node to be checked in this
     * @return a boolean that is true if this contains A and false otherwise
     */
    public boolean contains(T A) {
        return DesignMap.containsKey(A);
    }

}
