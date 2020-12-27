package com.jengger.studentportalappclient.ui.hasil_studi;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HasilStudiragment extends Fragment {

    private TextView tvKode, tvMK, tvSKS, tvHuruf, tvAngka, tvIPS, tvIPK;
    private Spinner spinnerMK;
    private Integer semesterInit = 1;

    String[] semester = {
            "Semester 1",
            "Semester 2",
            "Semester 3",
            "Semester 4",
            "Semester 5",
            "Semester 6"
    };

    String[] matakuliah = {
            "Algoritma Pemrograman",
            "Struktur Data",
            "Desain Database",
            "Bahasa Inggris",
            "Teknologi Informasi",
            "Bahasa Inggris",
            "Sistem Komputasi Awan"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hasil_studiragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvKode = view.findViewById(R.id.hs_kode);
        tvAngka = view.findViewById(R.id.hs_angka_teori);
        tvHuruf = view.findViewById(R.id.hs_huruf_teori);
        tvMK = view.findViewById(R.id.hs_mk);
        tvSKS = view.findViewById(R.id.hs_sks_teori);
        tvIPS = view.findViewById(R.id.txt_ips);
        tvIPK = view.findViewById(R.id.txt_ipk);

        /* Semester */
        Spinner spinnerSemester = (Spinner) view.findViewById(R.id.spinner_semester);
        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "Selected "+ semester[position], Toast.LENGTH_SHORT).show();
                semesterInit = position + 1;
                getMatakuliah(Preference.getLoggedKelas(getContext()), position + 1, "Bearer " + Preference.getLoggedToken(getContext()));
                getHasilIPS(Preference.getLoggedNPM(getContext()), semesterInit,"Bearer " + Preference.getLoggedToken(getContext()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        @SuppressLint("ResourceType") final ArrayAdapter adapterSemester = new ArrayAdapter(requireContext(), R.layout.spinner_item, semester);
        adapterSemester.setDropDownViewResource(R.layout.spinner_dropdown);
        spinnerSemester.setAdapter(adapterSemester);

        /* Matakuliah */
        spinnerMK = view.findViewById(R.id.spinner_mk);

        getHasilIPK(Preference.getLoggedNPM(getContext()),"Bearer " + Preference.getLoggedToken(getContext()));
    }

    private void spinnerInitMK (Spinner spinner, final ArrayList<String> mk, final ArrayList<String> kodeMK) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "Selected "+ kodeMK.get(position), Toast.LENGTH_SHORT).show();
                getHasilStudiMK(Preference.getLoggedNPM(getContext()), semesterInit, kodeMK.get(position), "Bearer " +Preference.getLoggedToken(getContext()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        @SuppressLint("ResourceType") final ArrayAdapter adapterMK = new ArrayAdapter(requireContext(), R.layout.spinner_item, mk);
        adapterMK.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapterMK);
    }

    private void getMatakuliah(String kodeKelas, Integer kodeSemester, String token) {
        final ArrayList<String> listMK = new ArrayList<>();
        final ArrayList<String> listKodeMatakuliah = new ArrayList<>();

        Call<ResponseBody> mkResult = APIClient.getPerkuliahan().getDataPerkuliahanSemester(kodeKelas, kodeSemester, token);
        mkResult.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObjectResult = new JSONObject(response.body().string());
                        if (jsonObjectResult.getString("values").length() > 0) {
                            JSONArray dataArray = jsonObjectResult.getJSONArray("values");

                            listMK.clear();
                            listKodeMatakuliah.clear();

                            for (int i = 0; i < dataArray.length(); i++) {
                                listMK.add(dataArray.getJSONObject(i).getString("MATAKULIAH"));
                                listKodeMatakuliah.add(dataArray.getJSONObject(i).getString("KODE_MK"));
                            }

                            spinnerInitMK(spinnerMK, listMK, listKodeMatakuliah);
                        }
                    } catch (JSONException | IOException e ) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void getHasilStudiMK(Integer npm, Integer kodeSMt, String kodeMatakuliah, String token) {
        Call<ResponseBody> nilaiResult = APIClient.getNilai().getNilaiMK(npm, kodeSMt, kodeMatakuliah, token);
        nilaiResult.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObjectResult = new JSONObject(response.body().string());
                        if (jsonObjectResult.getString("values").length() > 0) {
                            JSONArray dataArray = jsonObjectResult.getJSONArray("values");
                            JSONObject dataObject = dataArray.getJSONObject(0);

                            tvKode.setText(dataObject.getString("KODE_MK"));
                            tvMK.setText(dataObject.getString("MATAKULIAH"));
                            tvSKS.setText(dataObject.getString("SKS"));
                            tvAngka.setText(dataObject.getString("NILAI_ANGKA"));
                            tvHuruf.setText(dataObject.getString("NILAI_HURUF"));
                        } else {
                            tvKode.setText(R.string.strip);
                            tvMK.setText(R.string.strip);
                            tvSKS.setText(R.string.strip);
                            tvAngka.setText(R.string.strip);
                            tvHuruf.setText(R.string.strip);
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void getHasilIPS(Integer npm, Integer kodeSmt, String token) {
        Call<ResponseBody> nilaiResult = APIClient.getNilai().getIPS(npm, kodeSmt, token);
        nilaiResult.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObjectResult = new JSONObject(response.body().string());
                        if (jsonObjectResult.getString("values").length() > 0) {
                            JSONObject dataObject = jsonObjectResult.getJSONObject("values");

                            tvIPS.setText(dataObject.getString("IPS"));
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void getHasilIPK(Integer npm, String token) {
        Call<ResponseBody> nilaiResult = APIClient.getNilai().getIPK(npm, token);
        nilaiResult.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObjectResult = new JSONObject(response.body().string());
                        if (jsonObjectResult.getString("values").length() > 0) {
                            JSONObject dataObject = jsonObjectResult.getJSONObject("values");

                            tvIPK.setText(dataObject.getString("IPK"));
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}