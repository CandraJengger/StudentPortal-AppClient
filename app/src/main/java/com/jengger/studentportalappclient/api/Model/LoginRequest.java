package com.jengger.studentportalappclient.api.Model;

public class LoginRequest {
    private Integer npm;
    private String password;

    public Integer getNpm() {
        return npm;
    }

    public void setNpm(Integer npm) {
        this.npm = npm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
