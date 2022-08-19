import {Icon, LatLngExpression} from "leaflet";
import React, {Component} from "react";
import {Marker, Popup, Tooltip} from "react-leaflet";
import "leaflet/dist/leaflet.css";
import markerPinIcon from "leaflet/dist/images/marker-icon.png"
import markerPinShadow from "leaflet/dist/images/marker-shadow.png"

interface EndMarkerProps {
    position: LatLngExpression,
}

class EndMarker extends Component<EndMarkerProps, {}> {

    render() {
        return (
            <Marker
                position={this.props.position}
                icon={new Icon({
                    iconUrl: markerPinIcon,
                    shadowUrl: markerPinShadow,
                    iconSize: [25, 40],
                    iconAnchor: [12.5, 40],
                    popupAnchor: [0, -40],
                })}>
                <Popup><h3>DESTINATION</h3></Popup>
                <Tooltip>END POINT</Tooltip>
            </Marker>
        )
    }
}

export default EndMarker;
