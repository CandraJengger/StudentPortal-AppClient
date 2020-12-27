package com.jengger.studentportalappclient.api.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParentRequest {
    @SerializedName("parents")
    @Expose
    private Parents parents;

    public Parents getParents() {
        return parents;
    }

    public void setParents(Parents parents) {
        this.parents = parents;
    }
}
