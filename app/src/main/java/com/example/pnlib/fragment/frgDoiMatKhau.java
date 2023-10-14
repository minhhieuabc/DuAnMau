package com.example.pnlib.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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


public class frgDoiMatKhau extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frgDoiMatKhau() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgDoiMatKhau.
     */
    // TODO: Rename and change types and number of parameters
    public static frgDoiMatKhau newInstance(String param1, String param2) {
        frgDoiMatKhau fragment = new frgDoiMatKhau();
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

    EditText edMatKhauCu, edMatKhauMoi, edNhapLaiMK;
    Button btnDoi, btnHuy;
    ThuThuDao thuThuDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_doi_mat_khau, container, false);
        edMatKhauCu = view.findViewById(R.id.edMatKhauCu);
        edMatKhauMoi = view.findViewById(R.id.edMatKhauMoi);
        edNhapLaiMK = view.findViewById(R.id.edNhapLaiMK);
        thuThuDao = new ThuThuDao(getActivity());
        btnDoi = view.findViewById(R.id.btnDoi);
        btnDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = pref.getString("USERNAME", "");
                if (validate() > 0){
                    ThuThu thuThu = thuThuDao.getID(user);
                    thuThu.setMatKhau(edMatKhauMoi.getText().toString());
                    if (thuThuDao.updatePass(thuThu) > 0){
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edMatKhauCu.setText("");
                        edMatKhauMoi.setText("");
                        edNhapLaiMK.setText("");
                    } else {
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnHuy = view.findViewById(R.id.btnHuy);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edMatKhauCu.setText("");
                edMatKhauMoi.setText("");
                edNhapLaiMK.setText("");
            }
        });
        return view;
    }
    public int validate(){
        int check = 1;
        if (edMatKhauCu.getText().length() == 0 || edMatKhauMoi.getText().length() == 0 || edNhapLaiMK.getText().length() == 0){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passOld = pref.getString("PASSWORD", "");
            String pass = edMatKhauMoi.getText().toString();
            String rePass = edNhapLaiMK.getText().toString();
            if (!passOld.equals(edMatKhauCu.getText().toString())){
                Toast.makeText(getContext(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!pass.equals(rePass)){
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}