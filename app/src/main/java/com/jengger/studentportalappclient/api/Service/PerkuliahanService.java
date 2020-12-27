package com.jengger.studentportalappclient.api.Service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface PerkuliahanService {
    @GET("lectures/classLectures/{classCode}/semester/{semesterCode}")
    Call<ResponseBody> getDataPerkuliahanSemester(@Path("classCode") String kodeKelas,
                                                  @Path("semesterCode") int kodeSemester,
                                                  @Header("Authorization") String authorization);
    @GET("lectures/classLectures/{classCode}/semester/{semesterCode}/day/{day}")
    Call<ResponseBody> getDataPerkuliahanHari(@Path("classCode") String kodeKelas,
                                                  @Path("semesterCode") int kodeSemester,
                                                  @Path("day") String hari,
                                                  @Header("Authorization") String authorization);

    @GET("lectures/{id}")
    Call<ResponseBody> getPerkuliahanDetail(@Path("id") int kodePerkuliahan, @Header("Authorization") String authorization);

    @GET("courses/{id}")
    Call<ResponseBody> getMatakuliahDetail(@Path("id") int kodeMK, @Header("Authorization") String authorization);
}
