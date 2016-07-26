package com.rohan.fivesquare.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseDetails {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("response")
    @Expose
    private ResponseDetailsUseful response;

    /**
     * @return The meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     * @param meta The meta
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    /**
     * @return The response
     */
    public ResponseDetailsUseful getResponse() {
        return response;
    }

    /**
     * @param response The response
     */
    public void setResponse(ResponseDetailsUseful response) {
        this.response = response;
    }

}