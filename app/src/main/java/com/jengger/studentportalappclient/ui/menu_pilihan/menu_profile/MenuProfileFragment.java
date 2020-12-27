package com.jengger.studentportalappclient.ui.menu_pilihan.menu_profile;

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


public class MenuProfileFragment extends Fragment {

    CardView cardDataProfil, cardRekapPresensi, cardHasilStudi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_menu_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardDataProfil = view.findViewById(R.id.card_data_profil);
        cardRekapPresensi = view.findViewById(R.id.card_rekap_presensi);
        cardHasilStudi = view.findViewById(R.id.card_hasil_studi);

        cardDataProfil.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_nav_menuProfileFragment_to_nav_profileFragment)
        );
        cardRekapPresensi.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_nav_menuProfileFragment_to_nav_rekapPresensiFragment)
        );
        cardHasilStudi.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_nav_menuProfileFragment_to_nav_hasilStudiragment)
        );

    }
}