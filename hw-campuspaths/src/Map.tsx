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

import { LatLngExpression } from "leaflet";
import React, { Component } from "react";
import {MapContainer, TileLayer} from "react-leaflet";
import "leaflet/dist/leaflet.css";
import MapLine from "./MapLine";
import { UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER } from "./Constants";

// coordinates of the UW Seattle campus
let position: LatLngExpression = [UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER];

interface MapProps {
    lines: string[][]
}

class Map extends Component<MapProps, {}> {

    render() {
        const paths = this.props.lines
        const arrayOfLines: JSX.Element[] = []
        for (let i = 0; i < paths.length; i++) {
            arrayOfLines.push(
                <MapLine
                    x1={Number(paths[i][0])}
                    y1={Number(paths[i][1])}
                    x2={Number(paths[i][2])}
                    y2={Number(paths[i][3])}
                    color={"red"}
                    key={"Line #" + i}
                ></MapLine>
            )
        }

        return (
            <div id="map">
                <MapContainer
                    center={position}
                    zoom={15}
                    doubleClickZoom={true}
                    scrollWheelZoom={false}>
                    <TileLayer
                        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"/>
                    { <div>{arrayOfLines}</div> }
                </MapContainer>
            </div>
        );
    }
}

export default Map;
