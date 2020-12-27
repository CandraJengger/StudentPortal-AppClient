package com.jengger.studentportalappclient.ui.pengumuman;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jengger.studentportalappclient.R;

import java.util.ArrayList;

public class ListPengumumanAdapter extends RecyclerView.Adapter<ListPengumumanAdapter.ListViewHolder> {

    private ArrayList<Pengumuman> listPengumuman;

    public ListPengumumanAdapter(ArrayList<Pengumuman> list) {
        this.listPengumuman = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_pengumuman, parent, false);
        return new ListViewHolder(view);
    }

    private OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        Pengumuman pengumuman = listPengumuman.get(position);

        holder.tvJudul.setText(pengumuman.getJudul());
        holder.tvTanggal.setText(pengumuman.getTanggal());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listPengumuman.get(holder.getAdapterPosition()), v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPengumuman.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView tvJudul, tvTanggal;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvJudul = itemView.findViewById(R.id.pengumuman_judul);
            tvTanggal = itemView.findViewById(R.id.pengumuman_tgl);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Pengumuman data, View view);
    }
}
