import React, {Component} from 'react'

interface SearchSelectionProps {
    onChange: (start: string, end: string) => void
}

interface SearchSelectionState {
    start: string
    end: string
    names: string[]
}


class SearchSelection extends Component<SearchSelectionProps, SearchSelectionState> {

    constructor(props: SearchSelectionProps) {
        super(props)
        this.state = {
            start: "select",
            end: "select",
            names: []
        }
    }

    // async names() {
    //     let response = await fetch('http://localhost:4567/names')
    //     if (!response.ok) { alert("Input is not ok.") }
    //     // const parsed: Path = await response.json() as Path
    //     const parsed = await response.json()
    //     this.setState({names: parsed})
    //     // return parsed
    // }

    componentDidMount() {
        fetch('http://localhost:4567/names')
            .then(response => response.json())
            .then(json => this.setState({
                        names: Object.keys(json).map(
                            function(k) {
                                return k + " - " + json[k]
                            }
                        )
                    }
                )
            )
    }

    handleStartChange = (start: any) => this.setState({ start: start.target.value, })

    handleEndChange = (end: any) => this.setState({ end: end.target.value })

    handleClear = (start: any, end: any) => { this.setState({
        start: start,
        end: end
    }) }

    render() {
        return (
            <div id="searchBar">
                <h3>Start from:
                    <select
                        value={this.state.start}
                        onChange={(start) => { this.handleStartChange(start) }}>
                        {this.state.names.map((name) => (
                            <option key={name}>{name}</option>))}
                    </select>
                </h3>
                <h3>To destination:
                    <select
                        value={this.state.end}
                        onChange={(end) => { this.handleEndChange(end) }}>
                        {this.state.names.map((name) => (
                            <option key={name}>{name}</option>))}
                    </select>
                </h3>
                <button onClick={() => this.props.onChange(this.state.start, this.state.end)}>Search</button>
                <button onClick={() => this.handleClear("select", "select")}>Clear</button>
            </div>
        )
    }
}

export default SearchSelection
