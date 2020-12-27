package com.jengger.studentportalappclient.ui.detail_pengumuman;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jengger.studentportalappclient.R;
import com.jengger.studentportalappclient.api.Model.StatusMahasiswa;

public class DetailPengumumanFragment extends Fragment {

    TextView tvJudul, tvIsi, tvTanggal, tvJam, tvPengirim, tvKategori;
    Button btnKembali;

    public static String EXTRA_JUDUL= "extra_judul";
    public static String EXTRA_KATEOORI= "extra_kategori";
    public static String EXTRA_JAM= "extra_jam";
    public static String EXTRA_ISI= "extra_isi";
    public static String EXTRA_TANGGAL= "extra_tanggal";
    public static String EXTRA_PENGIRIM= "extra_pengirim";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_pengumuman, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String judul = getArguments().getString(EXTRA_JUDUL);
        String jam = getArguments().getString(EXTRA_JAM);
        String tanggal = getArguments().getString(EXTRA_TANGGAL);
        String kategori = getArguments().getString(EXTRA_KATEOORI);
        String pengirim = getArguments().getString(EXTRA_PENGIRIM);
        String isi = getArguments().getString(EXTRA_ISI);

        tvJam.setText(jam);
        tvKategori.setText(kategori);
        tvIsi.setText(isi);
        tvJudul.setText(judul);
        tvTanggal.setText(tanggal);
        tvPengirim.setText(pengirim);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvJam = view.findViewById(R.id.pengumuman_jam);
        tvJudul = view.findViewById(R.id.pengumuman_judul);
        tvIsi = view.findViewById(R.id.pengumuman_isi);
        tvTanggal = view.findViewById(R.id.pengumuman_tanggal);
        tvPengirim = view.findViewById(R.id.pengumuman_pengirim);
        tvKategori = view.findViewById(R.id.pengumuman_kategori);
        btnKembali = view.findViewById(R.id.btn_kembali);
        btnKembali.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_nav_detailPengumumanFragment_to_nav_pengumumanFragment)
        );
    }

}