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
    lines: string[][]
}

class App extends Component<{}, AppState> {

    constructor(props: {}) {
        super(props);
        this.state = {
            start: "Choose an option", end: "Choose an option", lines: []
        }
    }

    async sendRequest(startStr: string, endStr: string) {
        try {
            if (startStr !== "Choose an option" && endStr !== "Choose an option") {
                let start = startStr.substring(0, startStr.indexOf(" -"))
                let end = endStr.substring(0, endStr.indexOf(" -"))
                this.setState({start: start, end: end})
                let response = await fetch('http://localhost:4567/path?start='+ start +'&end=' + end)
                if (!response.ok) { alert("Input is invalid (fetch failed).") }
                let parsed = await response.json()
                let arrayOfLines = []
                for (let i = 0; i < parsed.path.length; i++) {
                    let line = []
                    line.push(parsed.path[i].start.x)
                    line.push(parsed.path[i].start.y)
                    line.push(parsed.path[i].end.x)
                    line.push(parsed.path[i].end.y)
                    arrayOfLines.push(line)
                }
                this.setState({lines: arrayOfLines})
            } else {
                this.setState({start: "Choose an option", end: "Choose an option", lines: []})
            }
        } catch (e) { alert("Input is invalid (to json failed).") }
    }

    render() {
        return (
            <div>
                <h1 id="appTitle">Campus Path Finder!</h1>
                <div className={"app"}>
                    <Map lines={this.state.lines}/>
                </div>
                <SearchSelection
                    onChange={(start, end) => { this.sendRequest(start, end) }}
                />
            </div>
        )
    }

}

export default App;
