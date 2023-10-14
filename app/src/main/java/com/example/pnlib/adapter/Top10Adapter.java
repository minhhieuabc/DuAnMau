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
import com.example.pnlib.fragment.frgLoaiSach;
import com.example.pnlib.fragment.frgTop10;
import com.example.pnlib.model.LoaiSach;
import com.example.pnlib.model.Top;

import java.util.ArrayList;

public class Top10Adapter extends ArrayAdapter<Top> {
    private Context context;
    private ArrayList<Top> list;
    frgTop10 frgTop10;
    TextView txtTenTop, txtSoLuongTop;

    public Top10Adapter(@NonNull Context context, frgTop10 frgTop10, ArrayList<Top> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.frgTop10 = frgTop10;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_top10, null);
        }

        final Top item = list.get(position);
        if (item != null){
            txtTenTop = v.findViewById(R.id.txtTenTop);
            txtTenTop.setText("Tên sách: " + item.getTenSach());
            txtSoLuongTop = v.findViewById(R.id.txtSoLuongTop);
            txtSoLuongTop.setText("Số lượng: " + item.getSoLuong());
        }



        return v;
    }
}
