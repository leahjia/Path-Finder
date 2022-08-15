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
import spark.*;

import java.util.ArrayList;
import java.util.List;

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
        Spark.get("setup", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                String param = request.queryParams("param");
                if (param == null) { param = "STRANGER"; }
                return "<h1>SETUP TEST: " + param + "!";
            }
        });

        Spark.get("numbers", new Route() {
            @Override
            public Object handle(Request request, Response response) {

                // check params are entered
                String startStr = request.queryParams("start");
                String endStr = request.queryParams("end");
                if (startStr == null || endStr == null) {
                    Spark.halt(400, "<h1>null start/end");
                }

                // check params are numbers
                int start = 0, end = 0;
                try {
                    start = Integer.parseInt(startStr);
                    end = Integer.parseInt(endStr);
                } catch (NumberFormatException e) {
                    Spark.halt(400, "<h1>non-number start/end");
                }
//                CampusMap newMap = new CampusMap();
                List<Integer> numbers = new ArrayList<>();
                numbers.add(start);
                numbers.add(end);
                String output = new Gson().toJson(numbers);
                return "<h1>RANGE: " + output + " !";
            }
        });

    }

}
