package com.rohan.fivesquare;

/**
 * Created by Rohan on 25-Jul-16.
 */
public class VenueModel {

    private String name;
    private String category;
    private String distance;
    private String address;
    private String icon;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDistance() {
        return distance;
    }

    public String getAddress() {
        return address;
    }

    public String getIcon() {
        return icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
