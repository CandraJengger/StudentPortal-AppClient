package com.jengger.studentportalappclient.ui.profile.page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jengger.studentportalappclient.R;
import com.jengger.studentportalappclient.api.APIClient;
import com.jengger.studentportalappclient.api.Model.StatusMahasiswa;
import com.jengger.studentportalappclient.share_pref.Preference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusFragment extends Fragment {
    private TextView tvStatus, tvNpm, tvAngkatan, tvProdi, tvNoHp, tvKelas, tvKota;
    private String TAG = StatusMahasiswa.class.getSimpleName();
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvStatus = view.findViewById(R.id.txt_status);
        tvNpm = view.findViewById(R.id.status_npm);
        tvAngkatan = view.findViewById(R.id.status_angkatan);
        tvKelas = view.findViewById(R.id.status_kelas);
        tvNoHp = view.findViewById(R.id.status_nohp);
        tvKota = view.findViewById(R.id.status_kota);
        tvProdi = view.findViewById(R.id.status_prodi);
        progressBar = view.findViewById(R.id.progressBar);

        showLoading(true);
        getStatusMahasiswa(Preference.getLoggedNPM(getContext()), "Bearer " + Preference.getLoggedToken(getContext()));
    }

    private void getStatusMahasiswa(Integer npm, final String token) {
        Call<ResponseBody> statusMahasiswaResult = APIClient.getMahasiswaService().getStatusMahasiswa(npm, token);
        statusMahasiswaResult.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray dataArray = jsonObject.getJSONArray("values");
                        JSONObject dataObject = dataArray.getJSONObject(0);

                        tvNpm.setText(String.valueOf(dataObject.getInt("NPM")));
                        getAkunMahasiswa(dataObject.getInt("NPM"), token);
                        getProdi(dataObject.getString("ID_PRODI"), token);
                        getKelas(dataObject.getString("KODE_KELAS"), token);
                        getAngkatan(dataObject.getInt("KODE_ANGKATAN"), token);
                        getDataPribadi(dataObject.getInt("NPM"), token);

                        showLoading(false);
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

    private void getAkunMahasiswa(int npm, String token) {
        Call<ResponseBody> statusMahasiswaResult = APIClient.getMahasiswaService().getAkunMahasiswa(npm, token);
        statusMahasiswaResult.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray dataArray = jsonObject.getJSONArray("values");
                        JSONObject dataObject = dataArray.getJSONObject(0);

                        tvStatus.setText(dataObject.getString("STATUS_MHS"));

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

    private void getDataPribadi(int npm, final String token) {
        Call<ResponseBody> statusMahasiswaResult = APIClient.getMahasiswaService().getProfilMahasiswa(npm, token);
        statusMahasiswaResult.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray dataArray = jsonObject.getJSONArray("values");
                        JSONObject dataObject = dataArray.getJSONObject(0);

                        tvNoHp.setText(dataObject.getString("NO_HP_MHS"));
                        tvKota.setText(dataObject.getString("KOTA_ASAL_MHS"));

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

    private void getAngkatan(int kodeAngkatan,final String token) {
        Call<ResponseBody> statusMahasiswaResult = APIClient.getAngkatanService().getAngkatan(kodeAngkatan, token);
        statusMahasiswaResult.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray dataArray = jsonObject.getJSONArray("values");
                        JSONObject dataObject = dataArray.getJSONObject(0);

                        tvAngkatan.setText(dataObject.getString("DESKRIPSI"));

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

    private void getKelas(String kodeKelas, final String token) {
        Call<ResponseBody> statusMahasiswaResult = APIClient.getKelasService().getKelas(kodeKelas, token);
        statusMahasiswaResult.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray dataArray = jsonObject.getJSONArray("values");
                        JSONObject dataObject = dataArray.getJSONObject(0);

                        tvKelas.setText(dataObject.getString("NAMA_KELAS"));

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

    private void getProdi(String idProdi,final String token) {
        Call<ResponseBody> statusMahasiswaResult = APIClient.getProgramStudiService().getProgramStudi(idProdi, token);
        statusMahasiswaResult.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray dataArray = jsonObject.getJSONArray("values");
                        JSONObject dataObject = dataArray.getJSONObject(0);

                        tvProdi.setText(dataObject.getString("NAMA_PRODI"));

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

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}