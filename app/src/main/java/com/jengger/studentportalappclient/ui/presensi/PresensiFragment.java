package com.jengger.studentportalappclient.ui.presensi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jengger.studentportalappclient.R;
import com.jengger.studentportalappclient.api.APIClient;
import com.jengger.studentportalappclient.share_pref.Preference;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PresensiFragment extends Fragment {
    private RecyclerView rvPresensi;
    private ArrayList<Presensi> list = new ArrayList<>();
    private Integer kodeSemester = 0;
    private Integer pilihMinggu = 1;

    private String TAG = PresensiFragment.class.getSimpleName();

    String[] semester = {
            "Semester 1",
            "Semester 2",
            "Semester 3",
            "Semester 4",
            "Semester 5",
            "Semester 6"
    };

    String[] minggu = {
            "Minggu 1",
            "Minggu 2",
            "Minggu 3",
            "Minggu 4"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_presensi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* Semester */
        Spinner spinnerSemester = (Spinner) view.findViewById(R.id.spinner_semester);
        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "Selected "+ semester[position], Toast.LENGTH_SHORT).show();
                kodeSemester = position + 1;
                getMingguPresensi(Preference.getLoggedNPM(getContext()),String.valueOf(kodeSemester), pilihMinggu, "Bearer " + Preference.getLoggedToken(getContext()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        @SuppressLint("ResourceType") final ArrayAdapter adapterSemester = new ArrayAdapter(requireContext(), R.layout.spinner_item, semester);
        adapterSemester.setDropDownViewResource(R.layout.spinner_dropdown);
        spinnerSemester.setAdapter(adapterSemester);

        /* Matakuliah */
        Spinner spinnerMK = (Spinner) view.findViewById(R.id.spinner_minggu);
        spinnerMK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "Selected "+ minggu[position], Toast.LENGTH_SHORT).show();
                pilihMinggu = position + 1;
                getMingguPresensi(Preference.getLoggedNPM(getContext()),String.valueOf(kodeSemester), pilihMinggu, "Bearer " + Preference.getLoggedToken(getContext()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        @SuppressLint("ResourceType") final ArrayAdapter adapterMinggu = new ArrayAdapter(requireContext(), R.layout.spinner_item, minggu);
        adapterMinggu.setDropDownViewResource(R.layout.spinner_dropdown);
        spinnerMK.setAdapter(adapterMinggu);

        rvPresensi = view.findViewById(R.id.rv_presensi);
    }

    private void getMingguPresensi(Integer npm, String kodeSemeter, Integer minggu, String token) {
        final ArrayList<Presensi> listPresensi = new ArrayList<>();

        Call<ResponseBody> presensiResult = APIClient.getPresensiService().getPresensiMinggu(npm, kodeSemeter, minggu, token);
        presensiResult.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObjectResult = new JSONObject(response.body().string());
                        if (jsonObjectResult.getString("values").length() > 0) {
                            JSONArray dataArray = jsonObjectResult.getJSONArray("values");

                            list.clear();
                            listPresensi.clear();

                            for (int i = 0; i < dataArray.length() ;i++) {
                                Presensi presensi = new Presensi();

                                presensi.setNama(dataArray.getJSONObject(i).getString("MATAKULIAH"));
                                presensi.setStatus(dataArray.getJSONObject(i).getString("KETERANGAN_PRESENSI"));

                                listPresensi.add(presensi);

                            }

                            list.addAll(listPresensi);
                            showRecyclerList();
                        } else {
                            list.clear();
                            listPresensi.clear();
                            showRecyclerList();
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

    public void showRecyclerList() {
        rvPresensi.setLayoutManager(new LinearLayoutManager(getActivity()));
        ListPresensiAdapter listPresensiAdapter = new ListPresensiAdapter(list);
        rvPresensi.setAdapter(listPresensiAdapter);
    }
}