package com.example.pnlib.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DbHelper extends SQLiteOpenHelper {
    public static final String Db_name = "QLTV";

    public DbHelper(Context context) {
        super(context, Db_name, null, 11);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng thủ thư
        String Table_ThuThu = "CREATE TABLE ThuThu(" +
                "maTT TEXT PRIMARY KEY, " +
                "hoTen TEXT NOT NULL, " +
                "matKhau TEXT NOT NULL)";
        db.execSQL(Table_ThuThu);
        String Insert_ThuThu = "INSERT INTO ThuThu VALUES('admin', 'NguyenTruong', '123456')," +
                                                        "('abc', 'Admin', '123456')";
        db.execSQL(Insert_ThuThu);

        // Tạo bảng thành viên
        String Table_ThanhVien = "CREATE TABLE ThanhVien(" +
                "maTV INTEGER PRIMARY KEY AUTOINCREMENT," +
                "hoTen TEXT NOT NULL, " +
                "namSinh TEXT NOT NULL)";
        db.execSQL(Table_ThanhVien);
//        String Insert_ThanhVien = "INSERT INTO ThanhVien(hoTen, namSinh) VALUES " +
//                "('Nguyễn Anh Văn', '2004')," +
//                "('Hoàng Quang Vinh', '2002')," +
//                "('Nguyễn Tiến Mạnh', '2005')";
//        db.execSQL(Insert_ThanhVien);

        // Tạo bảng loại sách
        String Table_LoaiSach = "CREATE TABLE LoaiSach(" +
                "maLS INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenLoai TEXT NOT NULL)";
        db.execSQL(Table_LoaiSach);
//        String Insert_LoaiSach = "INSERT INTO LoaiSach(tenLoai) VALUES('Ngữ pháp tiếng anh')," +
//                "('Lập trình')," +
//                "('Làm đẹp')";
//        db.execSQL(Insert_LoaiSach);

        // Tao bang sach
        String Table_Sach = "CREATE TABLE Sach(" +
                "maSach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenSach TEXT NOT NULL, " +
                "giaThue INTEGER NOT NULL, " +
                "maLoai INTEGER REFERENCES LoaiSach(maLoai))";
        db.execSQL(Table_Sach);
//        String Insert_Sach = "INSERT INTO Sach(tenSach, giaThue, maLoai) VALUES('Lập trình web', 2000, 1)," +
//                "('Luyện siêu trí nhớ từ vựng', 4000, 3)," +
//                "('Sắc đẹp tuổi teen', 1000, 2)";
//        db.execSQL(Insert_Sach);

        // Tao bang phieu muon
        String Table_PhieuMuon = "CREATE TABLE PhieuMuon(" +
                "maPM INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maTV TEXT REFERENCES ThanhVien(maTV), " +
                "maTT INTEGER REFERENCES ThuThu(maTT), " +
                "maSach INTEGER REFERENCES Sach(maSach)," +
                "ngayThue DATE NOT NULL," +
                "tienThue INTEGER NOT NULL," +
                "traSach INTEGER NOT NULL)";
        db.execSQL(Table_PhieuMuon);
//        String Insert_PhieuMuon = "INSERT INTO PhieuMuon(maTV, maTT, maSach, ngayThue, tienThue, traSach) VALUES " +
//                "('1', 'admin', '1', '2023-06-24', 2000, 0)," +
//                "('2', 'admin', '3', '2023-02-12', 1000, 1)," +
//                "('3', 'abc', '2', '2021-07-20', 4000, 0)";
//        db.execSQL(Insert_PhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS ThuThu");
            db.execSQL("DROP TABLE IF EXISTS ThanhVien");
            db.execSQL("DROP TABLE IF EXISTS LoaiSach");
            db.execSQL("DROP TABLE IF EXISTS Sach");
            db.execSQL("DROP TABLE IF EXISTS PhieuMuon");
            onCreate(db);
        }
    }
}
