package com.example.pnlib.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pnlib.R;
import com.example.pnlib.dao.ThuThuDao;
import com.example.pnlib.model.ThuThu;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frgThemNguoi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgThemNguoi extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frgThemNguoi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgThemNguoi.
     */
    // TODO: Rename and change types and number of parameters
    public static frgThemNguoi newInstance(String param1, String param2) {
        frgThemNguoi fragment = new frgThemNguoi();
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
    ThuThuDao thuThuDao;
    EditText edTenDangNhap, edHoVaTen, edMatKhauND, edNhapLaiMKND;
    Button btnThemND, btnHuyND;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_them_nguoi, container, false);
        thuThuDao = new ThuThuDao(getContext());
        edTenDangNhap = view.findViewById(R.id.edTenDangNhap);
        edHoVaTen = view.findViewById(R.id.edHoVaTen);
        edMatKhauND = view.findViewById(R.id.edMatKhauND);
        edNhapLaiMKND = view.findViewById(R.id.edNhapLaiMKND);
        btnHuyND = view.findViewById(R.id.btnHuyND);
        btnThemND = view.findViewById(R.id.btnThemND);

        btnHuyND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edTenDangNhap.setText("");
                edHoVaTen.setText("");
                edMatKhauND.setText("");
                edNhapLaiMKND.setText("");
            }
        });

        btnThemND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDN = edTenDangNhap.getText().toString();
                String hoTen = edHoVaTen.getText().toString();
                String matKhau = edMatKhauND.getText().toString();
                String nhapLai = edNhapLaiMKND.getText().toString();

                if (validate(tenDN,hoTen,matKhau,nhapLai) > 0) {
                    ThuThuDao thuThuDao = new ThuThuDao(getContext());
                    if (thuThuDao.insert(new ThuThu(tenDN,hoTen,matKhau)) > 0) {
                        Toast.makeText(getContext(), "Thêm thành công !", Toast.LENGTH_SHORT).show();
                        edTenDangNhap.setText("");
                        edHoVaTen.setText("");
                        edMatKhauND.setText("");
                        edNhapLaiMKND.setText("");
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    public int validate(String tenDN,String hoTen,String matKhau,String nhapLai) {
        int check = 1;
        if (tenDN.length() != 0 && hoTen.length() != 0 && matKhau.length() != 0 && nhapLai.length() != 0) {
            if (!matKhau.equals(nhapLai)) {
                Toast.makeText(getContext(), "Nhập lại mật khẩu chưa chính xác !", Toast.LENGTH_SHORT).show();
                edNhapLaiMKND.setError("Nhập lại mật khẩu chưa chính xác !");
                edNhapLaiMKND.requestFocus();
                check = -1;
            } else {
                check = 1;
            }
        } else {
            if (tenDN.length() == 0) {
                edTenDangNhap.setError("Không được để trống trường này !");
                edTenDangNhap.requestFocus();
            }
            if (hoTen.length() == 0) {
                edHoVaTen.setError("Không được để trống trường này !");
                edHoVaTen.requestFocus();
            }
            if (matKhau.length() == 0) {
                edMatKhauND.setError("Không được để trống trường này !");
                edMatKhauND.requestFocus();
            }
            if (nhapLai.length() == 0) {
                edNhapLaiMKND.setError("Không được để trống trường này !");
                edNhapLaiMKND.requestFocus();
            }
            check = -1;
        }
        return check;
    }
}