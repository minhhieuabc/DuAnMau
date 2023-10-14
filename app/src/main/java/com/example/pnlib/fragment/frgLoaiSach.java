package com.example.pnlib.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pnlib.R;
import com.example.pnlib.adapter.LoaiSachAdapter;
import com.example.pnlib.adapter.ThanhVienAdapter;
import com.example.pnlib.dao.LoaiSachDao;
import com.example.pnlib.model.LoaiSach;
import com.example.pnlib.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frgLoaiSach#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgLoaiSach extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frgLoaiSach() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgLoaiSach.
     */
    // TODO: Rename and change types and number of parameters
    public static frgLoaiSach newInstance(String param1, String param2) {
        frgLoaiSach fragment = new frgLoaiSach();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    ListView lvLoaiSach;
    FloatingActionButton fltAdd;
    ArrayList<LoaiSach> list;
    LoaiSachDao loaiSachDao;
    LoaiSach loaiSach;
    LoaiSachAdapter loaiSachAdapter;
    Dialog dialog;

    EditText edtTenL, edtMaL;
    Button btnLuuLS, btnHuyLS;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_loai_sach, container, false);
        lvLoaiSach = view.findViewById(R.id.lvLoaiSach);
        fltAdd = view.findViewById(R.id.fltAdd);
        loaiSachDao = new LoaiSachDao(getContext());
        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendilogThem(getActivity(), 0);
            }
        });
        lvLoaiSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                loaiSach = list.get(position);
                opendilogThem(getActivity(), 1);
                return false;
            }
        });
        capNhatLV();
        return view;
    }

    public void capNhatLV(){
        list = (ArrayList<LoaiSach>) loaiSachDao.getAll();
        loaiSachAdapter = new LoaiSachAdapter(getActivity(), this, list);
        lvLoaiSach.setAdapter(loaiSachAdapter);
    }

    public void xoa(final String Id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa");
        builder.setMessage("Bạn chắc chắn muốn xóa?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        loaiSachDao.delete(Id);
                        capNhatLV();
                        dialog.cancel();
                    }
                }
        );
        builder.setNegativeButton(
                "Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }
        );
        AlertDialog alert = builder.create();
        builder.show();
    }
    public void opendilogThem(final Context context, final  int type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_loaisach);
        edtMaL = dialog.findViewById(R.id.edtMaL);
        edtTenL = dialog.findViewById(R.id.edtTenL);
        btnLuuLS = dialog.findViewById(R.id.btnLuuLS);
        btnHuyLS = dialog.findViewById(R.id.btnHuyLS);
        if (type != 0){
            edtMaL.setText(String.valueOf(loaiSach.getMaLoai()));
            edtTenL.setText(loaiSach.getTenLoai());
        }
        btnHuyLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnLuuLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiSach = new LoaiSach();
                loaiSach.setTenLoai(edtTenL.getText().toString());
                if (validate() > 0){
                    if (type == 0){
                        if (loaiSachDao.insert(loaiSach) > 0){
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        loaiSach.setMaLoai(Integer.parseInt(edtMaL.getText().toString()));
                        if (loaiSachDao.update(loaiSach) > 0){
                            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                capNhatLV();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public int validate(){
        int check = 1;
        if (edtTenL.getText().length() == 0){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}