package com.example.pnlib.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pnlib.R;
import com.example.pnlib.dao.SachDao;
import com.example.pnlib.dao.ThanhVienDao;
import com.example.pnlib.fragment.frgPhieuMuon;
import com.example.pnlib.model.PhieuMuon;
import com.example.pnlib.model.Sach;
import com.example.pnlib.model.ThanhVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PhieuMuonAdapter extends ArrayAdapter<PhieuMuon> {
    frgPhieuMuon frgPhieuMuon;
    private Context context;
    private ArrayList<PhieuMuon> list;
    TextView tvMaPM, tvTenTV, tvTenSach, tvTienThue, tvNgay, tvTraSach;
    ImageButton imgBtnDelete;
    SachDao sachDao;
    ThanhVienDao thanhVienDao;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public PhieuMuonAdapter(@NonNull Context context, frgPhieuMuon frgPhieuMuon, ArrayList<PhieuMuon> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.frgPhieuMuon = frgPhieuMuon;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_phieumuon, null);
        }

        final PhieuMuon item = list.get(position);
        if (item != null){
            tvMaPM = v.findViewById(R.id.txtMaPM);
            tvMaPM.setText("Mã phiếu: " + item.getMaPM());

            sachDao = new SachDao(context);
            Sach sach = sachDao.getID(String.valueOf(item.getMaSach()));
            tvTenSach = v.findViewById(R.id.txtTenSachPM);
            tvTenSach.setText("Tên sách: " + sach.getTenSach());
            thanhVienDao = new ThanhVienDao(context);
            ThanhVien thanhVien = thanhVienDao.getID(String.valueOf(item.getMaTV()));
            tvTenTV = v.findViewById(R.id.txtTenTVPM);
            tvTenTV.setText("Thành viên: " + thanhVien.getHoTenTV());
            tvTienThue = v.findViewById(R.id.txtTienThue);
            tvTienThue.setText("Tiền thuê: " + item.getTienThue());
            tvNgay = v.findViewById(R.id.txtNgayThue);
            tvNgay.setText("Ngày thuê: " + sdf.format(item.getNgay()));
            tvTraSach = v.findViewById(R.id.txtTraSach);
            if (item.getTraSach() == 1){
                tvTraSach.setTextColor(Color.BLUE);
                tvTraSach.setText("Đã trả sách");
            } else {
                tvTraSach.setTextColor(Color.RED);
                tvTraSach.setText("Chưa trả sách");
            }
            imgBtnDelete = v.findViewById(R.id.imgBtnDelete);
        }
        imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frgPhieuMuon.xoa(String.valueOf(item.getMaPM()));
            }
        });
        return v;
    }
}
