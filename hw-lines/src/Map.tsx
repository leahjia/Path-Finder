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
import React, {Component, useState} from "react"
import {MapContainer, Marker, Popup, TileLayer, Tooltip, useMapEvents} from "react-leaflet"
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
                ></MapLine>
            )
        }

        function LocationMarker() {
            const [currPosition, setPosition] = useState(position)
            const map = useMapEvents({
                click() {
                    map.locate()
                },
                locationfound(e) {
                    setPosition(e.latlng)
                    map.flyTo(e.latlng, map.getZoom())
                },
            })

            return currPosition === null ? null : (
                <Marker position={currPosition}
                        // draggable={true}
                        icon={new Icon({
                            iconUrl: markerIconPng,
                            iconSize: [30, 50],
                            iconAnchor: [15, 50],
                            popupAnchor: [0, -50],
                        })}>
                    <Popup>
                        <h2>You are here.</h2>
                        <h3>Click the map to center back to your location.</h3>
                    </Popup>
                    <Tooltip>Click anywhere in the map to center at your location.</Tooltip>
                </Marker>
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
                    <LocationMarker />
                </MapContainer>
            </div>
        )
    }
}

export default Map
