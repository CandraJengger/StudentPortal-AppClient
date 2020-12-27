package com.jengger.studentportalappclient.api.Service;

import com.jengger.studentportalappclient.api.Model.LoginRequest;
import com.jengger.studentportalappclient.api.Model.StudentAccountRequest;
import com.jengger.studentportalappclient.api.Model.StudentProfile;
import com.jengger.studentportalappclient.api.Model.StudentProfileRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MahasiswaService {
    @POST("auth/student/login")
    Call<ResponseBody> mahasiswaLogin(@Body LoginRequest loginRequest);

    @GET("studentAccounts/{npm}")
    Call<ResponseBody> getAkunMahasiswa(@Path("npm") int npm, @Header("Authorization") String authorization);

    @PUT("activation/")
    Call<ResponseBody> activation(@Body StudentAccountRequest studentAccountRequest);

    @GET("studentStatus/npm/{npm}")
    Call<ResponseBody> getStatusMahasiswa(@Path("npm") int npm, @Header("Authorization") String authorization);

    @GET("studentProfile/{npm}")
    Call<ResponseBody> getProfilMahasiswa(@Path("npm") int npm, @Header("Authorization") String authorization);

    @GET("studentProfile/img/{name}")
    Call<ResponseBody> getImgProfilMahasiswa(@Path("name") String name, @Header("Authorization") String authorization);

    @GET("parents/{npm}")
    Call<ResponseBody> getOrtuMahasiswa(@Path("npm") int npm, @Header("Authorization") String authorization);

    @GET("reRegistrations/krs/{npm}/semester/{semester}")
    Call<ResponseBody> getDaftarUlang(@Path("npm") int npm, @Path("semester") String semester, @Header("Authorization") String authorization);

    @GET("ukt/{npm}")
    Call<ResponseBody> getUKTDetail(@Path("npm") int npm, @Header("Authorization") String authorization);

    @PUT("studentProfile/update")
    Call<ResponseBody> updateDataProfil(@Body StudentProfileRequest studentProfileRequest, @Header("Authorization") String authorization);
}
