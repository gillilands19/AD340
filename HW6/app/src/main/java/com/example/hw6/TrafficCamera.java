package com.example.hw6;

public class TrafficCamera {
    private String url;
    private String location;
    private String type;

    public TrafficCamera (String description, String url, String type) {
        this.type = type;
        this.location = description;
        this.url = buildImageURL(url);
    }

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

    public String getLocation() {
        return this.location;
    }

}
