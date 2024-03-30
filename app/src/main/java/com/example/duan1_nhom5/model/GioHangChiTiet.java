package com.example.duan1_nhom5.model;

public class GioHangChiTiet {
    public int id,id_Gio_Hang,id_San_Pham,giaTien;
    public String tenSP,Mau,Size,soLuong,Hang;

    public GioHangChiTiet() {
    }

    public GioHangChiTiet(int id, int id_Gio_Hang, int id_San_Pham, int giaTien, String tenSP, String mau, String size, String soLuong, String hang) {
        this.id = id;
        this.id_Gio_Hang = id_Gio_Hang;
        this.id_San_Pham = id_San_Pham;
        this.giaTien = giaTien;
        this.tenSP = tenSP;
        Mau = mau;
        Size = size;
        this.soLuong = soLuong;
        Hang = hang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_Gio_Hang() {
        return id_Gio_Hang;
    }

    public void setId_Gio_Hang(int id_Gio_Hang) {
        this.id_Gio_Hang = id_Gio_Hang;
    }

    public int getId_San_Pham() {
        return id_San_Pham;
    }

    public void setId_San_Pham(int id_San_Pham) {
        this.id_San_Pham = id_San_Pham;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getMau() {
        return Mau;
    }

    public void setMau(String mau) {
        Mau = mau;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getHang() {
        return Hang;
    }

    public void setHang(String hang) {
        Hang = hang;
    }
}
