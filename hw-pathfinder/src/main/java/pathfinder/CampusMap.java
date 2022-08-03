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

import graph.UnivMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CampusMap<T> implements ModelAPI<T> {

    @Override
    public boolean shortNameExists(String shortName) {
        // DONE: Implement this method exactly as it is specified in ModelAPI
        return buildingNames().containsKey(shortName);
    }

    @Override
    public String longNameForShort(String shortName) {
        // DONE: Implement this method exactly as it is specified in ModelAPI
        return buildingNames().get(shortName);
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
    public Path<CampusBuilding> findShortestPath(String startShortName, String endShortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        List<CampusBuilding> buildings = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
        UnivMap<CampusBuilding, Double> map = new UnivMap<>();
        CampusBuilding startBuilding = null;
        CampusBuilding endBuilding = null;
        for (CampusBuilding building: buildings) {
            map.AddNode(building);
            if (building.getShortName().equals(startShortName)) {
                startBuilding = building;
            } else if (building.getShortName().equals(endShortName)) {
                endBuilding = building;
            }
        }

        List<CampusPath> paths = CampusPathsParser.parseCampusPaths("campus_paths.csv");
        for (CampusPath path : paths) {
            double pathX1 = path.getX1();
            double pathX2 = path.getX2();
            double pathY1 = path.getY1();
            double pathY2 = path.getY2();
            // initially assume both endpoints are non-CampusBuildings
            CampusBuilding start = new CampusBuilding(null, null, pathX1, pathY1);
            CampusBuilding end = new CampusBuilding(null, null, pathX2, pathY2);
            for (CampusBuilding building: map.getNodes()) {
                double buildX = building.getX();
                double buildY = building.getY();
                String shortName = building.getShortName();
                String longName = building.getLongName();
                if (buildX == pathX1 && buildY == pathY1) {
                    // start building matched
                    start = new CampusBuilding(shortName, longName, pathX1, pathY1);
                } else if (buildX == pathX2 && buildY == pathY2) {
                    end = new CampusBuilding(shortName, longName, pathX2, pathY2);
                }
            }
            double weight = path.getDistance();
            map.AddEdge(start, end, weight);
        }

        if (startBuilding != null && endBuilding != null) {
            DijkstraPathFinder<CampusBuilding, Double> finder = new DijkstraPathFinder<>();
            return finder.DijkstraPath(map, startBuilding, endBuilding);
        } else {
            throw new IllegalArgumentException("start/end are null, or not valid short names of buildings");
        }
    }

}
