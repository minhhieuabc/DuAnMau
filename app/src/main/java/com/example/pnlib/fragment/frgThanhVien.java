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
import android.widget.TextView;
import android.widget.Toast;

import com.example.pnlib.R;
import com.example.pnlib.adapter.SachAdapter;
import com.example.pnlib.adapter.ThanhVienAdapter;
import com.example.pnlib.dao.SachDao;
import com.example.pnlib.dao.ThanhVienDao;
import com.example.pnlib.model.Sach;
import com.example.pnlib.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frgThanhVien#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgThanhVien extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frgThanhVien() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgThanhVien.
     */
    // TODO: Rename and change types and number of parameters
    public static frgThanhVien newInstance(String param1, String param2) {
        frgThanhVien fragment = new frgThanhVien();
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
    ListView lvThanhVien;
    FloatingActionButton fltAdd;
    ArrayList<ThanhVien> listTV;
    static ThanhVienDao thanhVienDao;
    ThanhVienAdapter thanhVienAdapter;
    ThanhVien thanhVien;
    Dialog dialog;
    EditText dialog_TenTV, dialog_NamSinh, dialog_MaTV;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_thanh_vien, container, false);
        lvThanhVien = view.findViewById(R.id.lvThanhVien);
        fltAdd = view.findViewById(R.id.fltAdd);
        thanhVienDao = new ThanhVienDao(getContext());
        capNhatLV();
        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendilogThem(getActivity(), 0);
            }
        });
        lvThanhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                thanhVien = listTV.get(position);
                opendilogThem(getActivity(), 1);
                return false;
            }
        });
        return  view;
    }

    void capNhatLV(){
        listTV = (ArrayList<ThanhVien>) thanhVienDao.getAll();
        thanhVienAdapter = new ThanhVienAdapter(getActivity(), this, listTV);
        lvThanhVien.setAdapter(thanhVienAdapter);
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
                        thanhVienDao.delete(Id);
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
        dialog.setContentView(R.layout.dialog_thanhvien);
        dialog_MaTV = dialog.findViewById(R.id.dialog_MaTV);
        dialog_TenTV = dialog.findViewById(R.id.dialog_TenTV);
        dialog_NamSinh = dialog.findViewById(R.id.dialog_NamSinh);
        Button btnThem = dialog.findViewById(R.id.btnLuu);
        Button btnHuyT = dialog.findViewById(R.id.btnHuyT);
        if (type != 0){
            dialog_MaTV.setText(String.valueOf(thanhVien.getMaTV()));
            dialog_TenTV.setText(thanhVien.getHoTenTV());
            dialog_NamSinh.setText(thanhVien.getNamSinh());
        }
        btnHuyT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thanhVien = new ThanhVien();
                thanhVien.setHoTenTV(dialog_TenTV.getText().toString());
                thanhVien.setNamSinh(dialog_NamSinh.getText().toString());
                if (validate() > 0){
                    if (type == 0){
                        if (thanhVienDao.insert(thanhVien) > 0){
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        thanhVien.setMaTV(Integer.parseInt(dialog_MaTV.getText().toString()));
                        if (thanhVienDao.update(thanhVien) > 0){
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
        if (dialog_TenTV.getText().length() == 0 || dialog_NamSinh.getText().length() == 0){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

}