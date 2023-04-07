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

import React, {Component} from 'react'

interface EdgeListProps {
    onChange: (edges: string) => void // called when a new edge list is ready
    onClear: () => any
    onQuestion: () => any
}

interface EdgeListState { inputText: string }

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {

    constructor(props: EdgeListProps) {
        super(props)
        this.state = {inputText: "I'm stuck..."}
    }

    textChange = (evt: any) => this.setState({ inputText: evt.target.value })

    resetText = (evt: any) => {
        this.setState({ inputText: evt})
        this.props.onClear()
    }

    render() {
        return (
            <h2 id="edge-list">
                Enter edge(s) here:
                <button onClick={() => this.props.onQuestion()}>?</button>
                <br/>
                <textarea
                    rows={5}
                    cols={36}
                    style={{
                        color: "darkolivegreen",
                        backgroundColor: "seashell",
                        fontFamily: "Copperplate",
                        fontSize: 20,
                        border: "double"
                }}
                    value={this.state.inputText}
                    onChange={(evt) => { this.textChange(evt) }}
                /> <br/>
                <button onClick={() => this.props.onChange(this.state.inputText)}>Draw</button>
                <button onClick={() => this.resetText("I'm stuck...")}>Clear</button>
            </h2>
        )
    }
}

export default EdgeList
