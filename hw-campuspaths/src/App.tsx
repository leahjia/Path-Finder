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
import MapLine from "./MapLine";
import {latLng, LatLng, LatLngExpression} from "leaflet";
import {
    UW_LATITUDE,
    UW_LATITUDE_OFFSET,
    UW_LATITUDE_SCALE,
    UW_LONGITUDE,
    UW_LONGITUDE_OFFSET,
    UW_LONGITUDE_SCALE
} from "./Constants";

interface AppState {
    start: string,
    end: string,
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
            coordinatesStart: [0, 0], coordinatesEnd: [0, 0],
            start: "Choose an option", end: "Choose an option", lines: []
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

    // sendRequest(startStr: string, endStr: string) {
    //     if (startStr !== "Choose an option" && endStr !== "Choose an option") {
    //         let start = startStr.substring(0, startStr.indexOf(" -"))
    //         let end = endStr.substring(0, endStr.indexOf(" -"))
    //         this.setState({start: start, end: end},
    //             () => {
    //                 this.props.onFindPath(this.state.start, this.state.end)
    //             })
    //     }
    // }


    // // fetch the paths from start to end
    // async sendRequest(startStr: string, endStr: string) {
    //     try {
    //         if (startStr !== "Choose an option" && endStr !== "Choose an option") {
    //
    //             // extract short names
    //             let start = startStr.substring(0, startStr.indexOf(" -"))
    //             let end = endStr.substring(0, endStr.indexOf(" -"))
    //
    //             // change state and send request
    //             this.setState({start: start, end: end})
    //             let response = await fetch('http://localhost:4567/path?start=' + start + '&end=' + end)
    //             if (!response.ok) {
    //                 alert("Input is invalid (fetch failed).")
    //             }
    //             let parsed = await response.json()
    //
    //             // collect MapLines for map
    //             const arrayOfLines: JSX.Element[] = []
    //             for (let i = 0; i < parsed.path.length; i++) {
    //                 arrayOfLines.push(
    //                     <MapLine
    //                         x1={parsed.path[i].start.x}
    //                         y1={parsed.path[i].start.y}
    //                         x2={parsed.path[i].end.x}
    //                         y2={parsed.path[i].end.y}
    //                         color={"red"}
    //                         key={"Line #" + i}
    //                     ></MapLine>
    //                 )
    //             }
    //             this.setState({lines: arrayOfLines})
    //         } else {
    //             this.setState({lines: []})
    //         }
    //     } catch (e) {
    //         alert("Input is invalid (to json() failed).")
    //     }
    // }

    setList(list: JSX.Element[]) {
        this.setState({lines: list})
    }

    render() {
        return (
            <div>
                <h1 id="appTitle">Campus Path Finder!</h1>
                <div className={"app"}>
                    <Map coordinatesStart={this.state.coordinatesStart}
                         coordinatesEnd={this.state.coordinatesEnd}
                         lines={this.state.lines}/>
                </div>
                <SearchSelection
                    onChange={(start, end) => {
                        // this.sendRequest(start, end)
                    }}
                    onSearchList={(list) => this.setList(list)}
                    onSelectStart={(str) => this.putPinStart(str)}
                    onSelectEnd={(str) => this.putPinEnd(str)}
                />
            </div>
        )
    }

}

export default App;
