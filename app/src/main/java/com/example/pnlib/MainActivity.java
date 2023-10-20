package com.example.pnlib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.pnlib.dao.ThuThuDao;
import com.example.pnlib.fragment.frgDoanhThu;
import com.example.pnlib.fragment.frgDoiMatKhau;
import com.example.pnlib.fragment.frgLoaiSach;
import com.example.pnlib.fragment.frgPhieuMuon;
import com.example.pnlib.fragment.frgSach;
import com.example.pnlib.fragment.frgThanhVien;
import com.example.pnlib.fragment.frgThemNguoi;
import com.example.pnlib.fragment.frgTop10;
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
        if(user.equalsIgnoreCase("admin")){
            nav.getMenu().findItem(R.id.addPerson).setVisible(true);
        }

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String fragmentTitle = item.getTitle().toString();
                getSupportActionBar().setTitle(fragmentTitle);
                Fragment fragment = null;


                if (item.getItemId() == R.id.home) {
                    fragment = new frgPhieuMuon();
                } else if (item.getItemId() == R.id.kindBook) {
                    fragment = new frgLoaiSach();
                } else if (item.getItemId() == R.id.book) {
                    fragment = new frgSach();
                } else if (item.getItemId() == R.id.member) {
                    fragment = new frgThanhVien();
                } else if (item.getItemId() == R.id.top10) {
                    fragment = new frgTop10();
                } else if (item.getItemId() == R.id.revenue) {
                    fragment = new frgDoanhThu();
                } else if (item.getItemId() == R.id.addPerson) {
                    fragment = new frgThemNguoi();
                } else if (item.getItemId() == R.id.key) {
                    fragment = new frgDoiMatKhau();
                } else if (item.getItemId() == R.id.logOut) {
                    showLogoutDialog();
                    return true;
                }

                if (fragment != null) {
                    replaceFrg(fragment);
                    closeDrawer();
                    return true;
                }

                return false;
            }
        });
    }
    public void replaceFrg(Fragment frg){
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frmNav, frg).commit();
    }
    public void closeDrawer() {
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        drawerLayout.closeDrawers();
    }
    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");
        builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                performLogout();
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }
    private void performLogout() {
        // Thực hiện các thao tác đăng xuất tại đây
        // Ví dụ: xóa thông tin đăng nhập, vv.

        // Khởi tạo Intent để chuyển đến màn hình đăng nhập
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

        // Kết thúc Activity hiện tại
        finish();
    }
}