package com.jengger.studentportalappclient.ui.wisuda.prestasi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jengger.studentportalappclient.R;

public class TambahPrestasiFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tambah_prestasi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnBatal = view.findViewById(R.id.btn_batal);
//        btnBatal.setOnClickListener(
//                Navigation.createNavigateOnClickListener(R.id.action_tambahPrestasiFragment_to_prestasiFragment)
//        );

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_tambahPrestasiFragment_to_prestasiFragment);
            }
        });
    }
}