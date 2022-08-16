import React, { Component } from "react";
import "leaflet/dist/leaflet.css";

interface BuildingListProps { buildingList: string[] }

class BuildingList extends Component<BuildingListProps, {}> {
    render() {
        const arrayOfBuildings: Object[] = []
        const len = this.props.buildingList.length
        for (let i = 0; i < len; i++) {
            let eachBuilding = this.props.buildingList[i]
            arrayOfBuildings.push({ shortName: eachBuilding, longName: eachBuilding })
        }

        return (
            <div id="buildingList">{<div>{arrayOfBuildings}</div>}</div>
        );
    }
}

export default BuildingList;
