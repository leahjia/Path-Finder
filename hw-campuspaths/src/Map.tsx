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

import {Icon, LatLng, latLng, LatLngExpression} from "leaflet";
import React, {Component, useState} from "react";
import {MapContainer, Marker, Popup, TileLayer, Tooltip, useMapEvents} from "react-leaflet";
import "leaflet/dist/leaflet.css";
import {
    UW_LATITUDE,
    UW_LATITUDE_CENTER, UW_LATITUDE_OFFSET, UW_LATITUDE_SCALE,
    UW_LONGITUDE,
    UW_LONGITUDE_CENTER,
    UW_LONGITUDE_OFFSET,
    UW_LONGITUDE_SCALE
} from "./Constants";
import markerIconPng from "leaflet/dist/images/marker-icon.png"

// coordinates of the UW Seattle campus
let position: LatLngExpression = [UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER];

interface MapProps {
    lines: JSX.Element[]
}

// Converts x coordinate to longitude and y coordinate to latitude
function toLatLon(y: number, x: number): LatLng {
    return latLng(UW_LATITUDE + (y - UW_LATITUDE_OFFSET) * UW_LATITUDE_SCALE,
        UW_LONGITUDE + (x - UW_LONGITUDE_OFFSET) * UW_LONGITUDE_SCALE)
}

class Map extends Component<MapProps, {}> {

    render() {
        const paths = this.props.lines
        let median = paths.length / 2
        median = paths.length % 2 === 1? median + .5 : median
        let newCenter = paths.length === 0? position : toLatLon(Number(paths[median].props.y1), Number(paths[median].props.x1))
        for (let i = 0; i < paths.length; i++) {
        }

        // helper method to initiate zoom in
        let prevCenter = position
        function FlyToHelper() {
            if (newCenter !== position && newCenter !== prevCenter) {
                FlyTo()
                prevCenter = newCenter
            }
            return null
        }

        // feature to zoom in on the path found
        function FlyTo() {
            const map = useMapEvents({
                mouseover() {
                    map.flyTo(newCenter, map.getScaleZoom(4, 14))
                }
            })
            return null
        }

        function LocationMarker() {
            const [currPosition, setPosition] = useState(position)
            const map = useMapEvents({
                mouseover() { map.locate() },
                locationfound(e) { setPosition(e.latlng) }
            })
            return currPosition === position ? (<Marker position={[0,0]}></Marker>) : (
                <Marker position={currPosition}
                        icon={new Icon({
                            iconUrl: markerIconPng,
                            iconSize: [25, 40],
                            iconAnchor: [12.5, 40],
                            popupAnchor: [0, -40],})}>
                    <Popup><h3>This is your current location</h3></Popup>
                    <Tooltip>You are here</Tooltip>
                </Marker>
            )
        }

        return (
            <div id="map">
                <MapContainer
                    id="container"
                    center={newCenter}
                    zoom={15}
                    doubleClickZoom={true}
                    scrollWheelZoom={true}>
                    <FlyToHelper/>
                    <LocationMarker/>
                    <TileLayer
                        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"/>
                    { <div>{this.props.lines}</div> }
                </MapContainer>
            </div>
        );
    }
}

export default Map;
