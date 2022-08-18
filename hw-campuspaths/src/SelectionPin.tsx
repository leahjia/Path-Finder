import {Icon, LatLng, latLng, LatLngExpression} from "leaflet";
import React, {Component} from "react";
import {Marker, Popup, Tooltip} from "react-leaflet";
import "leaflet/dist/leaflet.css";
import {
    UW_LATITUDE, UW_LATITUDE_CENTER,
    UW_LATITUDE_OFFSET, UW_LATITUDE_SCALE,
    UW_LONGITUDE, UW_LONGITUDE_CENTER,
    UW_LONGITUDE_OFFSET,
    UW_LONGITUDE_SCALE
} from "./Constants";
import markerPinIcon from "leaflet/dist/images/marker-icon.png"
import markerPinShadow from "leaflet/dist/images/marker-shadow.png"

interface MarkerPinProps {
    position: LatLngExpression,
}

// Converts x coordinate to longitude and y coordinate to latitude
function toLatLon(y: number, x: number): LatLng {
    return latLng(UW_LATITUDE + (y - UW_LATITUDE_OFFSET) * UW_LATITUDE_SCALE,
        UW_LONGITUDE + (x - UW_LONGITUDE_OFFSET) * UW_LONGITUDE_SCALE)
}

class MarkerPin extends Component<MarkerPinProps, {}> {

    render() {
        const position = this.props.position
        console.log("position: ", latLng(position).lat)
        return (
            <Marker position={position}
                    icon={new Icon({
                        iconUrl: markerPinIcon,
                        shadowUrl: markerPinShadow,
                        iconSize: [25, 40],
                        iconAnchor: [12.5, 40],
                        popupAnchor: [0, -40],
                    })}>
                <Popup><h3>testing</h3></Popup>
                <Tooltip>You are here</Tooltip>
            </Marker>
        )
    }
}

export default MarkerPin;
