package com.jengger.studentportalappclient.api.Service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface KelasService {
    @GET("classLectures/{id}")
    Call<ResponseBody> getKelas(@Path("id") String kodeKelas, @Header("Authorization") String authorization);
}
