package com.jengger.studentportalappclient.api.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentAccountRequest {
    @SerializedName("studentAccount")
    @Expose
    private StudentAccount studentAccount;

    public StudentAccount getStudentAccount() {
    return studentAccount;
    }

    public void setStudentAccount(StudentAccount studentAccount) {
    this.studentAccount = studentAccount;
    }
}
