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
import "./App.css"


interface AppState {
    lines: string[]
}

class App extends Component<{}, AppState> { // <- {} means no props.

    constructor(props: {}) {
        super(props)
        this.state = {
            // DONE: store edges in this state
            lines: []
        }
    }

    invalidInputCheck(msg: string) {
        let lines = msg.split('\n')
        for (let i = 0; i < lines.length; i++) {
            let elements = lines[i].split(" ")
            if (elements.length !== 5) {
                alert('Invalid input: expected "x1, y1, x2, y2, color",' +
                    ' and no extra space or empty lines.');
                return true
            }
            if (isNaN(Number(elements[0])) || isNaN(Number(elements[1])) ||
                isNaN(Number(elements[2])) || isNaN(Number(elements[3]))) {
                alert('Invalid input: please enter real numbers as coordinates')
                return true
            } else {
                let numbers = elements.slice(0, 4)
                numbers.forEach((el) => {
                    let num = Number(el)
                    if (num < 0 || num > 4000) {
                        alert('Invalid input: coordinates must be at least 0 and up to 4,000')
                        return true
                    }
                })
            }
        }
    }

    addEdgeList(msg: string) {
        if (msg.length === 0 || this.invalidInputCheck(msg)) {
            this.setState({ lines: [] })
        } else {
            let lines: string[] = msg.split('\n')
            let updatedLines = []
            for (let i = 0; i < lines.length; i++) {
                updatedLines.push(lines[i])
                this.setState({
                    lines: updatedLines,
                })
            }
        }
    }

    render() {
        return (
            <div style={{backgroundColor: "snow"}}>
                <h1 id="app-title">Line Mapper!</h1>
                <div>
                    {/* DONE: define props in the Map component and pass them in here */}
                    <Map edgeList={this.state.lines} />
                </div>
                <EdgeList
                    // DONE: Modify this onChange callback to store the edges in the state
                    onChange={(msg) => {this.addEdgeList(msg)}}
                    onClear={() => {this.addEdgeList("")}}
                    onQuestion={() => {alert("format: x1, y1, x2, y2, color")}}
                />
            </div>
        )
    }
}

export default App
