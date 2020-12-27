package com.jengger.studentportalappclient.ui.profile.edit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.jengger.studentportalappclient.R;
import com.jengger.studentportalappclient.api.APIClient;
import com.jengger.studentportalappclient.api.Model.ParentRequest;
import com.jengger.studentportalappclient.api.Model.Parents;
import com.jengger.studentportalappclient.share_pref.Preference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDataOrtuFragment extends Fragment {
    public static String KEY_DATA_ORANGTUA_EXTRA = "key_data_orangtua_extra";
    private TextView tvNIK, tvNama, tvNIP, tvPangkat, tvInstansi, tvTanggal;
    private LinearLayout btnSimpan;
    Parents mDataOrangTua = new Parents();

    private String TAG = EditDataOrtuFragment.class.getSimpleName();
    String[] pendidikan = {
            "Tidak Sekolah",
            "PAUD",
            "TK / Sederajat",
            "Putus SD",
            "SD / Sederajat",
            "SMP / Sederajat",
            "SMA / Sederajat",
            "Paket A",
            "Paket B",
            "Paket C",
            "D1",
            "D2",
            "D3",
            "S1",
            "Profesi",
            "Sp-1",
            "S2",
            "Sp-2",
            "S3",
            "Postdoc"
    };

    String[] pekerjaan = {
            "Tidak bekerja",
            "Nelayan",
            "Petani",
            "Peternak",
            "PNS/TNI/Polri",
            "Karyawan Swasta",
            "Pedagang Kecil",
            "Pedagang Besar",
            "Wiraswasta",
            "Wirausaha",
            "Buruh",
            "Pensiunan",
            "Sudah Meninggal",
            "Lainnya"
    };

    String[] penghasilan = {
            "Kurang dari Rp. 500.000",
            "Rp. 500.000 - Rp. 999.999",
            "Rp. 999.000 - Rp. 1.999.999",
            "Rp. 2.000.000 - Rp. 4.999.999",
            "Rp. 5.000.000 - Rp. 20.000.000",
            "Lebih dari Rp. 20.000.000"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_data_ortu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle mBundle = getArguments();
        final Parents dataPribadi = (Parents) mBundle.getSerializable(KEY_DATA_ORANGTUA_EXTRA);

        tvNIK = view.findViewById(R.id.edt_nik);
        tvNIK.setText(dataPribadi.getNikOrtu());

        tvNama = view.findViewById(R.id.edt_nama);
        tvNama.setText(dataPribadi.getNamaOrtu());

        tvInstansi = view.findViewById(R.id.edt_instansi);
        tvInstansi.setText(dataPribadi.getInstansiOrtu());

        tvNIP = view.findViewById(R.id.edt_nip);
        tvNIP.setText(dataPribadi.getNIPOrtu());

        tvPangkat = view.findViewById(R.id.edt_pangkat);
        tvPangkat.setText(dataPribadi.getPangkatOrtu());

        tvTanggal = view.findViewById(R.id.edt_tanggal_lahir);
        tvTanggal.setText(dataPribadi.getTanggalLahirOrtu());

        Button btnBatal = view.findViewById(R.id.btn_batal);
        btnBatal.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_editDataOrtuFragment_to_nav_profileFragment)
        );

        Spinner spinnerPendidikan = view.findViewById(R.id.spinner_pendidikan);
        Spinner spinnerPekerjaan = view.findViewById(R.id.spinner_pekerjaan);
        Spinner spinnerPenghasilan = view.findViewById(R.id.spinner_penghasilan);

        spinnerPendidikan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDataOrangTua.setPendidikanOrtu(pendidikan[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerPekerjaan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDataOrangTua.setPekerjaanOrtu(pekerjaan[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerPenghasilan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDataOrangTua.setPenghasilanOrtu(penghasilan[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter adapterPendidikan = new ArrayAdapter(requireContext(), R.layout.spinner_item_light, pendidikan);
        adapterPendidikan.setDropDownViewResource(R.layout.spinner_dropdown);
        spinnerPendidikan.setAdapter(adapterPendidikan);
        for (int i = 0; i < pendidikan.length; i++) {
            if (String.valueOf(spinnerPendidikan.getItemAtPosition(i)).toLowerCase().contains(dataPribadi.getPendidikanOrtu().toLowerCase())) {
                spinnerPendidikan.setSelection(i);
                break;
            }
        }

        ArrayAdapter adapterPekerjaan = new ArrayAdapter(requireContext(), R.layout.spinner_item_light, pekerjaan);
        adapterPekerjaan.setDropDownViewResource(R.layout.spinner_dropdown);
        spinnerPekerjaan.setAdapter(adapterPekerjaan);
        for (int i = 0; i < pekerjaan.length; i++) {
            if (String.valueOf(spinnerPekerjaan.getItemAtPosition(i)).equals(dataPribadi.getPekerjaanOrtu())) {
                spinnerPekerjaan.setSelection(i);
                break;
            }
        }

        ArrayAdapter adapterPenghasilan = new ArrayAdapter(requireContext(), R.layout.spinner_item_light, penghasilan);
        adapterPenghasilan.setDropDownViewResource(R.layout.spinner_dropdown);
        spinnerPenghasilan.setAdapter(adapterPenghasilan);
        for (int i = 0; i < penghasilan.length; i++) {
            if (String.valueOf(spinnerPenghasilan.getItemAtPosition(i)).equals(dataPribadi.getPenghasilanOrtu())) {
                spinnerPenghasilan.setSelection(i);
                break;
            }
        }

        btnSimpan = view.findViewById(R.id.btn_simpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvNIK.getText().length() > 0) {
                    mDataOrangTua.setNikOrtu(tvNIK.getText().toString());
                } else {
                    mDataOrangTua.setNikOrtu("-");
                }
                if (tvNama.getText().length() > 0) {
                    mDataOrangTua.setNamaOrtu(tvNama.getText().toString());
                } else {
                    mDataOrangTua.setNamaOrtu("-");
                }
                if (tvInstansi.getText().length() > 0) {
                    mDataOrangTua.setInstansiOrtu(tvInstansi.getText().toString());
                } else {
                    mDataOrangTua.setInstansiOrtu("-");
                }
                if (tvNIP.getText().toString().length() > 0) {
                    mDataOrangTua.setNIPOrtu(tvNIP.getText().toString());
                } else {
                    mDataOrangTua.setNIPOrtu("-");
                }
                if (tvPangkat.getText().toString().length() > 0) {
                    mDataOrangTua.setPangkatOrtu(tvPangkat.getText().toString());
                } else {
                    mDataOrangTua.setPangkatOrtu("-");
                }
                if (tvTanggal.getText().toString().length() > 0) {
                    mDataOrangTua.setTanggalLahirOrtu(tvTanggal.getText().toString());
                } else {
                    mDataOrangTua.setTanggalLahirOrtu("-");
                }

                updateData(v);
            }
        });
    }

    private void updateData(final View view) {
        mDataOrangTua.setNpm(Preference.getLoggedNPM(getContext()));
        ParentRequest parentRequest = new ParentRequest();
        parentRequest.setParents(mDataOrangTua);


        Call<ResponseBody> updateResult = APIClient.getOrangtua().updateDataOrangtua(parentRequest, "Bearer " + Preference.getLoggedToken(getContext()));
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