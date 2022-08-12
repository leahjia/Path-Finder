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
import { MapContainer, TileLayer } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import MapLine from "./MapLine";
import { UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER } from "./Constants";

// This defines the location of the map. These are the coordinates of the UW Seattle campus
const position: LatLngExpression = [UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER];

interface MapProps {
    // TODO: Define the props of this component. You will want to pass down edges
    //   so you can render them here
    edgeList: number[]
    color: string[]
    keys: string[]
}

interface MapState {}

class Map extends Component<MapProps, MapState> {

    constructor(props: MapProps) {
        super(props);
        this.state = {
            edgeList: [],
            color: [],
            keys: []
        }
    }

    render() {
        if (this.props.edgeList === undefined) {
            return (
                <div id="map">
                    <MapContainer
                        center={position}
                        zoom={15}
                        scrollWheelZoom={false}
                    >
                        <TileLayer
                            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        />
                        {<MapLine color={""} x1={0} y1={0} x2={0} y2={0} key={""}/>}
                    </MapContainer>
                </div>)
        } else {
            return (
                <div id="map">
                    <MapContainer
                        center={position}
                        zoom={15}
                        scrollWheelZoom={false}
                    >
                        <TileLayer
                            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        />
                        {
                            // TODO: Render map lines here using the MapLine component. E.g.
                            // <MapLine key={key1} color="red" x1={1000} y1={1000} x2={2000} y2={2000}/>
                            // will draw a red line from the point 1000,1000 to 2000,2000 on the map
                            [<MapLine
                                color={'red'}
                                x1={1000}
                                y1={1000}
                                x2={1000}
                                y2={1500}
                                key={"whatever"}
                            />]
                            // [<MapLine
                            //     key={this.props.keys[0]}
                            //     color={this.props.color[0]}
                            //     x1={this.props.edgeList[0]}
                            //     y1={this.props.edgeList[1]}
                            //     x2={this.props.edgeList[2]}
                            //     y2={this.props.edgeList[3]}
                            // />]
                            // [<MapLine
                            //     color={this.props.edgeList[0].color}
                            //     x1={this.props.edgeList[0].x1}
                            //     y1={this.props.edgeList[0].y1}
                            //     x2={this.props.edgeList[0].x2}
                            //     y2={this.props.edgeList[0].y2}
                            //     key={this.props.edgeList[0].key}
                            // />]
                        }
                    </MapContainer>
                </div>
            )
        }
    }
}

export default Map;
