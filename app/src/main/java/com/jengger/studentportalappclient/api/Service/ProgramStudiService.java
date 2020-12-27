package com.jengger.studentportalappclient.api.Service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ProgramStudiService {
    @GET("studyPrograms/{id}")
    Call<ResponseBody> getProgramStudi(@Path("id") String kodeProdi, @Header("Authorization") String authorization);
}
