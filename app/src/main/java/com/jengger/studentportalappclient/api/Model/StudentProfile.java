package com.jengger.studentportalappclient.api.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StudentProfile implements Serializable {
    @SerializedName("nik_mhs")
@Expose
private String nikMhs;
@SerializedName("npm")
@Expose
private Integer npm;
@SerializedName("nama_mhs")
@Expose
private String namaMhs;
@SerializedName("no_hp_mhs")
@Expose
private String noHpMhs;
@SerializedName("kota_asal_mhs")
@Expose
private String kotaAsalMhs;
@SerializedName("alamat_asal_mhs")
@Expose
private String alamatAsalMhs;
@SerializedName("alamat_sekarang_mhs")
@Expose
private String alamatSekarangMhs;
@SerializedName("agama_mhs")
@Expose
private String agamaMhs;
@SerializedName("jenis_kelamin_mhs")
@Expose
private String jenisKelaminMhs;
@SerializedName("tempat_lahir_mhs")
@Expose
private String tempatLahirMhs;

    public String getProvinsiMhs() {
        return provinsiMhs;
    }

    public void setProvinsiMhs(String provinsiMhs) {
        this.provinsiMhs = provinsiMhs;
    }

    @SerializedName("provinsi_mhs")
    @Expose
    private String provinsiMhs;
@SerializedName("tanggal_lahir_mhs")
@Expose
private String tanggalLahirMhs;
@SerializedName("email_mhs")
@Expose
private String emailMhs;

public String getNikMhs() {
return nikMhs;
}

public void setNikMhs(String nikMhs) {
this.nikMhs = nikMhs;
}

public Integer getNpm() {
return npm;
}

public void setNpm(Integer npm) {
this.npm = npm;
}

public String getNamaMhs() {
return namaMhs;
}

public void setNamaMhs(String namaMhs) {
this.namaMhs = namaMhs;
}

public String getNoHpMhs() {
return noHpMhs;
}

public void setNoHpMhs(String noHpMhs) {
this.noHpMhs = noHpMhs;
}

public String getKotaAsalMhs() {
return kotaAsalMhs;
}

public void setKotaAsalMhs(String kotaAsalMhs) {
this.kotaAsalMhs = kotaAsalMhs;
}

public String getAlamatAsalMhs() {
return alamatAsalMhs;
}

public void setAlamatAsalMhs(String alamatAsalMhs) {
this.alamatAsalMhs = alamatAsalMhs;
}

public String getAlamatSekarangMhs() {
return alamatSekarangMhs;
}

public void setAlamatSekarangMhs(String alamatSekarangMhs) {
this.alamatSekarangMhs = alamatSekarangMhs;
}

public String getAgamaMhs() {
return agamaMhs;
}

public void setAgamaMhs(String agamaMhs) {
this.agamaMhs = agamaMhs;
}

public String getJenisKelaminMhs() {
return jenisKelaminMhs;
}

public void setJenisKelaminMhs(String jenisKelaminMhs) {
this.jenisKelaminMhs = jenisKelaminMhs;
}

public String getTempatLahirMhs() {
return tempatLahirMhs;
}

public void setTempatLahirMhs(String tempatLahirMhs) {
this.tempatLahirMhs = tempatLahirMhs;
}

public String getTanggalLahirMhs() {
return tanggalLahirMhs;
}

public void setTanggalLahirMhs(String tanggalLahirMhs) {
this.tanggalLahirMhs = tanggalLahirMhs;
}

public String getEmailMhs() {
return emailMhs;
}

public void setEmailMhs(String emailMhs) {
this.emailMhs = emailMhs;
}
}
