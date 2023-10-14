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
import com.example.pnlib.dao.SachDao;
import com.example.pnlib.fragment.frgSach;
import com.example.pnlib.fragment.frgThanhVien;
import com.example.pnlib.model.LoaiSach;
import com.example.pnlib.model.Sach;
import com.example.pnlib.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class SachAdapter extends ArrayAdapter<Sach> {
    private Context context;
    frgSach frgSach;
    private ArrayList<Sach> list;

    TextView txtMaSach, txtTenSach, txtLSsach, txtGiaThue;
    ImageButton imgBtnDelete;


    public SachAdapter(@NonNull Context context, frgSach frgSach, List<Sach> list) {
        super(context, 0, list);
        this.context = context;
        this.frgSach = frgSach;
        this.list = (ArrayList<Sach>) list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_sach, null);
        }

        final Sach item = list.get(position);
        if (item != null){
            LoaiSachDao loaiSachDao = new LoaiSachDao(context);
            LoaiSach loaiSach = loaiSachDao.getID(String.valueOf(item.getMaLoai()));
            txtMaSach = v.findViewById(R.id.txtMaSach);
            txtMaSach.setText("Mã sách: " + item.getMaSach());
            txtTenSach = v.findViewById(R.id.txtTenSach);
            txtTenSach.setText("Tên sách: " + item.getTenSach());
            txtGiaThue = v.findViewById(R.id.txtGiaThue);
            txtGiaThue.setText("Giá thuê: " + item.getGiaThue());
            txtLSsach = v.findViewById(R.id.txtLSsach);
            txtLSsach.setText("Loại sách: " + item.getMaLoai());
            imgBtnDelete = v.findViewById(R.id.imgBtnDelete);
        }
        imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frgSach.xoa(String.valueOf(item.getMaSach()));
            }
        });

        return v;
    }
}
