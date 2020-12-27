package com.jengger.studentportalappclient.ui.menu_pilihan.menu_wisuda;

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


public class MenuWisudaFragment extends Fragment {

    CardView cardDaftarWisuda, cardMagang, cardKeterampilan, cardPrestasi, cardOrganisasi, cardBI, cardPernyataan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_wisuda, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardDaftarWisuda = view.findViewById(R.id.card_daftar_wisuda);
        cardPernyataan = view.findViewById(R.id.card_pernyataan);
        cardBI = view.findViewById(R.id.card_b_internasional);
        cardOrganisasi = view.findViewById(R.id.card_organisasi);
        cardMagang = view.findViewById(R.id.card_magang);
        cardKeterampilan = view.findViewById(R.id.card_keterampilan);
        cardPrestasi = view.findViewById(R.id.card_prestasi);

        cardDaftarWisuda.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_menuWisudaFragment_to_daftarWisudaFragment)
        );
        cardMagang.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_menuWisudaFragment_to_magangFragment)
        );
        cardKeterampilan.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_menuWisudaFragment_to_keterampilanFragment)
        );
        cardPrestasi.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_menuWisudaFragment_to_prestasiFragment)
        );
        cardOrganisasi.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_menuWisudaFragment_to_organisasiFragment)
        );
        cardBI.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_menuWisudaFragment_to_BInternasionalFragment)
        );
        cardPernyataan.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_menuWisudaFragment_to_pernyataanFragment)
        );
    }
}