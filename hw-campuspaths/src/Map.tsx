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
import markerIcon from "leaflet/dist/images/marker-icon-2x.png"
import markerShadow from "leaflet/dist/images/marker-shadow.png"
import {
    UW_LATITUDE,
    UW_LATITUDE_CENTER,
    UW_LATITUDE_OFFSET,
    UW_LATITUDE_SCALE,
    UW_LONGITUDE,
    UW_LONGITUDE_CENTER,
    UW_LONGITUDE_OFFSET,
    UW_LONGITUDE_SCALE
} from "./Constants";
import StartMarker from "./StartMarker";
import EndMarker from "./EndMarker";

// coordinates of the UW Seattle campus
let defaultPosition: LatLngExpression = [UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER];

interface MapProps {
    mapLines: JSX.Element[],
    startPt: LatLngExpression,
    endPt: LatLngExpression,
}

// Converts x coordinate to longitude and y coordinate to latitude
function toLatLon(y: number, x: number): LatLng {
    return latLng(UW_LATITUDE + (y - UW_LATITUDE_OFFSET) * UW_LATITUDE_SCALE,
        UW_LONGITUDE + (x - UW_LONGITUDE_OFFSET) * UW_LONGITUDE_SCALE)
}

class Map extends Component<MapProps, {}> {

    render() {

        //calculate the mid-point of the path to later zoom in on
        const paths = this.props.mapLines
        let median = paths.length % 2 === 1 ? paths.length / 2 + .5 : paths.length / 2
        let newCenter = paths.length === 0 ? defaultPosition :
            toLatLon(Number(paths[median].props.y1), Number(paths[median].props.x1))

        // helper method to initiate zoom in
        function FlyToHelper() {
            if (newCenter !== defaultPosition) {
                FlyTo(newCenter, 4, 14)
            } else {
                FlyTo(defaultPosition, 1, 15)
            }
            return null
        }

        // feature to zoom in on the path found
        function FlyTo(center: LatLngExpression, scale: number, size: number) {
            const map = useMapEvents({
                mouseover() {
                    map.flyTo(center, map.getScaleZoom(scale, size))
                }
            })
            return null
        }

        // pin a marker on user's current location with a popup message
        function LocationMarker() {
            const [currPosition, setPosition] = useState(defaultPosition)
            const map = useMapEvents({
                mousemove() {
                    map.locate()
                },
                locationfound(e) {
                    setPosition(e.latlng)
                }
            })

            return currPosition === defaultPosition ?
                <Marker position={[0, 0]}></Marker> :
                <Marker position={currPosition}
                        icon={new Icon({
                            iconUrl: markerIcon,
                            shadowUrl: markerShadow,
                            iconSize: [25, 40],
                            iconAnchor: [12.5, 40],
                            popupAnchor: [0, -40],
                        })}>
                    <Popup><h3>This is your current location</h3></Popup>
                    <Tooltip>You are here</Tooltip>
                </Marker>
        }

        return (
            <div id="map">
                <MapContainer
                    id="container"
                    center={newCenter}
                    zoom={15}
                    doubleClickZoom={true}
                    scrollWheelZoom={true}>
                    <LocationMarker/>
                    <TileLayer
                        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"/>
                    <StartMarker position={this.props.startPt}/>
                    <EndMarker position={this.props.endPt}/>
                    <div>{this.props.mapLines}</div>
                    <FlyToHelper/>
                </MapContainer>
            </div>
        )
    }
}

export default Map;
