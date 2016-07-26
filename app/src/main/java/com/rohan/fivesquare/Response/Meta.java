package com.rohan.fivesquare.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("code")
    @Expose
    private Long code;
    @SerializedName("requestId")
    @Expose
    private String requestId;

    /**
     * @return The code
     */
    public Long getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(Long code) {
        this.code = code;
    }

    /**
     * @return The requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @param requestId The requestId
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}