package com.jengger.studentportalappclient.api.Service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface PengumumanService {
    @GET("announcements/")
    Call<ResponseBody> getPengumuman(@Header("Authorization") String authorization);

    @GET("announcements/category/{category}")
    Call<ResponseBody> getPengumumanKategori(@Path("category") String kategori, @Header("Authorization") String authorization);
}
