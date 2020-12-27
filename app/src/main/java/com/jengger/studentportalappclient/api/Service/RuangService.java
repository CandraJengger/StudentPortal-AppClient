package com.jengger.studentportalappclient.api.Service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface RuangService {
    @GET("rooms/{roomCode}")
    Call<ResponseBody> getRuangDetail(@Path("roomCode") String kodeRuang, @Header("Authorization") String authorization);

    @GET("buildings/{buildingCode}")
    Call<ResponseBody> getGedungDetail(@Path("roomCode") String kodeGedung, @Header("Authorization") String authorization);
}
