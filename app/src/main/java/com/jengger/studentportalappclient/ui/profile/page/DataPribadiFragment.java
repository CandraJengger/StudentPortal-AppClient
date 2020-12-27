package com.jengger.studentportalappclient.ui.profile.page;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.jengger.studentportalappclient.R;
import com.jengger.studentportalappclient.api.APIClient;
import com.jengger.studentportalappclient.api.Model.StudentProfile;
import com.jengger.studentportalappclient.share_pref.Preference;
import com.jengger.studentportalappclient.ui.profile.edit.EditPribadiFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPribadiFragment extends Fragment {
    private TextView tvNIK, tvNama, tvLahir, tvJK, tvAgama,
            tvAlamatAsal, tvKotaAsal, tvNoHP, tvEmail, tvAlamatSekarang;
    private ProgressBar progressBar;
    private String TAG = DataPribadiFragment.class.getSimpleName();
    StudentProfile mDataPribadi = new StudentProfile();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_pribadi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnUbahDataDiri = view.findViewById(R.id.btn_ubah_data_pribadi);

        tvNIK = view.findViewById(R.id.dp_nik);
        tvNama = view.findViewById(R.id.dp_nama);
        tvLahir = view.findViewById(R.id.dp_lahir);
        tvJK = view.findViewById(R.id.dp_jk);
        tvAgama = view.findViewById(R.id.dp_agama);
        tvAlamatAsal = view.findViewById(R.id.dp_alamat_asal);
        tvKotaAsal = view.findViewById(R.id.dp_kota);
        tvNoHP = view.findViewById(R.id.dp_no_hp);
        tvEmail = view.findViewById(R.id.dp_email);
        tvAlamatSekarang = view.findViewById(R.id.dp_sekarang);
        progressBar = view.findViewById(R.id.progressBar);

        btnUbahDataDiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(EditPribadiFragment.KEY_DATA_PRIBADI_EXTRA, mDataPribadi);

                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_nav_profileFragment_to_editPribadiFragment, mBundle);
            }
        });

        showLoading(true);
        getDataPribadi(Preference.getLoggedNPM(getContext()), "Bearer " + Preference.getLoggedToken(getContext()));
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

                        String ttl = dataObject.getString("TEMPAT_LAHIR_MHS") + ", " + dataObject.getString("TANGGAL_LAHIR_MHS");
                        tvNIK.setText(dataObject.getString("NIK_MHS"));
                        tvNama.setText(dataObject.getString("NAMA_MHS"));
                        tvLahir.setText(ttl);
                        tvJK.setText(dataObject.getString("JENIS_KELAMIN_MHS"));
                        tvAgama.setText(dataObject.getString("AGAMA_MHS"));
                        tvAlamatAsal.setText(dataObject.getString("ALAMAT_ASAL_MHS"));
                        tvKotaAsal.setText(dataObject.getString("KOTA_ASAL_MHS"));
                        tvNoHP.setText(dataObject.getString("NO_HP_MHS"));
                        tvAlamatSekarang.setText(dataObject.getString("ALAMAT_SEKARANG_MHS"));
                        tvEmail.setText(dataObject.getString("EMAIL_MHS"));

                        Preference.setKeyNik(getContext(), dataObject.getString("NIK_MHS"));

                        mDataPribadi.setNikMhs(Preference.getKeyNik(getContext()));
                        mDataPribadi.setNpm(Preference.getLoggedNPM(getContext()));
                        mDataPribadi.setNamaMhs(dataObject.getString("NAMA_MHS"));
                        mDataPribadi.setAgamaMhs(dataObject.getString("AGAMA_MHS"));
                        mDataPribadi.setProvinsiMhs(dataObject.getString("PROVINSI_MHS"));
                        mDataPribadi.setAlamatAsalMhs(dataObject.getString("ALAMAT_ASAL_MHS"));
                        mDataPribadi.setAlamatSekarangMhs(dataObject.getString("ALAMAT_SEKARANG_MHS"));
                        mDataPribadi.setJenisKelaminMhs(dataObject.getString("JENIS_KELAMIN_MHS"));
                        mDataPribadi.setTempatLahirMhs(dataObject.getString("TEMPAT_LAHIR_MHS"));
                        mDataPribadi.setTanggalLahirMhs(dataObject.getString("TANGGAL_LAHIR_MHS"));
                        mDataPribadi.setKotaAsalMhs(dataObject.getString("KOTA_ASAL_MHS"));
                        mDataPribadi.setNoHpMhs(dataObject.getString("NO_HP_MHS"));
                        mDataPribadi.setEmailMhs(dataObject.getString("EMAIL_MHS"));

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

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}