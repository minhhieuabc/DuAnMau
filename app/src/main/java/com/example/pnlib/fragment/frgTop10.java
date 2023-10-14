package com.example.pnlib.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.pnlib.R;
import com.example.pnlib.adapter.Top10Adapter;
import com.example.pnlib.dao.PhieuMuonDao;
import com.example.pnlib.dao.ThongKeDao;
import com.example.pnlib.model.Top;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frgTop10#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frgTop10 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frgTop10() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frgTop10.
     */
    // TODO: Rename and change types and number of parameters
    public static frgTop10 newInstance(String param1, String param2) {
        frgTop10 fragment = new frgTop10();
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
    ListView lvTop10;
    ArrayList<Top> list;
    Top10Adapter top10Adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frg_top10, container, false);
        lvTop10 = view.findViewById(R.id.lvTop10);
        ThongKeDao thongKeDao = new ThongKeDao(getActivity());
        list = (ArrayList<Top>) thongKeDao.getTop();
        top10Adapter = new Top10Adapter(getActivity(), this, list);
        lvTop10.setAdapter(top10Adapter);
        return view;
    }
}