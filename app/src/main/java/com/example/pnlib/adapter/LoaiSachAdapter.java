package com.example.pnlib.adapter;



import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlib.R;
import com.example.pnlib.dao.LoaiSachDao;
import com.example.pnlib.fragment.frgLoaiSach;
import com.example.pnlib.model.LoaiSach;
import com.example.pnlib.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachAdapter extends ArrayAdapter<LoaiSach> {
    private  Context context;
    private  ArrayList<LoaiSach> list;
    frgLoaiSach frgLoaiSach;

    TextView txtMaLoai, txtTenLoai;
    ImageButton imgBtnDelete;


    public LoaiSachAdapter(@NonNull Context context, frgLoaiSach frgLoaiSach, ArrayList<LoaiSach> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.frgLoaiSach = frgLoaiSach;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_loaisach, null);
        }

        final LoaiSach item = list.get(position);
        if (item != null){
            txtMaLoai = v.findViewById(R.id.txtMaLoai);
            txtMaLoai.setText("Mã loại: " + item.getMaLoai());
            txtTenLoai = v.findViewById(R.id.txtTenLoai);
            txtTenLoai.setText("Tên loại: " + item.getTenLoai());
            imgBtnDelete = v.findViewById(R.id.imgBtnDelete);
        }

        imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frgLoaiSach.xoa(String.valueOf(item.getMaLoai()));
            }
        });

        return v;
    }
}
