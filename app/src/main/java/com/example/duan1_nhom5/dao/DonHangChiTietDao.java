package com.example.duan1_nhom5.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.duan1_nhom5.DBHelper.db;
import com.example.duan1_nhom5.model.DonHangChiTiet;

import java.util.ArrayList;

public class DonHangChiTietDao {
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;

    public DonHangChiTietDao(Context context) {
        dbHelper = new db(context);
    }
    public ArrayList<DonHangChiTiet> getList() {
        ArrayList<DonHangChiTiet> chiTietDonHangList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Thực hiện câu truy vấn join
        String query = "SELECT ChiTietDonHang.*, SanPham.ten AS tenSanPham, SanPham.gia_tien, " +
                "(SELECT gia_tri_thuoc_tinh FROM ThuocTinhSanPham WHERE id_san_pham = SanPham.id AND id_thuoc_tinh = (SELECT id FROM ThuocTinh WHERE ten = 'Màu sắc')) AS mauSac, " +
                "(SELECT gia_tri_thuoc_tinh FROM ThuocTinhSanPham WHERE id_san_pham = SanPham.id AND id_thuoc_tinh = (SELECT id FROM ThuocTinh WHERE ten = 'Kích thước')) AS kichThuoc " +
                "FROM ChiTietDonHang " +
                "INNER JOIN SanPham ON ChiTietDonHang.id_san_pham = SanPham.id " +
                "INNER JOIN ThuocTinhSanPham ON ChiTietDonHang.id_san_pham = ThuocTinhSanPham.id_san_pham";
        Cursor cursor = db.rawQuery(query, null);


        if (cursor.moveToFirst()) {
            do {
                DonHangChiTiet chiTietDonHang = new DonHangChiTiet();
                chiTietDonHang.setId(cursor.getInt(cursor.getColumnIndex("id")));
                chiTietDonHang.setIdDH(cursor.getInt(cursor.getColumnIndex("id_don_hang")));
                chiTietDonHang.setIdSP(cursor.getInt(cursor.getColumnIndex("id_san_pham")));
                chiTietDonHang.setSoLuong(cursor.getInt(cursor.getColumnIndex("so_luong")));
                chiTietDonHang.setGia(cursor.getInt(cursor.getColumnIndex("gia_tien")));
                chiTietDonHang.setMau(cursor.getString(cursor.getColumnIndex("mauSac")));
                chiTietDonHang.setKichThuoc(cursor.getString(cursor.getColumnIndex("kichThuoc")));
                chiTietDonHangList.add(chiTietDonHang);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return chiTietDonHangList;
    }

}
