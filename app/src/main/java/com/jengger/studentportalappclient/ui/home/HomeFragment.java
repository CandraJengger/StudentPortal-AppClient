package com.jengger.studentportalappclient.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jengger.studentportalappclient.MainActivity;
import com.jengger.studentportalappclient.R;
import com.jengger.studentportalappclient.api.APIClient;
import com.jengger.studentportalappclient.api.Model.StatusMahasiswa;
import com.jengger.studentportalappclient.share_pref.Preference;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private final String TAG = HomeFragment.class.getSimpleName();
    private Integer izin, alpha, hadir;
    private TextView tvUsername;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Integer npm = MainActivity.npmMhs;
//        String kodeSemester = MainActivity.semesterMhs;
//        String tokenAuth = MainActivity.tokenMhs;

//         Get Presensi

        tvUsername = view.findViewById(R.id.txt_username);
        TextView tvPesan = view.findViewById(R.id.txt_ucapan);
        TextView tvTanggal = view.findViewById(R.id.txt_tanggal);

        getStatusMahasiswa(view, Preference.getLoggedNPM(getContext()), "Bearer " + Preference.getLoggedToken(getContext()));
        getDataPribadi(Preference.getLoggedNPM(getContext()), "Bearer " + Preference.getLoggedToken(getContext()));

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(calendar.getTime());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        tvTanggal.setText(date);
        if(hour>=1 && hour<=11) {
            tvPesan.setText(R.string.selamat_pagi);
        } else if(hour>11 && hour<=16) {
            tvPesan.setText(R.string.selamat_siang);
        } else if(hour>16 && hour<=18) {
            tvPesan.setText(R.string.selamat_sore);
        } else if(hour>18 && hour<=24) {
            tvPesan.setText(R.string.selamat_malam);
        }
    }

    private void getPresensi(final View view, Integer npm, String kodeSemeter, String token) {
        Call<ResponseBody> presensiResult = APIClient.getPresensiService().getPresensiSemester(npm, kodeSemeter, token);
        presensiResult.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObjectResult = new JSONObject(response.body().string());
                        if (jsonObjectResult.getString("values").length() > 0) {
                            JSONObject dataObject = jsonObjectResult.getJSONObject("values");

                            izin = dataObject.getInt("izin");
                            alpha = dataObject.getInt("alpha");
                            hadir = dataObject.getInt("hadir");

                            pieChartInit(view, izin, alpha, hadir);
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "Error: " + response);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {

            }
        });
    }

    private void pieChartInit(View view, Integer izin, Integer alpha, Integer hadir) {
        PieChartView pieChartView = view.findViewById(R.id.chart);
        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(izin, Color.parseColor("#FFEB31")));
        pieData.add(new SliceValue(alpha, Color.parseColor("#EB6D7A")));
        pieData.add(new SliceValue(hadir, Color.parseColor("#4ECCA3")));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasCenterCircle(true);
        pieChartView.setPieChartData(pieChartData);
    }

    private void getDataPribadi(final int npm, final String token) {
        Call<ResponseBody> statusMahasiswaResult = APIClient.getMahasiswaService().getProfilMahasiswa(npm, token);
        statusMahasiswaResult.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray dataArray = jsonObject.getJSONArray("values");
                        JSONObject dataObject = dataArray.getJSONObject(0);

                        tvUsername.setText(dataObject.getString("NAMA_MHS"));

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "Error " + response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "Error: " + t.getLocalizedMessage());
            }
        });
    }

    private void getStatusMahasiswa(final View view, Integer npm, final String token) {
        Call<ResponseBody> statusMahasiswaResult = APIClient.getMahasiswaService().getStatusMahasiswa(npm, token);
        statusMahasiswaResult.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray dataArray = jsonObject.getJSONArray("values");
                        JSONObject dataObject = dataArray.getJSONObject(0);

                        Preference.setLoggedKelas(getContext(), dataObject.getString("KODE_KELAS"));
                        Preference.setLoggedSemester(getContext(), Integer.parseInt(dataObject.getString("KODE_SEMESTER")));

                        getPresensi(view, Preference.getLoggedNPM(getContext()),
                                String.valueOf(Preference.getLoggedSemester(getContext())),
                                "Bearer " + Preference.getLoggedToken(getContext()));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "Error " + response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "Error: " + t.getLocalizedMessage());
            }
        });
    }
}

