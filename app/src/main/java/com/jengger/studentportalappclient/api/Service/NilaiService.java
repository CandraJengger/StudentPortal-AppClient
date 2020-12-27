package com.jengger.studentportalappclient.api.Service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface NilaiService {
    @GET("valueCourses/npm/{npm}/semester/{semester}/courses/{courses}")
    Call<ResponseBody> getNilaiMK(
            @Path("npm") int npm,
            @Path("semester") int semester,
            @Path("courses") String mk,
            @Header("Authorization") String authorization);

    @GET("valueCourses/npm/{npm}/ips/{semester}")
    Call<ResponseBody> getIPS(
            @Path("npm") int npm,
            @Path("semester") int smt,
            @Header("Authorization") String authorization);

    @GET("valueCourses/npm/{npm}/ipk")
    Call<ResponseBody> getIPK(
            @Path("npm") int npm,
            @Header("Authorization") String authorization);
}
