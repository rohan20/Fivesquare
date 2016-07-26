package com.rohan.fivesquare.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("response")
    @Expose
    private ResponseUseful response;

    /**
     *
     * @return
     * The meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     *
     * @param meta
     * The meta
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    /**
     *
     * @return
     * The response
     */
    public ResponseUseful getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(ResponseUseful response) {
        this.response = response;
    }

}