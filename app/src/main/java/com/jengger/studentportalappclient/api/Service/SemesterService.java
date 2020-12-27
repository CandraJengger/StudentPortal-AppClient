package com.jengger.studentportalappclient.api.Service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface SemesterService {
    @GET("semesters/{id}")
    Call<ResponseBody> getSemester(@Path("id") String kodeSemester, @Header("Authorization") String authorization);
}
