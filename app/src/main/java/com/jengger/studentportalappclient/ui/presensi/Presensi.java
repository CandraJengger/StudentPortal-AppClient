package com.jengger.studentportalappclient.ui.presensi;

import android.os.Parcel;
import android.os.Parcelable;

public class Presensi implements Parcelable {
    private String nama;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nama);
        dest.writeString(this.status);
    }

    Presensi() {

    }

    private Presensi(Parcel i) {
        this.nama = i.readString();
        this.status = i.readString();
    }

    public static final Parcelable.Creator<Presensi> CREATOR = new Parcelable.Creator<Presensi>() {

        @Override
        public Presensi createFromParcel(Parcel source) {
            return new Presensi(source);
        }

        @Override
        public Presensi[] newArray(int size) {
            return new Presensi[size];
        }
    };
}
