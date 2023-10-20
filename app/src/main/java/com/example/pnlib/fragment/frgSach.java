package com.example.pnlib.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pnlib.R;
import com.example.pnlib.adapter.LoaiSachSpinerAdapter;
import com.example.pnlib.adapter.SachAdapter;
import com.example.pnlib.dao.LoaiSachDao;
import com.example.pnlib.dao.SachDao;
import com.example.pnlib.model.LoaiSach;
import com.example.pnlib.model.Sach;
import com.example.pnlib.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frgSach#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgSach extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frgSach() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgSach.
     */
    // TODO: Rename and change types and number of parameters
    public static frgSach newInstance(String param1, String param2) {
        frgSach fragment = new frgSach();
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

    ListView lvSach;
    FloatingActionButton fltAdd;
    List<Sach> list;
    List<Sach> listPhu;
    SachDao sachDao;
    Sach sach;
    SachAdapter sachAdapter;
    Dialog dialog;
    Spinner spinner;
    EditText edMaSach, edTenSach, edNamXB , edGiaThue, edSearch;
    Button btnTang, btnGiam;
    LoaiSachSpinerAdapter sachSpinerAdapter;
    ArrayList<LoaiSach> listLS;
    LoaiSachDao loaiSachDao;
    LoaiSach loaiSach;
    int maLoaiSach, position;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_sach, container, false);
        lvSach = view.findViewById(R.id.lvSach);
        fltAdd = view.findViewById(R.id.fltAdd);
        btnTang = view.findViewById(R.id.btnTang);
        btnGiam = view.findViewById(R.id.btnGiam);
        sachDao = new SachDao(getContext());
        edSearch =  view.findViewById(R.id.edSearch);
        listPhu = sachDao.getAll();
        capNhatLV();
        btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sapXepTang(list);
                lvSach.setAdapter(sachAdapter);
                sachAdapter.notifyDataSetChanged();
            }
        });
        btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sapXepGiam(list);
                lvSach.setAdapter(sachAdapter);
                sachAdapter.notifyDataSetChanged();
            }
        });

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list.clear();
                String searchText = s.toString().toLowerCase();
                for (Sach sach: listPhu) {
                    String tenSach = sach.getTenSach().toLowerCase();
                    if (tenSach.contains(searchText)) {
                        list.add(sach);
                    }
                }
                lvSach.setAdapter(sachAdapter);
                sachAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);
            }
        });
        lvSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                sach = list.get(position);
                openDialog(getActivity(), 1);
                return false;
            }
        });
        return view;
    }
    void capNhatLV(){
        list = (List<Sach>) sachDao.getAll();
        sachAdapter = new SachAdapter(getActivity(), this, list);
        lvSach.setAdapter(sachAdapter);
    }
    public void sapXepTang(List<Sach> ds){
        ds.sort(Comparator.comparing(Sach::getGiaThue));
    }
    public void sapXepGiam(List<Sach> ds){
        ds.sort(Comparator.comparing(Sach::getGiaThue).reversed());
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
                        sachDao.delete(Id);
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

    protected void openDialog(final Context context, final  int type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_sach);
        edMaSach = dialog.findViewById(R.id.edMaSach);
        edTenSach = dialog.findViewById(R.id.edTenSach);
        edNamXB = dialog.findViewById(R.id.edNamXB);
        edGiaThue = dialog.findViewById(R.id.edGiaThue);
        spinner = dialog.findViewById(R.id.spLoaiSach);
        Button btnLuuS = dialog.findViewById(R.id.btnLuuSach);
        Button btnHuyS = dialog.findViewById(R.id.btnHuyTSach);

        listLS = new ArrayList<LoaiSach>();
        loaiSachDao = new LoaiSachDao(context);
        listLS = (ArrayList<LoaiSach>) loaiSachDao.getAll();

        sachSpinerAdapter = new LoaiSachSpinerAdapter(context, listLS);
        spinner.setAdapter(sachSpinerAdapter);
        // lấy mã loại sách
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiSach = listLS.get(position).getMaLoai();
                Toast.makeText(context, "Chọn" + listLS.get(position).getTenLoai() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (type != 0){
            edMaSach.setText(String.valueOf(sach.getMaSach()));
            edTenSach.setText(sach.getTenSach());
            edNamXB.setText(String.valueOf(sach.getNamXB()));
            edGiaThue.setText(String.valueOf(sach.getGiaThue()));
            for (int i = 0; i < listLS.size(); i++)
                if (sach.getMaLoai() == (listLS.get(i).getMaLoai())){
                    position = i ;
                }
            Log.i("demo", "posSach" + position);
            spinner.setSelection(position);
        }
        btnHuyS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnLuuS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sach = new Sach();
                sach.setTenSach(edTenSach.getText().toString());
                sach.setNamXB(Integer.parseInt(edNamXB.getText().toString()));
                sach.setGiaThue(Integer.parseInt(edGiaThue.getText().toString()));
                sach.setMaLoai(maLoaiSach);
                if (validate() > 0){
                    if (type == 0){
                        if (sachDao.insert(sach) > 0){
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        sach.setMaSach(Integer.parseInt(edMaSach.getText().toString()));
                        if (sachDao.update(sach) > 0){
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
        if (edTenSach.getText().length() == 0 || edGiaThue.getText().length() == 0 || edNamXB.getText().length() == 0){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

}