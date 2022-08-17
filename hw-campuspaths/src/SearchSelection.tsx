import React, {Component} from 'react'

interface SearchSelectionProps {
    onChange: (start: string, end: string) => void
}

interface SearchSelectionState {
    start: string, end: string, buildingNames: string[]
}

class SearchSelection extends Component<SearchSelectionProps, SearchSelectionState> {

    constructor(props: SearchSelectionProps) {
        super(props)
        this.state = {
            start: "Choose an option", end: "Choose an option", buildingNames: []
        }
    }

    // async sendRequest() {
    //     try {
    //         let response = await fetch('http://localhost:4567/names')
    //         if (!response.ok) { alert("Input is invalid #1.") }
    //         let parsed = await response.json()
    //         this.setState({ buildingNames: Object.keys(parsed).map(
    //                 function(key) { return key + " - " + parsed[key] })})
    //     } catch (e) { alert("Input is invalid.") }
    // }

    componentDidMount() {
        fetch('http://localhost:4567/names')
            .then(response => response.json())
            .then(parsed => this.setState({ buildingNames: Object.keys(parsed).map(
                    function(key) { return key + " - " + parsed[key] })}))
    }

    handleStartChange = (start: any) => this.setState({ start: start.target.value, })

    handleEndChange = (end: any) => this.setState({ end: end.target.value })

    handleClear = (msg: string) => {
        this.setState({ start: msg, end: msg })
        this.props.onChange(msg, msg)
    }

    render() {
        // this.sendRequest()
        return (
            <div id="dropdownList">
                <h3>Start from:
                    <select
                        value={this.state.start}
                        onChange={(start) => { this.handleStartChange(start) }}>
                        <option value={"startList"}>Choose an option</option>
                        {this.state.buildingNames.map((name) => ( <option key={name}>{name}</option> ))}
                    </select>
                </h3>
                <h3>To destination:
                    <select
                        value={this.state.end}
                        onChange={(end) => { this.handleEndChange(end) }}>
                        <option value={"endList"}>Choose an option</option>
                        {this.state.buildingNames.map((name) => ( <option key={name}>{name}</option> ))}
                    </select>
                </h3>
                <button onClick={() => this.props.onChange(this.state.start, this.state.end)}>Search</button>
                <button onClick={() =>
                    this.handleClear("Choose an option")}>Clear</button>
            </div>
        )
    }
}

export default SearchSelection
