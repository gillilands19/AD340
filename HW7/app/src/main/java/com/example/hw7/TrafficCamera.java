package com.example.hw7;

import android.os.Parcel;
import android.os.Parcelable;

public class TrafficCamera implements Parcelable {
    private String description;
    private String type;
    private String url;
    private Double latitude;
    private Double longitude;

    public TrafficCamera(String description, String url, String type, Double latitude, Double longitude) {
        this.description = description;
        this.type = type;
        this.url = buildImageURL(url);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public TrafficCamera(Parcel in) {
        this.description = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel destination, int flags) {
        destination.writeString(description);
        destination.writeString(type);
        destination.writeString(url);
        destination.writeDouble(latitude);
        destination.writeDouble(longitude);
    }

    public static final Parcelable.Creator<TrafficCamera> CREATOR = new Parcelable.Creator<TrafficCamera>() {
        public TrafficCamera createFromParcel(Parcel in) {
            return new TrafficCamera(in);
        }

        public TrafficCamera[] newArray(int size) {
            return new TrafficCamera[size];
        }
    };

    private String buildImageURL(String url) {

        String SDOTUrl = "http://www.seattle.gov/trafficcams/images/";
        String WSDOTUrl = "http://images.wsdot.wa.gov/nw/";

        if (type.equals("sdot")) {
            return SDOTUrl + url;
        } else if (type.equals("wsdot")) {
            return WSDOTUrl + url;
        }
        return null;
    }

    public String getUrl() {
        return this.url;
    }

    public String getDescription() {
        return this.description;
    }

    public Double getLatitude() { return this.latitude; }

    public Double getLongitude() { return this.longitude; }

    @Override
    public int hashCode() {
        return (this.getDescription()).hashCode();
    }

}
