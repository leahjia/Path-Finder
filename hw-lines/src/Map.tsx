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

import {LatLngExpression} from "leaflet"
import React, {Component} from "react"
import {MapContainer, Marker, Popup, TileLayer} from "react-leaflet"
import "leaflet/dist/leaflet.css"
import MapLine from "./MapLine"
import {UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER} from "./Constants"
import markerIconPng from "leaflet/dist/images/marker-icon.png"
import {Icon} from 'leaflet'


// This defines the location of the map. These are the coordinates of the UW Seattle campus
const position: LatLngExpression = [UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER]

interface MapProps {
    // DONE: Define the props of this component. You will want to pass down edges
    //   so you can render them here
    edgeList: string[]
}

interface MapState {}

class Map extends Component<MapProps, MapState> {

    constructor(props: MapProps) {
        super(props)
        this.state = {
        }
    }

    render() {
        const arrayOfLines: JSX.Element[] = []
        for (let i = 0; i < this.props.edgeList.length; i++) {
            let eachLine = this.props.edgeList[i]
            let elements = eachLine.split(" ")
            arrayOfLines.push(
                <MapLine
                    color={elements[4]}
                    x1={Number(elements[0])}
                    y1={Number(elements[1])}
                    x2={Number(elements[2])}
                    y2={Number(elements[3])}
                    key={"Line #" + i}
                    // strokeDashoffset={spring[0].value}
                    // strokeDasharray={length}
                    // strokeWidth={6}
                ></MapLine>
            )
        }

        return (
            <div id="map" >
                <MapContainer
                    center={position}
                    zoom={15}
                    scrollWheelZoom={true}
                >
                    <TileLayer
                        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    />
                    {
                        // DONE: Render map lines here using the MapLine component.
                        <div>{arrayOfLines}</div>
                    }
                    <Marker
                        position={position}
                        draggable={true}
                        title={"hover text for marker"}
                        icon={new Icon({
                            iconUrl: markerIconPng,
                            iconSize: [25, 40],
                            iconAnchor: [12.5, 40],
                            popupAnchor: [0, -40],
                        })}>
                        <marker>marker</marker>
                        <Popup><h2>Location of marker</h2></Popup>
                    </Marker>
                </MapContainer>
            </div>
        )
    }
}

export default Map
