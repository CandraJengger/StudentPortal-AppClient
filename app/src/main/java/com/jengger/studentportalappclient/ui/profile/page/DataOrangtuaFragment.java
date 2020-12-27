package com.jengger.studentportalappclient.ui.profile.page;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jengger.studentportalappclient.R;
import com.jengger.studentportalappclient.api.APIClient;
import com.jengger.studentportalappclient.api.Model.BundleOrtu;
import com.jengger.studentportalappclient.api.Model.Parents;
import com.jengger.studentportalappclient.share_pref.Preference;
import com.jengger.studentportalappclient.ui.profile.BottomDialogButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataOrangtuaFragment extends Fragment {
    private TextView tvNamaAyah, tvInstansiAyah, tvPekerjaanAyah,
            tvNamaIbu, tvInstansiIbu, tvPekerjaanIbu,
            tvNamaWali, tvInstansiWali, tvPekerjaanWali;
    private ProgressBar progressBar;
    private Button btnUbahDataOrtu;
    Parents mParentsAyah = new Parents();
    Parents mParentsIbu = new Parents();
    Parents mParentsWali = new Parents();
    BundleOrtu mBundleOrtu = new BundleOrtu();

    private String TAG = DataOrangtuaFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_orangtua, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvNamaAyah = view.findViewById(R.id.do_nama_ayah);
        tvInstansiAyah = view.findViewById(R.id.do_instansi_ayah);
        tvPekerjaanAyah = view.findViewById(R.id.do_pekerjaan_ayah);
        tvNamaIbu = view.findViewById(R.id.do_nama_ibu);
        tvInstansiIbu = view.findViewById(R.id.do_instansi_ibu);
        tvPekerjaanIbu = view.findViewById(R.id.do_pekerjaan_ibu);
        tvNamaWali = view.findViewById(R.id.do_nama_wali);
        tvInstansiWali = view.findViewById(R.id.do_instansi_wali);
        tvPekerjaanWali = view.findViewById(R.id.do_pekerjaan_wali);
        progressBar = view.findViewById(R.id.progressBar);
        btnUbahDataOrtu = view.findViewById(R.id.btn_ubah_data_ortu);

        showLoading(true);
        getDataOrangtua(Preference.getLoggedNPM(getContext()), "Bearer " + Preference.getLoggedToken(getContext()));

        btnUbahDataOrtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mBundle = new Bundle();
                mBundleOrtu.setParentsAyah(mParentsAyah);
                mBundleOrtu.setParentsIbu(mParentsIbu);
                mBundleOrtu.setParentsWali(mParentsWali);

                mBundle.putSerializable(BottomDialogButton.KEY_BOTTOM_EXTRA, mBundleOrtu);
                Navigation.findNavController(v).navigate(R.id.bottomDialogButton, mBundle);
            }
        });
    }

    private void getDataOrangtua(int npm, final String token) {
        Call<ResponseBody> dataOrangTuaResult = APIClient.getMahasiswaService().getOrtuMahasiswa(npm, token);
        dataOrangTuaResult.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray dataArray = jsonObject.getJSONArray("values");

                        for (int i = 0; i < dataArray.length(); i++) {
                            if (dataArray.getJSONObject(i).getString("STATUS_ORTU").equals("Ayah")) {
                                Log.d(TAG, dataArray.getJSONObject(i).getString("STATUS_ORTU"));
                                tvNamaAyah.setText(dataArray.getJSONObject(i).getString("NAMA_ORTU"));
                                tvPekerjaanAyah.setText(dataArray.getJSONObject(i).getString("PEKERJAAN_ORTU"));
                                tvInstansiAyah.setText(dataArray.getJSONObject(i).getString("INSTANSI_ORTU"));

                                Preference.setKeyNikAyah(getContext(), dataArray.getJSONObject(i).getString("NIK_ORTU"));

                                mParentsAyah.setNikOrtu(Preference.getKeyNikAyah(getContext()));
                                mParentsAyah.setNamaOrtu(dataArray.getJSONObject(i).getString("NAMA_ORTU"));
                                mParentsAyah.setInstansiOrtu(dataArray.getJSONObject(i).getString("INSTANSI_ORTU"));
                                mParentsAyah.setNIPOrtu(dataArray.getJSONObject(i).getString("NIP_ORTU"));
                                mParentsAyah.setPangkatOrtu(dataArray.getJSONObject(i).getString("PANGKAT_ORTU"));
                                mParentsAyah.setPekerjaanOrtu(dataArray.getJSONObject(i).getString("PEKERJAAN_ORTU"));
                                mParentsAyah.setPendidikanOrtu(dataArray.getJSONObject(i).getString("PENDIDIKAN_ORTU"));
                                mParentsAyah.setPenghasilanOrtu(dataArray.getJSONObject(i).getString("PENGHASILAN_ORTU"));
                                mParentsAyah.setTanggalLahirOrtu(dataArray.getJSONObject(i).getString("TANGGAL_LAHIR_ORTU"));
                                mParentsAyah.setStatusOrtu(dataArray.getJSONObject(i).getString("STATUS_ORTU"));
                            }

                            if (dataArray.getJSONObject(i).getString("STATUS_ORTU").equals("Ibu")) {
                                tvNamaIbu.setText(dataArray.getJSONObject(i).getString("NAMA_ORTU"));
                                Log.d(TAG, dataArray.getJSONObject(i).getString("STATUS_ORTU") +tvNamaIbu);
                                tvPekerjaanIbu.setText(dataArray.getJSONObject(i).getString("PEKERJAAN_ORTU"));
                                tvInstansiIbu.setText(dataArray.getJSONObject(i).getString("INSTANSI_ORTU"));

                                Preference.setKeyNikIbu(getContext(), dataArray.getJSONObject(i).getString("NIK_ORTU"));
                                mParentsIbu.setNikOrtu(Preference.getKeyNikIbu(getContext()));
                                mParentsIbu.setNamaOrtu(dataArray.getJSONObject(i).getString("NAMA_ORTU"));
                                mParentsIbu.setInstansiOrtu(dataArray.getJSONObject(i).getString("INSTANSI_ORTU"));
                                mParentsIbu.setNIPOrtu(dataArray.getJSONObject(i).getString("NIP_ORTU"));
                                mParentsIbu.setPangkatOrtu(dataArray.getJSONObject(i).getString("PANGKAT_ORTU"));
                                mParentsIbu.setPekerjaanOrtu(dataArray.getJSONObject(i).getString("PEKERJAAN_ORTU"));
                                mParentsIbu.setPendidikanOrtu(dataArray.getJSONObject(i).getString("PENDIDIKAN_ORTU"));
                                mParentsIbu.setPenghasilanOrtu(dataArray.getJSONObject(i).getString("PENGHASILAN_ORTU"));
                                mParentsIbu.setTanggalLahirOrtu(dataArray.getJSONObject(i).getString("TANGGAL_LAHIR_ORTU"));
                                mParentsIbu.setStatusOrtu(dataArray.getJSONObject(i).getString("STATUS_ORTU"));
                            }

                            if (dataArray.getJSONObject(i).getString("STATUS_ORTU").equals("Wali")) {
                                Log.d(TAG, dataArray.getJSONObject(i).getString("STATUS_ORTU"));
                                tvNamaWali.setText(dataArray.getJSONObject(i).getString("NAMA_ORTU"));
                                tvPekerjaanWali.setText(dataArray.getJSONObject(i).getString("PEKERJAAN_ORTU"));
                                tvInstansiWali.setText(dataArray.getJSONObject(i).getString("INSTANSI_ORTU"));

                                Preference.setKeyNikWali(getContext(), dataArray.getJSONObject(i).getString("NIK_ORTU"));
                                mParentsWali.setNikOrtu(Preference.getKeyNikWali(getContext()));
                                mParentsWali.setNamaOrtu(dataArray.getJSONObject(i).getString("NAMA_ORTU"));
                                mParentsWali.setInstansiOrtu(dataArray.getJSONObject(i).getString("INSTANSI_ORTU"));
                                mParentsWali.setNIPOrtu(dataArray.getJSONObject(i).getString("NIP_ORTU"));
                                mParentsWali.setPangkatOrtu(dataArray.getJSONObject(i).getString("PANGKAT_ORTU"));
                                mParentsWali.setPekerjaanOrtu(dataArray.getJSONObject(i).getString("PEKERJAAN_ORTU"));
                                mParentsWali.setPendidikanOrtu(dataArray.getJSONObject(i).getString("PENDIDIKAN_ORTU"));
                                mParentsWali.setPenghasilanOrtu(dataArray.getJSONObject(i).getString("PENGHASILAN_ORTU"));
                                mParentsWali.setTanggalLahirOrtu(dataArray.getJSONObject(i).getString("TANGGAL_LAHIR_ORTU"));
                                mParentsWali.setStatusOrtu(dataArray.getJSONObject(i).getString("STATUS_ORTU"));
                            }
                        }

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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "Attach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "Detach");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "resume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "DestroyView");
    }

    @Override
    public void onStop() {
        super.onStop();
        getParentFragmentManager().beginTransaction().remove(this);
        Log.d(TAG, "Destroy");
    }
}