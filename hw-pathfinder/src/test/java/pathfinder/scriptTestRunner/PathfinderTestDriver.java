/*
 * Copyright (C) 2022 Soham Pardeshi.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Summer Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder.scriptTestRunner;

import graph.UnivMap;
import pathfinder.datastructures.Path;

import java.io.*;
import java.util.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver<T> {

    /**
     * T -> Graph: maps the names of graphs to the actual graph
     **/
    private final Map<String, UnivMap<String>> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @spec.requires r != null && w != null
     * @spec.effects Creates a new PathfinderTestDriver which reads command from
     * {@code r} and writes results to {@code w}
     **/
    public PathfinderTestDriver(Reader r, Writer w) {
        // TODO: Implement this, reading commands from `r` and writing output to `w`.
        // See GraphTestDriver as an example.
        input = new BufferedReader(r);
        output = new PrintWriter(w);

    }

    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
    public void runTests() throws IOException {
        // TODO: Implement this.
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if(st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while(st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "FindPath":
                    FindPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }

    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName, new UnivMap<>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        UnivMap<String> map1 = graphs.get(graphName);
        map1.AddNode(nodeName);
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
//        double edgeLabel = Double.parseDouble(arguments.get(3));
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName, String weight) {
        UnivMap<String> map1 = graphs.get(graphName);
        map1.AddEdge(parentName, childName, weight);
        output.println("added edge " + weight + "00 from " + parentName +
                " to " + childName + " in " + graphName);
    }

    private void FindPath(List<String> arguments) {
        if(arguments.size() != 3) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }
        String graphName = arguments.get(0);
        String start = arguments.get(1);
        String end = arguments.get(2);
        FindPath(graphName, start, end);
    }

    class PathComparator implements Comparator<Path<String>>{
        public int compare(Path<String> A, Path<String> B) {
            if (A.getCost() < B.getCost())
                return -1;
            else if (A.getCost() > B.getCost() )
                return 1;
            return 0;
        }
    }

    private void FindPath(String graphName, String start, String dest) {
        UnivMap<String> map = graphs.get(graphName);
        // Each element is a path from start to a given node.
        // A path's “priority” in the queue is the total cost of that path.
        PriorityQueue<Path<String>> active = new PriorityQueue<>(new PathComparator());
        Set<String> finished = new HashSet<>();
        Path<String> initPath = new Path<>(start);
        initPath.extend(start, 0.0);
        active.add(initPath);
//        finished.add(start);
        Path<String> printOutput = initPath;
        while (!active.isEmpty()) {
            // next lowest-costing path
            Path<String> minPath = active.remove();
            // DEST of this path
            String minDest = minPath.getEnd();
            // SP found
            if (minDest.equals(dest)) {
                printOutput = minPath;
                break;
            }
            if (finished.contains(minDest)) {
                continue;
            }
            for (String e: map.ListChildren(minDest)) {
                // examine the path we've just found
                if (!finished.contains(e)) {
                    double newCost = Double.parseDouble(Collections.min(map.getLabels(minPath.getEnd(), e)));
                    Path<String> newPath = minPath.extend(e, newCost);
                    active.add(newPath);
                }
            }
            finished.add(minDest);
        }

        if (printOutput.equals(initPath)) {
            output.println("no path found.");
        } else {
            output.println("path from " + start + " to " + dest + ":");
            double totalCost = 0;
            for (Path<String>.Segment seg : printOutput) {
                totalCost += seg.getCost();
                output.println(seg.getStart() + " to " + seg.getEnd() + " with weight " +
                        String.format(" %.3f", seg.getCost()));
            }
            output.println("total cost: " + String.format(" %.3f", totalCost));
        }
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }

}
