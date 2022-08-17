import React, {Component} from 'react';
import Map from "./Map"
import MapLine from "./MapLine";

interface FetchListProps {
    start: string,
    end: string,
}

interface FetchListState {
    lines: JSX.Element[]
}

class FetchList extends Component<FetchListProps, FetchListState> {

    constructor(props: FetchListProps) {
        super(props);
        this.state = { lines: [] }
    }

    async sendRequest(start: string, end: string) {
        try {
            if (start !== "Choose an option" && end !== "Choose an option") {
                // extract short names
                // let start = startStr.substring(0, startStr.indexOf(" -"))
                // let end = endStr.substring(0, endStr.indexOf(" -"))

                // change state and send request
                console.log(this.props.end)
                let response = await fetch('http://localhost:4567/path?start='+ start +'&end=' + end)
                if (!response.ok) {
                    alert("Input is invalid (fetch failed).")
                }
                let parsed = await response.json()

                // change json into MapLines
                const arrayOfLines: JSX.Element[] = []
                for (let i = 0; i < parsed.path.length; i++) {
                    arrayOfLines.push(
                        <MapLine
                            x1={parsed.path[i].start.x}
                            y1={parsed.path[i].start.y}
                            x2={parsed.path[i].end.x}
                            y2={parsed.path[i].end.y}
                            color={"red"}
                            key={"Line #" + i}
                        ></MapLine>
                    )
                }

                this.setState({lines: arrayOfLines})
                console.log(arrayOfLines)
            } else {
                this.setState({ lines: []})
            }
        } catch (e) { alert("Input is invalid (to json failed).") }
    }

    render() {
        this.sendRequest(this.props.start, this.props.end)
        return (
            <div className={"FetchList"}>
                <Map lines={this.state.lines}/>
            </div>
        )
    }

}

export default FetchList;
