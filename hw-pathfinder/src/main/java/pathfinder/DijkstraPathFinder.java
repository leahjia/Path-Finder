package pathfinder;

import graph.DesignMap;
import pathfinder.datastructures.Path;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class DijkstraPathFinder<T, E> {

    class PathComparator implements Comparator<Path<T>> {
        public int compare(Path<T> A, Path<T> B) {
            if (A.getCost() <= B.getCost())
                return -1;
            else if (A.getCost() > B.getCost() )
                return 1;
            return 0;
        }
    }

    public Path<T> DijkstraPath(DesignMap<T, E> map, T start, T dest) {
        // Each element is a path from start to a given node.
        // A path's “priority” in the queue is the total cost of that path.
        PriorityQueue<Path<T>> pq = new PriorityQueue<>(new PathComparator());
        Set<T> known = new HashSet<>();
        Path<T> initPath = new Path<>(start);
        initPath.extend(start, 0.0);
        pq.add(initPath);
        Path<T> paths = initPath;
        if (start.equals(dest)) {
            return paths;
        }
        while (!pq.isEmpty()) {
            // next lowest-costing path
            Path<T> currPath = pq.remove();
            // DEST of this path
            T edgeTo = currPath.getEnd();
            // SP found
            if (edgeTo.equals(dest)) {
                paths = currPath;
                break;
            }
            if (!known.contains(edgeTo)) {
                for (T child : map.listChildren(edgeTo)) {
                    if (!known.contains(child)) {
                        String minCost = map.getLabels(edgeTo, child).get(0).toString();
                        double newCost = Double.parseDouble(minCost);
                        Path<T> newPath = currPath.extend(child, newCost);
                        pq.add(newPath);
                    }
                }
                known.add(edgeTo);
            }
        }
        if (paths.equals(initPath)) {
            return null;
        } else {
            return paths;
        }
    }
}
