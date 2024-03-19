package com.example.duan1_nhom5.dao;

import android.content.ContentValues;
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

    public ArrayList<DonHangChiTiet> getList(int donHangId) {
        ArrayList<DonHangChiTiet> chiTietDonHangList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Thực hiện câu truy vấn với DISTINCT để loại bỏ các bản ghi trùng lặp
        String query = "SELECT DISTINCT ChiTietDonHang.*, SanPham.ten AS tenSanPham, SanPham.gia_tien, " +
                "(SELECT gia_tri_thuoc_tinh FROM ThuocTinhSanPham WHERE id_san_pham = SanPham.id AND id_thuoc_tinh = (SELECT id FROM ThuocTinh WHERE ten = 'Màu sắc')) AS mauSac, " +
                "(SELECT gia_tri_thuoc_tinh FROM ThuocTinhSanPham WHERE id_san_pham = SanPham.id AND id_thuoc_tinh = (SELECT id FROM ThuocTinh WHERE ten = 'Kích thước')) AS kichThuoc " +
                "FROM ChiTietDonHang " +
                "INNER JOIN SanPham ON ChiTietDonHang.id_san_pham = SanPham.id " +
                "INNER JOIN ThuocTinhSanPham ON ChiTietDonHang.id_san_pham = ThuocTinhSanPham.id_san_pham " +
                "WHERE ChiTietDonHang.id_don_hang = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(donHangId)});

        if (cursor.moveToFirst()) {
            do {
                DonHangChiTiet chiTietDonHang = new DonHangChiTiet();
                chiTietDonHang.setId(cursor.getInt(cursor.getColumnIndex("id")));
                chiTietDonHang.setIdDH(cursor.getInt(cursor.getColumnIndex("id_don_hang")));
                chiTietDonHang.setIdSP(cursor.getInt(cursor.getColumnIndex("id_san_pham")));
                chiTietDonHang.setSoLuong(cursor.getInt(cursor.getColumnIndex("so_luong")));
                chiTietDonHang.setGia(cursor.getInt(cursor.getColumnIndex("gia_tien"))*cursor.getInt(cursor.getColumnIndex("so_luong")));
                chiTietDonHang.setTenSP(cursor.getString(cursor.getColumnIndex("tenSanPham")));
                chiTietDonHang.setMau(cursor.getString(cursor.getColumnIndex("mauSac")));
                chiTietDonHang.setKichThuoc(cursor.getString(cursor.getColumnIndex("kichThuoc")));
                chiTietDonHangList.add(chiTietDonHang);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return chiTietDonHangList;
    }
    public boolean deleteByDonHangId(int donHangId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete("ChiTietDonHang", "id_don_hang=?", new String[]{String.valueOf(donHangId)});
        db.close();
        return result > 0;
    }

    public boolean add(int idDonHang, int idSanPham, int soLuong, int giaTien) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_don_hang", idDonHang);
        values.put("id_san_pham", idSanPham);
        values.put("so_luong", soLuong);
        values.put("gia_tien", giaTien);

        long result = db.insert("ChiTietDonHang", null, values);
        db.close();

        // Kiểm tra xem việc thêm chi tiết đơn hàng có thành công hay không
        return result != -1;
    }
    public int getTotalPriceByDonHangId(int donHangId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT SUM(gia_tien) AS total_price FROM ChiTietDonHang WHERE id_don_hang = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(donHangId)});

        int totalPrice = 0;
        if (cursor.moveToFirst()) {
            totalPrice = cursor.getInt(cursor.getColumnIndex("total_price"));
        }

        cursor.close();
        db.close();

        return totalPrice;
    }

}

