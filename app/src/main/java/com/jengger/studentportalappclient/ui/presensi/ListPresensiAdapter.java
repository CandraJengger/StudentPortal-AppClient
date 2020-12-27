package com.jengger.studentportalappclient.ui.presensi;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jengger.studentportalappclient.R;

import java.util.ArrayList;

public class ListPresensiAdapter extends RecyclerView.Adapter<ListPresensiAdapter.ListViewHolder> {

    private ArrayList<Presensi> listPresensi;

    public ListPresensiAdapter(ArrayList<Presensi> list) {
        this.listPresensi = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_presensi, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Presensi presensi = listPresensi.get(position);

        holder.tvMK.setText(presensi.getNama());
        holder.tvStatus.setText(presensi.getStatus());
        if (presensi.getStatus().equals("izin")) {
            holder.tvStatus.setBackgroundColor(Color.parseColor("#FFEB31"));
        }
        if (presensi.getStatus().equals("alpha")) {
            holder.tvStatus.setBackgroundColor(Color.parseColor("#EB6D7A"));
        }
    }

    @Override
    public int getItemCount() {
        return listPresensi.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView tvMK, tvStatus;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);


            tvMK = itemView.findViewById(R.id.presensi_mk);
            tvStatus = itemView.findViewById(R.id.presensi_status);
        }
    }
}
