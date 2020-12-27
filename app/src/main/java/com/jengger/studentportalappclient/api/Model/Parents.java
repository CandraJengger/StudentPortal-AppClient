package com.jengger.studentportalappclient.api.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Parents implements Serializable {
    @SerializedName("nik_ortu")
    @Expose
    private String nikOrtu;
    @SerializedName("npm")
    @Expose
    private Integer npm;
    @SerializedName("nama_ortu")
    @Expose
    private String namaOrtu;
    @SerializedName("pendidikan_ortu")
    @Expose
    private String pendidikanOrtu;
    @SerializedName("pekerjaan_ortu")
    @Expose
    private String pekerjaanOrtu;
    @SerializedName("NIP_ortu")
    @Expose
    private String nIPOrtu;
    @SerializedName("pangkat_ortu")
    @Expose
    private String pangkatOrtu;
    @SerializedName("penghasilan_ortu")
    @Expose
    private String penghasilanOrtu;
    @SerializedName("instansi_ortu")
    @Expose
    private String instansiOrtu;
    @SerializedName("status_ortu")
    @Expose
    private String statusOrtu;
    @SerializedName("tanggal_lahir_ortu")
    @Expose
    private String tanggalLahirOrtu;

    public String getNikOrtu() {
        return nikOrtu;
    }

    public void setNikOrtu(String nikOrtu) {
        this.nikOrtu = nikOrtu;
    }

    public Integer getNpm() {
        return npm;
    }

    public void setNpm(Integer npm) {
        this.npm = npm;
    }

    public String getNamaOrtu() {
        return namaOrtu;
    }

    public void setNamaOrtu(String namaOrtu) {
        this.namaOrtu = namaOrtu;
    }

    public String getPendidikanOrtu() {
        return pendidikanOrtu;
    }

    public void setPendidikanOrtu(String pendidikanOrtu) {
        this.pendidikanOrtu = pendidikanOrtu;
    }

    public String getPekerjaanOrtu() {
        return pekerjaanOrtu;
    }

    public void setPekerjaanOrtu(String pekerjaanOrtu) {
        this.pekerjaanOrtu = pekerjaanOrtu;
    }

    public String getNIPOrtu() {
        return nIPOrtu;
    }

    public void setNIPOrtu(String nIPOrtu) {
        this.nIPOrtu = nIPOrtu;
    }

    public String getPangkatOrtu() {
        return pangkatOrtu;
    }

    public void setPangkatOrtu(String pangkatOrtu) {
        this.pangkatOrtu = pangkatOrtu;
    }

    public String getPenghasilanOrtu() {
        return penghasilanOrtu;
    }

    public void setPenghasilanOrtu(String penghasilanOrtu) {
        this.penghasilanOrtu = penghasilanOrtu;
    }

    public String getInstansiOrtu() {
        return instansiOrtu;
    }

    public void setInstansiOrtu(String instansiOrtu) {
        this.instansiOrtu = instansiOrtu;
    }

    public String getStatusOrtu() {
        return statusOrtu;
    }

    public void setStatusOrtu(String statusOrtu) {
        this.statusOrtu = statusOrtu;
    }

    public String getTanggalLahirOrtu() {
        return tanggalLahirOrtu;
    }

    public void setTanggalLahirOrtu(String tanggalLahirOrtu) {
        this.tanggalLahirOrtu = tanggalLahirOrtu;
    }
}
