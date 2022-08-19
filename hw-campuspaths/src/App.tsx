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
import React, {Component} from 'react';
import Map from "./Map"
import "./App.css";
import SearchSelection from "./SearchSelection";
import {latLng, LatLng, LatLngExpression} from "leaflet";
import { UW_LATITUDE, UW_LATITUDE_OFFSET, UW_LATITUDE_SCALE,
    UW_LONGITUDE, UW_LONGITUDE_OFFSET, UW_LONGITUDE_SCALE } from "./Constants";

interface AppState {
    lines: JSX.Element[],
    coordinatesStart: LatLngExpression,
    coordinatesEnd: LatLngExpression,
}

// Converts x coordinate to longitude and y coordinate to latitude
function toLatLon(y: number, x: number): LatLng {
    return latLng(UW_LATITUDE + (y - UW_LATITUDE_OFFSET) * UW_LATITUDE_SCALE,
        UW_LONGITUDE + (x - UW_LONGITUDE_OFFSET) * UW_LONGITUDE_SCALE)
}

class App extends Component<{}, AppState> {

    constructor(props: {}) {
        super(props);
        this.state = {
            coordinatesStart: [0, 0], coordinatesEnd: [0, 0], lines: []
        }
    }

    // check if selections are not empty or default
    checkPin(str: string) {
        return (str !== "" && str !== "Choose an option")
    }

    // fetch the name of the start point
    async putPinStart(str: string) {
        if (this.checkPin(str)) {
            let name = str.substring(0, str.indexOf(" -"))
            let response = await fetch('http://localhost:4567/path?start=' + name + '&end=' + name)
            if (!response.ok) {
                alert("Input is invalid (fetch failed).")
            }
            let parsed = await response.json()
            let newPin = parsed.start
            this.setState({coordinatesStart: toLatLon(newPin.y, newPin.x)})
        } else {
            this.setState({coordinatesStart: [0, 0]})
        }
    }

    // fetch the name of the end point
    async putPinEnd(str: string) {
        if (this.checkPin(str)) {
            let name = str.substring(0, str.indexOf(" -"))
            let response = await fetch('http://localhost:4567/path?start=' + name + '&end=' + name)
            if (!response.ok) {
                alert("Input is invalid (fetch failed).")
            }
            let parsed = await response.json()
            let newPin = parsed.start
            this.setState({coordinatesEnd: toLatLon(newPin.y, newPin.x)})
        } else {
            this.setState({coordinatesEnd: [0, 0]})
        }
    }

    setList(list: JSX.Element[]) {
        this.setState({lines: list})
    }

    render() {
        return (
            <div className={"app"}>
                <h1 id="appTitle">Campus Path Finder!</h1>
                <Map
                    coordinatesStart={this.state.coordinatesStart}
                    coordinatesEnd={this.state.coordinatesEnd}
                    lines={this.state.lines}
                />
                <SearchSelection
                    onSearchList={(list) => this.setList(list)}
                    onSelectStart={(str) => this.putPinStart(str)}
                    onSelectEnd={(str) => this.putPinEnd(str)}
                />
            </div>
        )
    }

}

export default App;
