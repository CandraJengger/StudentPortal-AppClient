package com.jengger.studentportalappclient.ui.pengumuman;

import android.os.Parcel;
import android.os.Parcelable;

public class Pengumuman implements Parcelable {
    private String judul;
    private String kategori;
    private String tanggal;
    private String jam;
    private String pengirim;
    private String isi;

    protected Pengumuman(Parcel in) {
        judul = in.readString();
        kategori = in.readString();
        tanggal = in.readString();
        jam = in.readString();
        pengirim = in.readString();
        isi = in.readString();
    }

    public static final Creator<Pengumuman> CREATOR = new Creator<Pengumuman>() {
        @Override
        public Pengumuman createFromParcel(Parcel in) {
            return new Pengumuman(in);
        }

        @Override
        public Pengumuman[] newArray(int size) {
            return new Pengumuman[size];
        }
    };

    public Pengumuman() {

    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(judul);
        dest.writeString(kategori);
        dest.writeString(tanggal);
        dest.writeString(jam);
        dest.writeString(pengirim);
        dest.writeString(isi);
    }
}
