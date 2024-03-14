package com.example.duan1_nhom5.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class db extends SQLiteOpenHelper {
    public static final String DB_NAME = "CHQA";
    public static final int DB_VERSION = 1;

    public db(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Thuộc tính
        String createTableThuocTinh = "CREATE TABLE ThuocTinh (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ten TEXT NOT NULL)";
        db.execSQL(createTableThuocTinh);

// Tạo bảng Thuộc tính sản phẩm
        String createTableThuocTinhSanPham = "CREATE TABLE ThuocTinhSanPham (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_thuoc_tinh INTEGER REFERENCES ThuocTinh(id), " +
                "id_san_pham INTEGER REFERENCES SanPham(id), " +
                "gia_tri_thuoc_tinh TEXT, " +
                "FOREIGN KEY (id_thuoc_tinh) REFERENCES ThuocTinh(id), " +
                "FOREIGN KEY (id_san_pham) REFERENCES SanPham(id))";
        db.execSQL(createTableThuocTinhSanPham);

// Tạo bảng Sản phẩm
        String createTableSanPham = "CREATE TABLE SanPham (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ten TEXT NOT NULL, " +
                "so_luong INTEGER,"+
                "gia_tien INTEGER) ";
        db.execSQL(createTableSanPham);

// Tạo bảng Giỏ hàng
        String createTableGioHang = "CREATE TABLE GioHang (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_khach_hang INTEGER REFERENCES KhachHang(id), " +
                "id_san_pham INTEGER REFERENCES SanPham(id), " +
                "so_luong INTEGER, " +
                "FOREIGN KEY (id_khach_hang) REFERENCES KhachHang(id), " +
                "FOREIGN KEY (id_san_pham) REFERENCES SanPham(id))";
        db.execSQL(createTableGioHang);

// Tạo bảng Khách hàng
        String createTableKhachHang = "CREATE TABLE KhachHang (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ten TEXT NOT NULL, " +
                "email TEXT, " +
                "sdt TEXT, " +
                "dia_chi TEXT, " +
                "username TEXT NOT NULL, " +
                "pass TEXT NOT NULL)";
        db.execSQL(createTableKhachHang);

// Tạo bảng Nhân viên
        String createTableNhanVien = "CREATE TABLE NhanVien (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ten TEXT NOT NULL, " +
                "email TEXT, " +
                "sdt TEXT, " +
                "dia_chi TEXT, " +
                "username TEXT NOT NULL, " +
                "pass TEXT NOT NULL, " +
                "chuc_vu TEXT NOT NULL)";
        db.execSQL(createTableNhanVien);

// Tạo bảng Đơn hàng
        String createTableDonHang = "CREATE TABLE DonHang (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_khach_hang INTEGER REFERENCES KhachHang(id), " +
                "id_nhan_vien INTEGER REFERENCES NhanVien(id), " +
                "ngay_mua DATE NOT NULL, " +
                "trang_thai INTEGER NOT NULL, " +
                "tong_tien INTEGER NOT NULL, " +
                "FOREIGN KEY (id_khach_hang) REFERENCES KhachHang(id), " +
                "FOREIGN KEY (id_nhan_vien) REFERENCES NhanVien(id))";
        db.execSQL(createTableDonHang);

// Tạo bảng Chi tiết đơn hàng
        String createTableChiTietDonHang = "CREATE TABLE ChiTietDonHang (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_san_pham INTEGER REFERENCES SanPham(id), " +
                "id_don_hang INTEGER REFERENCES DonHang(id), " +
                "so_luong INTEGER NOT NULL, " +
                "gia_tien INTEGER NOT NULL, " +
                "FOREIGN KEY (id_san_pham) REFERENCES SanPham(id), " +
                "FOREIGN KEY (id_don_hang) REFERENCES DonHang(id))";
        db.execSQL(createTableChiTietDonHang);

        // data mẫu
        // Chèn dữ liệu vào bảng Thuộc tính
        db.execSQL("INSERT INTO ThuocTinh  VALUES ('Màu sắc')," +
                " ('Hãng')," +
                "('Kích thước')");

// Chèn dữ liệu vào bảng Sản phẩm
        db.execSQL("INSERT INTO SanPham  VALUES ('Áo sơ mi', 20, 250000)," +
                "('Quần jean', 10, 250000)");

// Chèn dữ liệu vào bảng Thuộc tính sản phẩm
        db.execSQL("INSERT INTO ThuocTinhSanPham  VALUES (1, 1, 'Trắng')," +
                " (3, 1, 'ADIDAS')," +
                " (2, 1, 'M'), " +
                "(1, 2, 'Be')," +
                " (3, 2, 'ADIDAS')," +
                " (2, 2, 'L')");


// Chèn dữ liệu vào bảng Khách hàng
        db.execSQL("INSERT INTO KhachHang VALUES ('Nguyễn Văn A', 'nguyenvana@example.com', '0123456789', 'Hà Nội', 'nguyenvana', '123456')");

// Chèn dữ liệu vào bảng Nhân viên
        db.execSQL("INSERT INTO NhanVien  VALUES ('Nguyễn Thị B', 'vinhtd01112@gmail.com', '0987654321', 'Hồ Chí Minh', 'nguyenthidb', '123456', 'nhavien')," +
                "('Trần Đức Vinh', 'vinh04012004@gmail.com', '0987654321', 'Hồ Chí Minh', 'admin', 'admin', 'admin')");

// Chèn dữ liệu vào bảng Đơn hàng
        db.execSQL("INSERT INTO DonHang  VALUES (1, 1, '2024-03-14', 1 , 750000)");

// Chèn dữ liệu vào bảng Giỏ hàng
        db.execSQL("INSERT INTO GioHang  VALUES (1, 1, 2)," +
                "(1, 2, 1)");

// Chèn dữ liệu vào bảng Chi tiết đơn hàng
        db.execSQL("INSERT INTO ChiTietDonHang  VALUES (1, 1, 2, 250000)," +
                " (2, 1, 1, 250000)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i != i1) {
            db.execSQL("drop table if exists ThuocTinh");
            db.execSQL("drop table if exists SanPham");
            db.execSQL("drop table if exists ThuocTinhSanPham");
            db.execSQL("drop table if exists KhachHang");
            db.execSQL("drop table if exists NhanVien");
            db.execSQL("drop table if exists DonHang");
            db.execSQL("drop table if exists GioHang");
            db.execSQL("drop table if exists ChiTietDonHang");
            onCreate(db);
        }
    }

}
