package com.jengger.studentportalappclient.ui.jadwal;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JadwalFragment extends Fragment {
    private String TAG = JadwalFragment.class.getSimpleName();
    private ArrayList<Jadwal> list = new ArrayList<>();
    private RecyclerView recyclerView;

    String[] hari = {
            "Senin",
            "Selasa",
            "Rabu",
            "Kamis",
            "Jumat"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jadwal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_hari);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "Selected "+ hari[position], Toast.LENGTH_SHORT).show();
                getJadwalPerkuliahan(Preference.getLoggedKelas(getContext()),3, hari[position], "Bearer " + Preference.getLoggedToken(getContext()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        @SuppressLint("ResourceType") final ArrayAdapter adapter = new ArrayAdapter(requireContext(), R.layout.spinner_item, hari);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapter);


        recyclerView = view.findViewById(R.id.rv_perkuliahan);

    }

    private void getJadwalPerkuliahan(String kodeKelas, Integer kodeSemeter, String hari, String token) {
        final ArrayList<Jadwal> listJadwal = new ArrayList<>();

        Call<ResponseBody> jadwalResult = APIClient.getPerkuliahan().getDataPerkuliahanHari(kodeKelas, kodeSemeter, hari, token);
        jadwalResult.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObjectResult = new JSONObject(response.body().string());
                        if (jsonObjectResult.getString("values").length() > 0) {
                            JSONArray dataArray = jsonObjectResult.getJSONArray("values");

                            list.clear();
                            listJadwal.clear();

                            for (int i = 0; i < dataArray.length() ;i++) {
                                Jadwal jadwal = new Jadwal();

                                jadwal.setKodeMK(dataArray.getJSONObject(i).getString("KODE_JADWAL"));
                                jadwal.setDosenMK(dataArray.getJSONObject(i).getString("DOSEN"));
                                jadwal.setJamMK(dataArray.getJSONObject(i).getString("JAM_KE"));
                                jadwal.setNamaMK(dataArray.getJSONObject(i).getString("MATAKULIAH"));
                                jadwal.setRuangMK(dataArray.getJSONObject(i).getString("RUANG") + ", " +
                                        dataArray.getJSONObject(i).getString("GEDUNG"));

                                listJadwal.add(jadwal);

                            }

                            list.addAll(listJadwal);
                            showRecyclerView();
                        } else {
                            Toast.makeText(getContext(), "Selected ", Toast.LENGTH_SHORT).show();
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

    private void showRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ListJadwalAdapter adapter = new ListJadwalAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}