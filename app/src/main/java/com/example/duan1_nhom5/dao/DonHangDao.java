package com.example.duan1_nhom5.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.duan1_nhom5.DBHelper.db;
import com.example.duan1_nhom5.model.DonHang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DonHangDao {
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;
    public DonHangDao(Context context) {
        dbHelper = new db(context);
    }
    public ArrayList<DonHang> getList() {
        ArrayList<DonHang> donHangList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn SQL kết hợp với JOIN để lấy thông tin đầy đủ của đơn hàng
        String query = "SELECT DonHang.id, DonHang.id_khach_hang, DonHang.id_nhan_vien, DonHang.ngay_mua, DonHang.trang_thai, DonHang.tong_tien, KhachHang.ten AS ten_khach_hang, NhanVien.ten AS ten_nhan_vien " +
                "FROM DonHang " +
                "LEFT JOIN KhachHang ON DonHang.id_khach_hang = KhachHang.id " +
                "LEFT JOIN NhanVien ON DonHang.id_nhan_vien = NhanVien.id";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                DonHang donHang = new DonHang();
                donHang.setId(cursor.getInt(cursor.getColumnIndex("id")));
                donHang.setIdKH(cursor.getInt(cursor.getColumnIndex("id_khach_hang")));
                donHang.setIdNV(cursor.getInt(cursor.getColumnIndex("id_nhan_vien")));
                String ngayMuaString = cursor.getString(cursor.getColumnIndex("ngay_mua"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                try {
                    Date ngayMuaDate = dateFormat.parse(ngayMuaString);
                    donHang.setNgayMua(ngayMuaDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                donHang.setTrangThai(cursor.getInt(cursor.getColumnIndex("trang_thai")));
                donHang.setTongTien(cursor.getInt(cursor.getColumnIndex("tong_tien")));
                donHang.setTenKH(cursor.getString(cursor.getColumnIndex("ten_khach_hang")));
                donHang.setTenNV(cursor.getString(cursor.getColumnIndex("ten_nhan_vien")));
                donHangList.add(donHang);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return donHangList;
    }
}
