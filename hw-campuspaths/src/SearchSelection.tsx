import React, {Component} from 'react'

interface SearchSelectionProps {
    onChange: (start: string, end: string) => void
    onSelectStart: (str: string) => void
    onSelectEnd: (str: string) => void
}

interface SearchSelectionState {
    start: string,
    end: string,
    buildingNames: string[],
    lines: JSX.Element[],
    selectStart: string,
    selectEnd: string,
}

class SearchSelection extends Component<SearchSelectionProps, SearchSelectionState> {

    constructor(props: SearchSelectionProps) {
        super(props)
        this.state = {
            start: "Choose an option",
            end: "Choose an option",
            buildingNames: [],
            lines: [],
            selectStart: "",
            selectEnd: "",
        }
    }

    // fetch all building names for dropfown list
    componentDidMount() {
        fetch('http://localhost:4567/names')
            .then(response => response.json())
            .then(parsed => this.setState({
                buildingNames: (Object.keys(parsed).map(
                    function (key) {
                        return key + " - " + parsed[key]
                    })).sort()
            }))
    }

    // handle change of start string and start marker
    handleStartChange = (start: any) => {
        this.setState({start: start.target.value,})
        this.setState({selectStart: start.target.value,},
            () => {
                this.props.onSelectStart(this.state.selectStart)
            })
    }

    // handle change of end string and end marker
    handleEndChange = (end: any) => {
        this.setState({end: end.target.value})
        this.setState({selectEnd: end.target.value,}, () => {
                this.props.onSelectEnd(this.state.selectEnd)
            }
        )
    }

    // clear all stored routes, markers, and messages
    handleClear = (init: string) => {
        this.setState({start: init, end: init, lines: [], selectStart: "", selectEnd: ""},
            () => {
                this.props.onSelectStart(this.state.selectStart)
                this.props.onSelectEnd(this.state.selectEnd)
            })
        this.props.onChange(init, init)
    }

    render() {
        return (
            <div>
                <h3 id="prompt">Start from:
                    <select id="dropdownList"
                            value={this.state.start}
                            onChange={(start) => {
                                this.handleStartChange(start)
                            }}>
                        <option id="dropdownItems">Choose an option</option>
                        {this.state.buildingNames.map((name) => (<option key={name}>{name}</option>))}
                    </select>
                </h3>
                <h3 id="prompt">To destination:
                    <select id="dropdownList"
                            value={this.state.end}
                            onChange={(end) => {
                                this.handleEndChange(end)
                            }}>
                        <option id="dropdownItems">Choose an option</option>
                        {this.state.buildingNames.map((name) => (<option key={name}>{name}</option>))}
                    </select>
                </h3>
                <h3 id="prompt">
                    <button id="buttons"
                            onClick={() => this.props.onChange(this.state.start, this.state.end)}>Search
                    </button>
                    <button id="buttons"
                            onClick={() => this.handleClear("Choose an option")}>Clear
                    </button>
                </h3>
            </div>
        )
    }
}

export default SearchSelection
