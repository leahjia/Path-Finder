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

import React, { Component } from "react"
import EdgeList from "./EdgeList"
import Map from "./Map"

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";
import MapLine from "./MapLine";

interface AppState {
    lines: (number | string) []
    keys: string[]
}

class App extends Component<{}, AppState> { // <- {} means no props.

    constructor(props: {}) {
        super(props)
        this.state = {
            // TODO: store edges in this state
            lines: [], keys: []
        }
    }

    invalidInputCheck(msg: string) {
        let lines = msg.split('\n')
        for (let i = 0; i < lines.length; i++) {
            let elements = lines[i].split(" ")
            if (elements.length !== 5 || isNaN(parseInt(elements[0])) ||
                isNaN(parseInt(elements[1])) || isNaN(parseInt(elements[2])) ||
                isNaN(parseInt(elements[3]))) {
                return true
            }
        }
    }

    addEdgeList(msg: string) {
        if (msg.length === 0 || this.invalidInputCheck(msg)) {
            this.setState({
                lines: [], keys: []
            })
        } else {
            let lines = msg.split('\n')
            for (let i = 0; i < lines.length; i++) {
                let nextLine = lines[i].split(" ")
                let line = [Number(nextLine[0]), Number(nextLine[1]),
                    Number(nextLine[2]), Number(nextLine[3]), nextLine[4]]
                this.setState({
                    lines: this.state.lines.concat(line),
                    keys: this.state.keys.concat("Line #", String(i))
                })
            }
        }
    }

    render() {
        return (
            <div>
                <h1 id="app-title">Line Mapper!</h1>
                <div>
                    {/* TODO: define props in the Map component and pass them in here */}
                    <Map
                        edgeList={this.state.lines}
                        keys={this.state.keys}
                    />
                    {/*<Map />*/}
                </div>
                <EdgeList
                    // TODO: Modify this onChange callback to store the edges in the state
                    onChange={(msg) => {this.addEdgeList(msg)}}
                    onClear={() => {this.addEdgeList("")}}
                />
            </div>
        )
    }
}

export default App
