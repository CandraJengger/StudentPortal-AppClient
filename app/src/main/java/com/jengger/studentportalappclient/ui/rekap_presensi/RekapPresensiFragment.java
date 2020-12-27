package com.jengger.studentportalappclient.ui.rekap_presensi;

import android.annotation.SuppressLint;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RekapPresensiFragment extends Fragment  {
    private final String TAG = RekapPresensiFragment.class.getSimpleName();
    private Integer izin, alpha, hadir, kosong;
    private TextView txtHadir, txtIzin, txtAlpha, txtKosong;

    String[] semester = {
            "Semester 1",
            "Semester 2",
            "Semester 3",
            "Semester 4",
            "Semester 5",
            "Semester 6"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_rekap_presensi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtHadir = view.findViewById(R.id.txt_hadir);
        txtIzin = view.findViewById(R.id.txt_izin);
        txtAlpha = view.findViewById(R.id.txt_alpha);
        txtKosong = view.findViewById(R.id.txt_kosong);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_semester);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "Selected "+ semester[position], Toast.LENGTH_SHORT).show();
                getPresensi(view, Preference.getLoggedNPM(getContext()), String.valueOf(position + 1), "Bearer " + Preference.getLoggedToken(getContext()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        @SuppressLint("ResourceType") final ArrayAdapter adapter = new ArrayAdapter(requireContext(), R.layout.spinner_item, semester);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapter);


    }

    private void getPresensi(final View view, Integer npm, String kodeSemeter, String token) {
        Call<ResponseBody> presensiResult = APIClient.getPresensiService().getPresensiSemester(npm, kodeSemeter, token);
        presensiResult.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("SetTextI18n")
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
                            kosong = dataObject.getInt("kosong");

                            txtHadir.setText(hadir + " jam");
                            txtIzin.setText(izin + " jam");
                            txtAlpha.setText(alpha + " jam");
                            txtKosong.setText(kosong + " jam");

                        } else {
                            txtHadir.setText(0 + " jam");
                            txtIzin.setText(0 + " jam");
                            txtAlpha.setText(0 + " jam");
                            txtKosong.setText(0 + " jam");
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