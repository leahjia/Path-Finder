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

package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SparkServer {
    
    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().
        
        // TODO: Create all the Spark Java routes you need here.
        // spec: you probably want to create an instance of CampusMap that
        //       you can use to fulfill requests that are sent to your server
        //       you should not create a new CampusMap each time your server receives a request
        //       shouldn't do much more than contain a main method and define different routes in your app
        
        final CampusMap map = new CampusMap();
        
        Spark.get("path", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // http://localhost:4567/path?start=KNE&end=HUB
                List<String> arrayOfLines = new ArrayList<>();
                try {
                    Path<Point> pathFound = map.findShortestPath(request.queryParams("start"),
                            request.queryParams("end"));
                    if (pathFound != null) {
                        for (Path<Point>.Segment seg : pathFound) {
                            StringBuilder eachLine = new StringBuilder();
                            eachLine.append(seg.getStart().getX() + ",");
                            eachLine.append(seg.getStart().getY() + ",");
                            eachLine.append(seg.getEnd().getX() + ",");
                            eachLine.append(seg.getEnd().getY());
                            arrayOfLines.add(eachLine.toString());
                        }
                    }
                    return arrayOfLines;
//                    return pathFound;
                } catch (Exception e) {
                    Spark.halt(400, "<h1>Invalid input: start/end is null or not a number");
                }
                return "There is no path:(";
            }
        });
        
        Spark.get("names", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                Map<String, String> names = map.buildingNames();
                return new Gson().toJson(names);
            }
        });
        
    }
    
}
