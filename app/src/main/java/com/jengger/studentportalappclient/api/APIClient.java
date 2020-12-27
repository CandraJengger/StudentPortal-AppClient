package com.jengger.studentportalappclient.api;

import com.jengger.studentportalappclient.api.Service.AngkatanService;
import com.jengger.studentportalappclient.api.Service.DosenService;
import com.jengger.studentportalappclient.api.Service.KelasService;
import com.jengger.studentportalappclient.api.Service.MahasiswaService;
import com.jengger.studentportalappclient.api.Service.NilaiService;
import com.jengger.studentportalappclient.api.Service.OrangtuaService;
import com.jengger.studentportalappclient.api.Service.PengumumanService;
import com.jengger.studentportalappclient.api.Service.PerkuliahanService;
import com.jengger.studentportalappclient.api.Service.PresensiService;
import com.jengger.studentportalappclient.api.Service.ProgramStudiService;
import com.jengger.studentportalappclient.api.Service.RuangService;
import com.jengger.studentportalappclient.api.Service.SemesterService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private final static String API_BASE_URL = "http://192.168.43.93:5000/";

    private static Retrofit getRetrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API_BASE_URL)
                .client(okHttpClient)
                .build();

        return retrofit;
    }

    public static MahasiswaService getMahasiswaService() {
        return getRetrofit().create(MahasiswaService.class);
    }

    public static PresensiService getPresensiService() {
        return getRetrofit().create(PresensiService.class);
    }

    public static AngkatanService getAngkatanService() {
        return getRetrofit().create(AngkatanService.class);
    }

    public static KelasService getKelasService() {
        return getRetrofit().create(KelasService.class);
    }

    public static ProgramStudiService getProgramStudiService() {
        return getRetrofit().create(ProgramStudiService.class);
    }

    public static SemesterService getSemesterService() {
        return getRetrofit().create(SemesterService.class);
    }

    public static PerkuliahanService getPerkuliahan() {
        return getRetrofit().create(PerkuliahanService.class);
    }

    public static DosenService getDosen() {
        return getRetrofit().create(DosenService.class);
    }

    public static RuangService getRuang() {
        return getRetrofit().create(RuangService.class);
    }

    public static PengumumanService getPengumuman() {
        return getRetrofit().create(PengumumanService.class);
    }

    public static NilaiService getNilai() {
        return getRetrofit().create(NilaiService.class);
    }

    public static OrangtuaService getOrangtua(){
        return getRetrofit().create(OrangtuaService.class);
    }
}
