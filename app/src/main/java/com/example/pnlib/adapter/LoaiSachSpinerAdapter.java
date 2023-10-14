package com.example.pnlib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pnlib.R;
import com.example.pnlib.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachSpinerAdapter extends ArrayAdapter<LoaiSach> {
    Context context;
    ArrayList<LoaiSach> list;
    TextView tvMaLoaiSach, tvTenLoaiSach;

    public LoaiSachSpinerAdapter(@NonNull Context context, ArrayList<LoaiSach> list) {

        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loai_sach_item_spiner, null);
        }
        final  LoaiSach item = list.get(position);
        if (item != null){
            tvMaLoaiSach = v.findViewById(R.id.txtMaLoaiSachsp);
            tvMaLoaiSach.setText(item.getMaLoai() + ".");

            tvTenLoaiSach = v.findViewById(R.id.txtTenLoaiSachsp);
            tvTenLoaiSach.setText(item.getTenLoai() + ".");
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loai_sach_item_spiner, null);
        }
        final  LoaiSach item = list.get(position);
        if (item != null){
            tvMaLoaiSach = v.findViewById(R.id.txtMaLoaiSachsp);
            tvMaLoaiSach.setText(item.getMaLoai() + ".");

            tvTenLoaiSach = v.findViewById(R.id.txtTenLoaiSachsp);
            tvTenLoaiSach.setText(item.getTenLoai() + ".");
        }
        return v;
    }
}
