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

package pathfinder;

import graph.DesignMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CampusMap<T> implements ModelAPI {

    @Override
    public boolean shortNameExists(String shortName) {
        // DONE: Implement this method exactly as it is specified in ModelAPI
        return buildingNames().containsKey(shortName);
    }

    @Override
    public String longNameForShort(String shortName) {
        // DONE: Implement this method exactly as it is specified in ModelAPI
        if (!shortNameExists(shortName)) {
            throw new IllegalArgumentException("short name provided does not exist");
        } else {
            return buildingNames().get(shortName);
        }
    }

    @Override
    public Map<String, String> buildingNames() {
        // DONE: Implement this method exactly as it is specified in ModelAPI
        Map<String, String> names = new HashMap<>();
        List<CampusBuilding> buildings = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
        for (CampusBuilding building: buildings) {
            names.put(building.getShortName(), building.getLongName());
        }
        return names;
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        // DONE: Implement this method exactly as it is specified in ModelAPI
        if (!shortNameExists(startShortName) || !shortNameExists(endShortName)) {
            throw new IllegalArgumentException("Not valid short names of buildings");
        }
        List<CampusBuilding> buildings =
                CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
        List<CampusPath> paths = CampusPathsParser.parseCampusPaths("campus_paths.csv");

        // adds all nodes to map, and tags the start and end buildings
        DesignMap<Point, Double> map = new DesignMap<>();
        Point startBuilding = null;
        Point endBuilding = null;
        for (CampusBuilding building: buildings) {
            Point newBuilding = new Point(building.getX(), building.getY());
            map.AddNode(newBuilding);
            if (building.getShortName().equals(startShortName)) {
                startBuilding = newBuilding;
            }
            if (building.getShortName().equals(endShortName)) {
                endBuilding = newBuilding;
            }
        }

        // checks if either start or end building is still null
        if (startBuilding == null && endBuilding == null) {
            throw new IllegalArgumentException("start or end are null");
        }

        // adds all edges to map
        for (CampusPath path : paths) {
            Point startPoint = new Point(path.getX1(), path.getY1());
            Point endPoint = new Point(path.getX2(), path.getY2());
            double weight = path.getDistance();
            map.AddEdge(startPoint, endPoint, weight);
        }

        DijkstraPathFinder<Point, Double> finder = new DijkstraPathFinder<>();
        return finder.DijkstraPath(map, startBuilding, endBuilding);
    }

}
