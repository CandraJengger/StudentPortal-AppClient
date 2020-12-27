package com.jengger.studentportalappclient.api.Model;

import java.io.Serializable;

public class BundleOrtu implements Serializable {
    private Parents parentsAyah;
    private Parents parentsIbu;

    public Parents getParentsAyah() {
        return parentsAyah;
    }

    public void setParentsAyah(Parents parentsAyah) {
        this.parentsAyah = parentsAyah;
    }

    public Parents getParentsIbu() {
        return parentsIbu;
    }

    public void setParentsIbu(Parents parentsIbu) {
        this.parentsIbu = parentsIbu;
    }

    public Parents getParentsWali() {
        return parentsWali;
    }

    public void setParentsWali(Parents parentsWali) {
        this.parentsWali = parentsWali;
    }

    private Parents parentsWali;
}
