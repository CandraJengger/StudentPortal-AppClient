package com.jengger.studentportalappclient.api.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentAccount {
    @SerializedName("npm")
    @Expose
    private Integer npm;
    @SerializedName("password_mhs")
    @Expose
    private String passwordMhs;
    @SerializedName("status_mhs")
    @Expose
    private String statusMhs;

    public Integer getNpm() {
    return npm;
    }

    public void setNpm(Integer npm) {
    this.npm = npm;
    }

    public String getPasswordMhs() {
    return passwordMhs;
    }

    public void setPasswordMhs(String passwordMhs) {
    this.passwordMhs = passwordMhs;
    }

    public String getStatusMhs() {
    return statusMhs;
    }

    public void setStatusMhs(String statusMhs) {
    this.statusMhs = statusMhs;
    }
}
