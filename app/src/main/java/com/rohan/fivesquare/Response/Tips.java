package com.rohan.fivesquare.Response;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tips {

    @SerializedName("count")
    @Expose
    private Long count;
    @SerializedName("groups")
    @Expose
    private List<Group> groups = new ArrayList<Group>();

    /**
     * @return The count
     */
    public Long getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(Long count) {
        this.count = count;
    }

    /**
     * @return The groups
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * @param groups The groups
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

}