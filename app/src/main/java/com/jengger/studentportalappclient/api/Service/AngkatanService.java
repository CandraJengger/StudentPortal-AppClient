package com.jengger.studentportalappclient.api.Service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface AngkatanService {
    @GET("batchs/{id}")
    Call<ResponseBody> getAngkatan(@Path("id") int kodeAngkatan, @Header("Authorization") String authorization);

}
