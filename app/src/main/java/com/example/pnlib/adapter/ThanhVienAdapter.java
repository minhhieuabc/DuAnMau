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
import com.example.pnlib.dao.ThanhVienDao;
import com.example.pnlib.fragment.frgThanhVien;
import com.example.pnlib.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienAdapter extends ArrayAdapter<ThanhVien> {
    private Context context;
    frgThanhVien frgThanhVien;
    private ArrayList<ThanhVien> list;

    TextView txtMaTV, txtTenTV, txtNamSinh;
    ImageButton imgBtnDelete;

    ThanhVien thanhVien;

    public ThanhVienAdapter(@NonNull Context context, frgThanhVien frgThanhVien, ArrayList<ThanhVien> list) {
        super(context, 0, list);
        this.context = context;
        this.frgThanhVien = frgThanhVien;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_thanhvien, null);
        }

        final ThanhVien item = list.get(position);
        if (item != null){
            txtMaTV = v.findViewById(R.id.txtMaTV);
            txtMaTV.setText("Mã thành viên: " + item.getMaTV());
            txtTenTV = v.findViewById(R.id.txtTenTV);
            txtTenTV.setText("Tên thành viên: " + item.getHoTenTV());
            txtNamSinh = v.findViewById(R.id.txtNamSinh);
            txtNamSinh.setText("Năm sinh: " + item.getNamSinh());
            imgBtnDelete = v.findViewById(R.id.imgBtnDelete);
        }

        imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frgThanhVien.xoa(String.valueOf(item.getMaTV()));
            }
        });

        return v;
    }
}
