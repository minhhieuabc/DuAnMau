package com.example.pnlib.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pnlib.R;
import com.example.pnlib.adapter.PhieuMuonAdapter;
import com.example.pnlib.adapter.SachSpinerAdapter;
import com.example.pnlib.adapter.ThanhVienAdapter;
import com.example.pnlib.adapter.ThanhVienSpinerAdapter;
import com.example.pnlib.dao.PhieuMuonDao;
import com.example.pnlib.dao.SachDao;
import com.example.pnlib.dao.ThanhVienDao;
import com.example.pnlib.model.PhieuMuon;
import com.example.pnlib.model.Sach;
import com.example.pnlib.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frgPhieuMuon#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgPhieuMuon extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frgPhieuMuon() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgPhieuMuon.
     */
    // TODO: Rename and change types and number of parameters
    public static frgPhieuMuon newInstance(String param1, String param2) {
        frgPhieuMuon fragment = new frgPhieuMuon();
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

    ListView lvPhieuMuon;
    FloatingActionButton fltAdd;
    Dialog dialog;
    EditText edMaPM;
    Spinner spTV, spSach;
    TextView tvNgayT, tvTienThue;
    CheckBox chkTraSach;
    ArrayList<PhieuMuon> list;
    static PhieuMuonDao phieuMuonDao;
    PhieuMuonAdapter phieuMuonAdapter;
    PhieuMuon phieuMuon;
    ThanhVienSpinerAdapter thanhVienSpinerAdapter;
    ArrayList<ThanhVien> listThanhVien;
    ThanhVienDao thanhVienDao;
    ThanhVien thanhVien;
    int maThanhVien;

    SachSpinerAdapter sachSpinerAdapter;
    ArrayList<Sach> listSach;
    SachDao sachDao;
    Sach sach;
    int maSach, tienThue;
    int positionTV, positionSach;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_phieu_muon, container, false);
        lvPhieuMuon = view.findViewById(R.id.lvPhieuMuon);
        fltAdd = view.findViewById(R.id.fltAdd);
        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getContext(), 0);
            }
        });

        lvPhieuMuon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                phieuMuon = list.get(position);
                openDialog(getActivity(), 1);
                return false;
            }
        });
        phieuMuonDao = new PhieuMuonDao(getActivity());
        capNhatLV();
        return view;
    }

    void capNhatLV(){
        list = (ArrayList<PhieuMuon>) phieuMuonDao.getAll();
        phieuMuonAdapter = new PhieuMuonAdapter(getActivity(), this, list);
        lvPhieuMuon.setAdapter(phieuMuonAdapter);
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
                        phieuMuonDao.delete(Id);
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

    protected void openDialog(final Context context, final int type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_phieumuon);
        edMaPM = dialog.findViewById(R.id.edMaPM);
        spTV = dialog.findViewById(R.id.spMaTV);
        spSach = dialog.findViewById(R.id.spMaSach);
        tvNgayT = dialog.findViewById(R.id.tvNgay);
        tvTienThue = dialog.findViewById(R.id.tvGiaThue);
        chkTraSach = dialog.findViewById(R.id.chkTraSach);
        Button btnCancel = dialog.findViewById(R.id.btnHuyPM);
        Button btnSvae = dialog.findViewById(R.id.btnLuuPM);
        tvNgayT.setText("Ngày thuê: " + sdf.format(new Date()));

        thanhVienDao = new ThanhVienDao(context);
        listThanhVien = new ArrayList<ThanhVien>();
        listThanhVien = (ArrayList<ThanhVien>) thanhVienDao.getAll();
        thanhVienSpinerAdapter = new ThanhVienSpinerAdapter(context, listThanhVien);
        spTV.setAdapter(thanhVienSpinerAdapter);
        spTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maThanhVien = listThanhVien.get(position).getMaTV();
                Toast.makeText(context, "Chọn" + listThanhVien.get(position).getHoTenTV(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sachDao = new SachDao(context);
        listSach = new ArrayList<Sach>();
        listSach = (ArrayList<Sach>) sachDao.getAll();
        sachSpinerAdapter = new SachSpinerAdapter(context, listSach);
        spSach.setAdapter(sachSpinerAdapter);

        spSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = listSach.get(position).getMaSach();
                tienThue = listSach.get(position).getGiaThue();
                tvTienThue.setText("Tiền thuê: " + tienThue);
                Toast.makeText(context, "Chọn" + listSach.get(position).getTenSach(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (type != 0){
            edMaPM.setText(String.valueOf(phieuMuon.getMaPM()));
            for (int i = 0; i < listThanhVien.size(); i++)
                if (phieuMuon.getMaTV() == listThanhVien.get(i).getMaTV()){
                    positionTV = i;
                }
            spTV.setSelection(positionTV);
            for (int i = 0; i < listSach.size(); i++)
                if (phieuMuon.getMaSach() == listSach.get(i).getMaSach()){
                    positionSach = i;
                }

            tvNgayT.setText("Ngày thuê: " + sdf.format(phieuMuon.getNgay()));
            tvTienThue.setText("Tiền thuê: " + phieuMuon.getTienThue());
            if (phieuMuon.getTraSach() == 1){
                chkTraSach.setChecked(true);
            } else {
                chkTraSach.setChecked(false);
            }
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSvae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phieuMuon = new PhieuMuon();
                phieuMuon.setMaSach(maSach);
                phieuMuon.setMaTV(maThanhVien);
                phieuMuon.setNgay(new Date());
                phieuMuon.setTienThue(tienThue);
                if (chkTraSach.isChecked()){
                    phieuMuon.setTraSach(1);
                } else {
                    phieuMuon.setTraSach(0);
                }

                if (type == 0){
                    if (phieuMuonDao.insert(phieuMuon) > 0){
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    phieuMuon.setMaPM(Integer.parseInt(edMaPM.getText().toString()));
                    if (phieuMuonDao.update(phieuMuon) > 0){
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Cập nhật thất bại thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                capNhatLV();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}