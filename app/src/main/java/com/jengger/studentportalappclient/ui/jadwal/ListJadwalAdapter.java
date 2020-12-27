package com.jengger.studentportalappclient.ui.jadwal;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jengger.studentportalappclient.R;

import java.util.ArrayList;

public class ListJadwalAdapter extends RecyclerView.Adapter<ListJadwalAdapter.ListViewHolder> {
    private ArrayList<Jadwal> listJadwal;
    private String TAG = ListJadwalAdapter.class.getSimpleName();

    public ListJadwalAdapter(ArrayList<Jadwal> list) {
        this.listJadwal = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_perkuliahan, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Jadwal jadwal = listJadwal.get(position);

        Log.d(TAG, jadwal.getKodeMK() + String.valueOf(listJadwal));
        holder.tvKode.setText(jadwal.getKodeMK());
        holder.tvNama.setText(jadwal.getNamaMK());
        holder.tvDosen.setText(jadwal.getDosenMK());
        holder.tvJam.setText(jadwal.getJamMK());
        holder.tvRuang.setText(jadwal.getRuangMK());
    }

    @Override
    public int getItemCount() {
        return listJadwal.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvKode, tvNama, tvDosen, tvJam, tvRuang;

        public ListViewHolder(@NonNull View view) {
            super(view);

            tvKode = view.findViewById(R.id.jk_kode);
            tvJam = view.findViewById(R.id.jk_jam);
            tvNama = view.findViewById(R.id.jk_mk);
            tvDosen = view.findViewById(R.id.jk_dosen);
            tvRuang = view.findViewById(R.id.jk_ruang);
        }
    }
}
