package com.jengger.studentportalappclient.api.Service;

import com.jengger.studentportalappclient.api.Model.ParentRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface OrangtuaService {
    @PUT("parents/update")
    Call<ResponseBody> updateDataOrangtua(@Body ParentRequest parentRequest, @Header("Authorization") String authorization);
}
