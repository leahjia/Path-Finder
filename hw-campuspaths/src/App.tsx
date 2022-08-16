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

interface AppState {
    start: string,
    end: string,
    inputText: string,
    lines: string[],
}

// interface Path {
//     x1: string,
//     y1: string,
//     x2: string,
//     y2: string
// }

class App extends Component<{}, AppState> {
    constructor(props: {}) {
        super(props);
        this.state = {
            start: "",
            end: "",
            lines: [],
            inputText: ""
        }
    }

    async sendRequest(start: string, end: string) {
        // let startShortName = start.substring(0, start.indexOf(" "))
        // let endShortName = end.substring(0, end.indexOf(" "))
        try {
            // let response = await fetch('http://localhost:4567/path?start='+ startShortName +'&end=' + endShortName)
            let response = await fetch('http://localhost:4567/path?start=KNE&end=HUB')
            if (!response.ok) { alert("Input is invalid.") }
            // const parsed: Path = await response.json() as Path
            let parsed: string = await response.json()
            this.setState({inputText: parsed})
        } catch (e) { alert("Input is invalid.") }
    }

    addEdgeList(msg: string) {
        if (msg.length === 0) {
            this.setState({ lines: [] })
        } else {
            let lines: string[] = msg.toString().split(", ")
            let updatedLines = []
            for (let i = 0; i < lines.length; i+=4) {
                for (let j = 0; j < 4; j++) {
                    updatedLines.push(lines[i+j] + " " + lines[i+j] + " "  +  lines[i+j] + " "  + lines[i+j])
                }
            }
            this.setState({ lines: updatedLines})
        }
    }

    render() {
        return (
            <div>
                <h1 id="app-title">Campus Path Finder!</h1>
                <div>
                    <Map edgeList={this.state.lines} />
                </div>
                <SearchSelection
                    onChange={(start, end) => {
                        this.sendRequest(start, end)
                        console.log(this.state.inputText)
                        this.addEdgeList(this.state.inputText)
                    }}
                ></SearchSelection>
            </div>
        )
    }

}

export default App;
