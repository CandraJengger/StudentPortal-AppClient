package com.jengger.studentportalappclient.api.Service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface DosenService {
    @GET("lecturerAccount/{nip}")
    Call<ResponseBody> getDosen(@Path("nip") int nip, @Header("Authorization") String authorization);
}
