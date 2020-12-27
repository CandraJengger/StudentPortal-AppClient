package com.jengger.studentportalappclient.ui.pengumuman;

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
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jengger.studentportalappclient.R;
import com.jengger.studentportalappclient.api.APIClient;
import com.jengger.studentportalappclient.share_pref.Preference;
import com.jengger.studentportalappclient.ui.detail_pengumuman.DetailPengumumanFragment;
import com.jengger.studentportalappclient.ui.pengaturan.PengaturanFragment;

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


public class PengumumanFragment extends Fragment {
    private RecyclerView rvPengumuman;
    private ArrayList<Pengumuman> list = new ArrayList<>();
    private String TAG = PengumumanFragment.class.getSimpleName();

    String[] kategori = {
            "Semua Kategori",
            "Informasi",
            "Peringatan",
            "Kuisioner",
            "Download"
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pengumuman, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /* Semester */
        Spinner spinnerSemester = (Spinner) view.findViewById(R.id.spinner_kategori);
        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "Selected "+ kategori[position], Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    getSemuaPengumuman("Bearer " + Preference.getLoggedToken(getContext()));
                } else {
                   getKategoriPengumuman(kategori[position], "Bearer " + Preference.getLoggedToken(getContext()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        @SuppressLint("ResourceType") final ArrayAdapter adapterSemester = new ArrayAdapter(requireContext(), R.layout.spinner_item, kategori);
        adapterSemester.setDropDownViewResource(R.layout.spinner_dropdown);
        spinnerSemester.setAdapter(adapterSemester);

        rvPengumuman = view.findViewById(R.id.rv_pengumuman);

    }

    public void showRecyclerView() {
        rvPengumuman.setLayoutManager(new LinearLayoutManager(getActivity()));
        final ListPengumumanAdapter listPengumumanAdapter = new ListPengumumanAdapter(list);
        rvPengumuman.setAdapter(listPengumumanAdapter);

        listPengumumanAdapter.setOnItemClickCallback(new ListPengumumanAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Pengumuman data, View view) {
                showSelectedHero(data, view);
            }
        });
    }

    private void showSelectedHero(Pengumuman hero, View view) {
        Bundle mBundle = new Bundle();
        mBundle.putString(DetailPengumumanFragment.EXTRA_JUDUL, hero.getJudul());
        mBundle.putString(DetailPengumumanFragment.EXTRA_ISI, hero.getIsi());
        mBundle.putString(DetailPengumumanFragment.EXTRA_TANGGAL, hero.getTanggal());
        mBundle.putString(DetailPengumumanFragment.EXTRA_JAM, hero.getJam());
        mBundle.putString(DetailPengumumanFragment.EXTRA_KATEOORI, hero.getKategori());
        mBundle.putString(DetailPengumumanFragment.EXTRA_PENGIRIM, hero.getPengirim());

        Navigation.findNavController(view).navigate(R.id.action_nav_pengumumanFragment_to_nav_detailPengumumanFragment, mBundle);
//        Toast.makeText(getActivity(), "Kamu memilih " + hero.getJudul(), Toast.LENGTH_SHORT).show();
    }

    private void getSemuaPengumuman(String token) {
        final ArrayList<Pengumuman> listPengumuman = new ArrayList<>();

        Call<ResponseBody> pengumumanResult = APIClient.getPengumuman().getPengumuman(token);
        pengumumanResult.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObjectResult = new JSONObject(response.body().string());
                        if (jsonObjectResult.getString("values").length() > 0) {
                            JSONArray dataArray = jsonObjectResult.getJSONArray("values");

                            Pengumuman pengumuman = new Pengumuman();

                            list.clear();
                            listPengumuman.clear();

                            for (int i = 0; i < dataArray.length() ;i++) {
                                pengumuman.setJudul(dataArray.getJSONObject(i).getString("JUDUL_PENGUMUMAN"));
                                pengumuman.setIsi(dataArray.getJSONObject(i).getString("ISI_PENGUMUMAN"));
                                pengumuman.setKategori(dataArray.getJSONObject(i).getString("KATEGORI_PENGUMUMAN"));
                                pengumuman.setTanggal(dataArray.getJSONObject(i).getString("TANGGAL_PENGUMUMAN"));
                                pengumuman.setPengirim(dataArray.getJSONObject(i).getString("PENGIRIM_PENGUMUMAN"));
                                pengumuman.setJam(dataArray.getJSONObject(i).getString("JAM_PENGUMUMAN"));

                                listPengumuman.add(pengumuman);
                            }

                            list.addAll(listPengumuman);
                            showRecyclerView();
                        } else {
                            list.clear();
                            listPengumuman.clear();
                            showRecyclerView();
//                            Toast.makeText(getContext(), "Selected ", Toast.LENGTH_SHORT).show();
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

    private void getKategoriPengumuman(String kategori, String token) {
        final ArrayList<Pengumuman> listPengumuman = new ArrayList<>();

        Call<ResponseBody> pengumumanResult = APIClient.getPengumuman().getPengumumanKategori(kategori, token);
        pengumumanResult.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObjectResult = new JSONObject(response.body().string());
                        if (jsonObjectResult.getString("values").length() > 0) {
                            JSONArray dataArray = jsonObjectResult.getJSONArray("values");

                            list.clear();
                            listPengumuman.clear();

                            for (int i = 0; i < dataArray.length() ;i++) {
                                Pengumuman pengumuman = new Pengumuman();

                                pengumuman.setJudul(dataArray.getJSONObject(i).getString("JUDUL_PENGUMUMAN"));
                                pengumuman.setIsi(dataArray.getJSONObject(i).getString("ISI_PENGUMUMAN"));
                                pengumuman.setKategori(dataArray.getJSONObject(i).getString("KATEGORI_PENGUMUMAN"));
                                pengumuman.setTanggal(dataArray.getJSONObject(i).getString("TANGGAL_PENGUMUMAN"));
                                pengumuman.setPengirim(dataArray.getJSONObject(i).getString("PENGIRIM_PENGUMUMAN"));
                                pengumuman.setJam(dataArray.getJSONObject(i).getString("JAM_PENGUMUMAN"));

                                listPengumuman.add(pengumuman);
                            }

                            list.addAll(listPengumuman);
                            showRecyclerView();
                        } else {
                            list.clear();
                            listPengumuman.clear();
                            showRecyclerView();
//                            Toast.makeText(getContext(), "Selected ", Toast.LENGTH_SHORT).show();
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