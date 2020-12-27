package com.jengger.studentportalappclient.api.Model;

public class StatusMahasiswa {
    private String kodeSemester, kodeKelas, idProdi;

    public String getKodeSemester() {
        return kodeSemester;
    }

    public void setKodeSemester(String kodeSemester) {
        this.kodeSemester = kodeSemester;
    }

    public String getKodeKelas() {
        return kodeKelas;
    }

    public void setKodeKelas(String kodeKelas) {
        this.kodeKelas = kodeKelas;
    }

    public String getIdProdi() {
        return idProdi;
    }

    public void setIdProdi(String idProdi) {
        this.idProdi = idProdi;
    }

    public Integer getKodeAngkatan() {
        return kodeAngkatan;
    }

    public void setKodeAngkatan(Integer kodeAngkatan) {
        this.kodeAngkatan = kodeAngkatan;
    }

    public Integer getNpm() {
        return npm;
    }

    public void setNpm(Integer npm) {
        this.npm = npm;
    }

    private Integer kodeAngkatan, npm;
}
