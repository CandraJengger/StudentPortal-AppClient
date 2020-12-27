package com.jengger.studentportalappclient.ui.daftar_ulang;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jengger.studentportalappclient.R;
import com.jengger.studentportalappclient.api.APIClient;
import com.jengger.studentportalappclient.share_pref.Preference;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarUlangFragment extends Fragment {
    String[] periode = {
            "2019/2020 Gasal",
            "2019/2020 Genap"
    };

    private String TAG = DaftarUlangFragment.class.getSimpleName();
    private TextView tvUKT, tvStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daftar_ulang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_periode);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "Selected "+ periode[position], Toast.LENGTH_SHORT).show();
                getDaftarUlangMhs(Preference.getLoggedNPM(getContext()), String.valueOf(position+1), "Bearer " + Preference.getLoggedToken(getContext()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        @SuppressLint("ResourceType") final ArrayAdapter adapter = new ArrayAdapter(requireContext(), R.layout.spinner_item, periode);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapter);

        tvStatus = view.findViewById(R.id.du_status);
        tvUKT = view.findViewById(R.id.du_ukt);
    }

    private void getDaftarUlangMhs(final Integer npm, String kodeSemeter,final String token) {
        Call<ResponseBody> presensiResult = APIClient.getMahasiswaService().getDaftarUlang(npm, kodeSemeter, token);
        presensiResult.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObjectResult = new JSONObject(response.body().string());
                        if (jsonObjectResult.getString("values").length() > 0) {
                            JSONArray dataArray = jsonObjectResult.getJSONArray("values");
                            JSONObject dataObject = dataArray.getJSONObject(0);

                            tvStatus.setText(dataObject.getString("STATUS_DAFTAR_ULANG"));
                            if (dataObject.getString("STATUS_DAFTAR_ULANG").equals("Belum Lunas")) {
                                tvStatus.setBackgroundColor(Color.parseColor("#EB6D7A"));
                            }
                            if (dataObject.getString("STATUS_DAFTAR_ULANG").equals("Lunas")) {
                                tvStatus.setBackgroundColor(Color.parseColor("#4ECCA3"));
                            }
                            getUKTMhs(npm, token);

                        } else {
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
                Log.d(TAG, "Error: " + t.getLocalizedMessage());
            }
        });
    }
    private void getUKTMhs(Integer npm, String token) {
        Call<ResponseBody> presensiResult = APIClient.getMahasiswaService().getUKTDetail(npm, token);
        presensiResult.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObjectResult = new JSONObject(response.body().string());
                        if (jsonObjectResult.getString("values").length() > 0) {
                            JSONArray dataArray = jsonObjectResult.getJSONArray("values");
                            JSONObject dataObject = dataArray.getJSONObject(0);

                            tvUKT.setText("Rp "+dataObject.getString("JUMLAH_UKT"));

                        } else {
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
                Log.d(TAG, "Error: " + t.getLocalizedMessage());
            }
        });
    }
}