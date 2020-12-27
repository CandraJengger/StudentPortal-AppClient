package com.jengger.studentportalappclient.ui.menu_pilihan.menu_kuliah;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jengger.studentportalappclient.R;


public class MenuKuliahFragment extends Fragment {

    CardView cardJadwalKuliah, cardPresensi, cardKalender, cardLMS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_kuliah, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardJadwalKuliah = view.findViewById(R.id.card_jadwal_kuliah);
        cardPresensi = view.findViewById(R.id.card_presensi);
        cardKalender = view.findViewById(R.id.card_kalender);
        cardLMS = view.findViewById(R.id.card_lms);

        cardJadwalKuliah.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_nav_menuKuliahFragment_to_nav_jadwalFragment)
        );

        cardPresensi.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_nav_menuKuliahFragment_to_nav_presensiFragment)
        );

        cardKalender.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_nav_menuKuliahFragment_to_kalenderFragment)
        );

        cardLMS.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_nav_menuKuliahFragment_to_nav_LMSFragment)
        );
    }
}