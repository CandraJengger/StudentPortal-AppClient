package com.jengger.studentportalappclient.ui.profile.edit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.jengger.studentportalappclient.R;
import com.jengger.studentportalappclient.api.APIClient;
import com.jengger.studentportalappclient.api.Model.StudentProfile;
import com.jengger.studentportalappclient.api.Model.StudentProfileRequest;
import com.jengger.studentportalappclient.share_pref.Preference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPribadiFragment extends Fragment {

    public static String KEY_DATA_PRIBADI_EXTRA = "key_data_pribadi_extra";
    private TextView tvNIK, tvNPM, tvNama, tvTempatLahir, tvTanggalLahir, tvProvinsi,
                    tvKota, tvAlamatAsal, tvAlamatSekarang, tvNoHP, tvEmail;
    private RadioButton rbGender;

    StudentProfile mStudentProfile = new StudentProfile();

    private String TAG = EditPribadiFragment.class.getSimpleName();
    String[] agama = {
            "Islam",
            "Kristen",
            "Katholik",
            "Hindu",
            "Budha",
            "Konghucu"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_pribadi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        Bundle mBundle = getArguments();
        StudentProfile dataPribadi = (StudentProfile) mBundle.getSerializable(KEY_DATA_PRIBADI_EXTRA);

        super.onViewCreated(view, savedInstanceState);
        tvNIK = view.findViewById(R.id.edt_nik);
        tvNIK.setText(dataPribadi.getNikMhs());

        tvNama = view.findViewById(R.id.edt_nama);
        tvNama.setText(dataPribadi.getNamaMhs());

        tvTempatLahir = view.findViewById(R.id.edt_tempat_lahir);
        tvTempatLahir.setText(dataPribadi.getTempatLahirMhs());

        tvTanggalLahir = view.findViewById(R.id.edt_tanggal_lahir);
        tvTanggalLahir.setText(dataPribadi.getTanggalLahirMhs());

        tvProvinsi = view.findViewById(R.id.edt_provinsi);
        tvProvinsi.setText(dataPribadi.getProvinsiMhs());

        tvKota = view.findViewById(R.id.edt_kota);
        tvKota.setText(dataPribadi.getKotaAsalMhs());

        tvAlamatAsal = view.findViewById(R.id.edt_alamat_asal);
        tvAlamatAsal.setText(dataPribadi.getAlamatAsalMhs());

        tvAlamatSekarang = view.findViewById(R.id.edt_alamat_sekarang);
        tvAlamatSekarang.setText(dataPribadi.getAlamatSekarangMhs());

        tvNoHP = view.findViewById(R.id.edt_no_hp);
        tvNoHP.setText(dataPribadi.getNoHpMhs());

        tvEmail = view.findViewById(R.id.edt_email);
        tvEmail.setText(dataPribadi.getEmailMhs());

        RadioGroup rbGenderGroup =view.findViewById(R.id.rb_gender);
        int selectedId = rbGenderGroup.getCheckedRadioButtonId();
        rbGender = view.findViewById(selectedId);

        RadioButton rbLakiLaki = view.findViewById(R.id.rb_male);
        RadioButton rbPerempuan = view.findViewById(R.id.rb_female);
        Log.d(TAG, rbLakiLaki.getText().toString().substring(0, 1));
        if (rbLakiLaki.getText().toString().substring(0,1).equals(dataPribadi.getJenisKelaminMhs())) {
            rbLakiLaki.setChecked(true);
        } else {
            rbPerempuan.setChecked(true);
        }

        Spinner spinner = view.findViewById(R.id.spinner_agama);

        final ArrayAdapter adapter = new ArrayAdapter(requireContext(), R.layout.spinner_item_light, agama);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapter);

        for (int i = 0; i < agama.length; i++) {
            if (String.valueOf(spinner.getItemAtPosition(i)).equals(dataPribadi.getAgamaMhs())) {
                spinner.setSelection(i);
                break;
            }
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), agama[position], Toast.LENGTH_SHORT).show();
                mStudentProfile.setAgamaMhs(agama[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btnBatal = view.findViewById(R.id.btn_batal);
        btnBatal.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_editPribadiFragment_to_nav_profileFragment)
        );

        LinearLayout btnSimpan = view.findViewById(R.id.btn_simpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvNIK.getText().length() > 0) {
                    mStudentProfile.setNikMhs(tvNIK.getText().toString());
                } else {
                    mStudentProfile.setNikMhs("-");
                }
                if (tvNama.getText().length() > 0) {
                    mStudentProfile.setNamaMhs(tvNama.getText().toString());
                } else {
                    mStudentProfile.setNamaMhs("-");
                }
                if (tvTempatLahir.getText().length() > 0) {
                    mStudentProfile.setTempatLahirMhs(tvTempatLahir.getText().toString());
                } else {
                    mStudentProfile.setTempatLahirMhs("-");
                }
                if (tvTanggalLahir.getText().length() > 0) {
                    mStudentProfile.setTanggalLahirMhs(tvTanggalLahir.getText().toString());
                } else {
                    mStudentProfile.setTanggalLahirMhs("-");
                }
                if (tvAlamatAsal.getText().length() > 0) {
                    mStudentProfile.setAlamatAsalMhs(tvAlamatAsal.getText().toString());
                } else {
                    mStudentProfile.setAlamatAsalMhs("-");
                }
                if (tvAlamatSekarang.getText().length() > 0) {
                    mStudentProfile.setAlamatSekarangMhs(tvAlamatSekarang.getText().toString());
                } else {
                    mStudentProfile.setAlamatSekarangMhs("-");
                }
                if (tvProvinsi.getText().length() > 0) {
                    mStudentProfile.setProvinsiMhs(tvProvinsi.getText().toString());
                } else {
                    mStudentProfile.setProvinsiMhs("-");
                }
                if (tvKota.getText().length() > 0) {
                    mStudentProfile.setKotaAsalMhs(tvKota.getText().toString());
                } else {
                    mStudentProfile.setKotaAsalMhs("-");
                }
                if (tvNoHP.getText().length() > 0) {
                    mStudentProfile.setNoHpMhs(tvNoHP.getText().toString());
                } else {
                    mStudentProfile.setNoHpMhs("-");
                }
                if (tvEmail.getText().length() > 0) {
                    mStudentProfile.setEmailMhs(tvEmail.getText().toString());
                } else {
                    mStudentProfile.setEmailMhs("-");
                }

                mStudentProfile.setJenisKelaminMhs(rbGender.getText().toString());

                updateData(view);
            }
        });
        Log.d(TAG, dataPribadi.getNamaMhs());
    }
    private void updateData(final View view) {
        mStudentProfile.setNpm(Preference.getLoggedNPM(getContext()));
        StudentProfileRequest studentProfileRequest = new StudentProfileRequest();
        studentProfileRequest.setStudentProfile(mStudentProfile);

        Call<ResponseBody> updateResult = APIClient.getMahasiswaService().updateDataProfil(studentProfileRequest, "Bearer " + Preference.getLoggedToken(getContext()));
        updateResult.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObjectResult = new JSONObject(response.body().string());
                        if (jsonObjectResult.getString("code").equals("404")) {
                            Snackbar.make(view , "Incorrect email or password", Snackbar.LENGTH_LONG)
                                    .show();
                            return;
                        }

                        if (jsonObjectResult.getString("code").equals("200")) {

                            Snackbar.make(view, "Update Data Berhasil", Snackbar.LENGTH_LONG).show();
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