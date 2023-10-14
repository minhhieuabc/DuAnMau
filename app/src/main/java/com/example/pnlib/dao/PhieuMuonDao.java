package com.example.pnlib.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pnlib.database.DbHelper;
import com.example.pnlib.model.PhieuMuon;
import com.example.pnlib.model.ThanhVien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDao {
    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public PhieuMuonDao(Context context){
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(PhieuMuon obj){
        ContentValues values = new ContentValues();
        values.put("maTV", obj.getMaTV());
        values.put("maTT", obj.getMaTT());
        values.put("maSach",obj.getMaSach());
        values.put("ngayThue",  sdf.format(obj.getNgay()));
        values.put("tienThue", obj.getTienThue());
        values.put("traSach", obj.getTraSach());
        return db.insert("PhieuMuon", null, values);
    }
    public int update(PhieuMuon obj){
        ContentValues values = new ContentValues();
        values.put("maTV", obj.getMaTV());
        values.put("maTT", obj.getMaTT());
        values.put("maSach",obj.getMaSach());
        values.put("ngayThue",  sdf.format(obj.getNgay()));
        values.put("tienThue", obj.getTienThue());
        values.put("traSach", obj.getTraSach());
        return db.update("PhieuMuon", values, "maPM=?", new String[]{String.valueOf(obj.getMaPM())});
    }
    public int delete(String id){
        return db.delete("PhieuMuon", "maPM=?", new String[]{id});
    }

    @SuppressLint("Range")
    private List<PhieuMuon> getData(String sql, String...selectionArgs){
        List<PhieuMuon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            PhieuMuon obj = new PhieuMuon();
            obj.setMaPM(Integer.parseInt(c.getString(c.getColumnIndex("maPM"))));
            obj.setMaTV(Integer.parseInt(c.getString(c.getColumnIndex("maTV"))));
            obj.setMaTT(c.getString(c.getColumnIndex("maTT")));
            obj.setMaSach(Integer.parseInt(c.getString(c.getColumnIndex("maSach"))));
            try {
                obj.setNgay(sdf.parse(c.getString(c.getColumnIndex("ngayThue"))));
            } catch (ParseException e){
                e.printStackTrace();
            }
            obj.setTienThue(Integer.parseInt(c.getString(c.getColumnIndex("tienThue"))));
            obj.setTraSach(Integer.parseInt(c.getString(c.getColumnIndex("traSach"))));
            list.add(obj);
        }
        return list;
    }
    public List<PhieuMuon> getAll(){
        String sql = "SELECT * FROM PhieuMuon";
        return getData(sql);
    }
    public PhieuMuon getID(String id){
        String sql = "SELECT * FROM PhieuMuon WHERE maPM=?";
        List<PhieuMuon> list = getData(sql, id);
        return list.get(0);
    }
}
