package com.jengger.studentportalappclient.api.Service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface PresensiService {
    @GET("/presence/npm/{npm}/semester/{semester}/total")
    Call<ResponseBody> getPresensiSemester(
            @Path("npm") int npm,
            @Path("semester") String kodeSemester,
            @Header("Authorization") String authorization
    );

    @GET("/presence/npm/{npm}/semester/{semester}/week/{week}")
    Call<ResponseBody> getPresensiMinggu(
            @Path("npm") int npm,
            @Path("semester") String kodeSemester,
            @Path("week") int minggu,
            @Header("Authorization") String authorization
    );
}
