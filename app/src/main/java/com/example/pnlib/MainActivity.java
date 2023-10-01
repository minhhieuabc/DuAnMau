package com.example.pnlib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.pnlib.dao.ThuThuDao;
import com.example.pnlib.fragment.frgLoaiSach;
import com.example.pnlib.fragment.frgPhieuMuon;
import com.example.pnlib.model.ThuThu;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView nav;
    View mHeaderView;
    TextView txtUser;
    ThuThuDao thuThuDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolBar);
        nav = findViewById(R.id.nav);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mHeaderView = nav.getHeaderView(0);
        txtUser = mHeaderView.findViewById(R.id.txtUser);
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        thuThuDao = new ThuThuDao(this);
        ThuThu thuThu = thuThuDao.getID(user);
        String username = thuThu.getHoTen();
        txtUser.setText("Welcome, " + username);
        frgPhieuMuon frgPhieuMuon = new frgPhieuMuon();
        replaceFrg(frgPhieuMuon);
        getSupportActionBar().setTitle("Quản lý phiếu mượn");

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String fragmentTitle = item.getTitle().toString();
                getSupportActionBar().setTitle(fragmentTitle);
                if (item.getItemId() ==  R.id.home){
                    frgPhieuMuon frgPhieuMuon = new frgPhieuMuon();
                    replaceFrg(frgPhieuMuon);
                } else if (item.getItemId() == R.id.kindBook){
                    frgLoaiSach frgLoaiSach = new frgLoaiSach();
                    replaceFrg(frgLoaiSach);
                }
                return true;
            }
        });
    }
    public void replaceFrg(Fragment frg){
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frmNav, frg).commit();
    }
}