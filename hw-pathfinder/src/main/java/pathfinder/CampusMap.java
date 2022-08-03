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
        // DPME: Implement this method exactly as it is specified in ModelAPI
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
        List<CampusPath> paths = CampusPathsParser.parseCampusPaths("campus_paths.csv");
        Path<CampusBuilding> output = new Path<>(new CampusBuilding(startShortName,
                buildingNames().get(startShortName), paths.get(0).getX1(), paths.get(0).getY1()));

//        UnivMap<CampusBuilding> map = new UnivMap<>();
        for (CampusPath path: paths) {
//            CampusBuilding start = new CampusBuilding(startShortName,
//                    buildingNames().get(startShortName), path.getX1(), path.getY1());
//            CampusBuilding end = new CampusBuilding(endShortName,
//                    buildingNames().get(endShortName), path.getX2(), path.getY2());
            double startX = path.getX1();
            double startY = path.getX1();
        }
        return output;
    }

}
