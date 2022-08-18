import React, {Component} from 'react'

interface SearchSelectionProps {
    onChange: (start: string, end: string) => void
    onSelectStart: (str: string) => void
    onSelectEnd: (str: string) => void
}

interface SearchSelectionState {
    start: string,
    end: string,
    buildingNames: string[]
    lines: JSX.Element[],
    selectStart: string,
    selectEnd: string,
}

class SearchSelection extends Component<SearchSelectionProps, SearchSelectionState> {

    constructor(props: SearchSelectionProps) {
        super(props)
        this.state = {
            selectStart: "", selectEnd: "",
            start: "Choose an option", end: "Choose an option", buildingNames: [], lines: []
        }
    }

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


    handleStartChange = (start: any) => {
        this.setState({start: start.target.value,})
        this.setState({selectStart: start.target.value,},
            () => {
                this.props.onSelectStart(this.state.selectStart)
            })
    }

    handleEndChange = (end: any) => {
        this.setState({end: end.target.value})
        this.setState({selectEnd: end.target.value,}, () => {
                this.props.onSelectEnd(this.state.selectEnd)
            }
        )
    }

    handleClear = (msg: string) => {
        this.setState({start: msg, end: msg, lines: [], selectStart: "", selectEnd: ""},
            () => {
                this.props.onSelectStart(this.state.selectStart)
                this.props.onSelectEnd(this.state.selectEnd)
            })
        this.props.onChange(msg, msg)
    }

    render() {
        return (
            <div>
                <h3 id="root">Start from:
                    <select id="dropdownList"
                            value={this.state.start}
                            onChange={(start) => {
                                this.handleStartChange(start)
                            }}>
                        <option id="dropdownItems">Choose an option</option>
                        {this.state.buildingNames.map((name) => (<option key={name}>{name}</option>))}
                    </select>
                </h3>
                <h3>To destination:
                    <select id="dropdownList"
                            value={this.state.end}
                            onChange={(end) => {
                                this.handleEndChange(end)
                            }}>
                        <option id="dropdownItems">Choose an option</option>
                        {this.state.buildingNames.map((name) => (<option key={name}>{name}</option>))}
                    </select>
                </h3>
                <button
                    style={{
                        blockSize: 40,
                        borderRadius: 10,
                        fontSize: 16,
                        backgroundColor: 'SaddleBrown',
                        color: 'white',
                    }}
                    id="searchButton"
                    onClick={() => this.props.onChange(this.state.start, this.state.end)}>Search
                </button>
                <symbol></symbol>
                <button
                    style={{
                        blockSize: 40,
                        borderRadius: 10,
                        fontSize: 16,
                        backgroundColor: 'SaddleBrown',
                        color: 'white',
                    }}
                    id="searchButton"
                    onClick={() => this.handleClear("Choose an option")}>Clear
                </button>
            </div>
        )
    }
}

export default SearchSelection
