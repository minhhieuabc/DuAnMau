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
import com.example.pnlib.model.Sach;

import java.util.ArrayList;

public class SachSpinerAdapter extends ArrayAdapter<Sach> {
    Context context;
    ArrayList<Sach> list;
    TextView tvMaSach, tvTenSach;

    public SachSpinerAdapter(@NonNull Context context, ArrayList<Sach> list) {

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
            v = inflater.inflate(R.layout.sach_item_spiner, null);
        }
        final  Sach item = list.get(position);
        if (item != null){
            tvMaSach = v.findViewById(R.id.txtMaSachsp);
            tvMaSach.setText(item.getMaSach() + ".");

            tvTenSach = v.findViewById(R.id.txtTenSachsp);
            tvTenSach.setText(item.getTenSach() + ".");
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.sach_item_spiner, null);
        }
        final  Sach item = list.get(position);
        if (item != null){
            tvMaSach = v.findViewById(R.id.txtMaSachsp);
            tvMaSach.setText(item.getMaSach() + ".");

            tvTenSach = v.findViewById(R.id.txtTenSachsp);
            tvTenSach.setText(item.getTenSach() + ".");
        }
        return v;
    }
}
