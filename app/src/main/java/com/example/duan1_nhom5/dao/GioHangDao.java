
package com.example.duan1_nhom5.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.duan1_nhom5.DBHelper.db;
import com.example.duan1_nhom5.model.GioHang;

import java.util.ArrayList;

public class GioHangDao {
    private SQLiteDatabase db;
    private SQLiteOpenHelper sqLiteOpenHelper;
    public GioHangDao(Context context){
        sqLiteOpenHelper= new db(context);
    }
    public ArrayList<GioHang> getList(){
        ArrayList<GioHang> gioHangArrayList=new ArrayList<>();
        SQLiteDatabase db= sqLiteOpenHelper.getReadableDatabase();
        String query="SELECT  GioHang.id,GioHang.id_khach_hang from GioHang " +
                "LEFT JOIN KhachHang ON GioHang.id_khach_hang=KhachHang.id " ;
        Cursor cursor= db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                GioHang gioHang =new GioHang();
                gioHang.setId(cursor.getInt(cursor.getColumnIndex("id")));
                gioHang.setId_Khach_Hang(cursor.getInt(cursor.getColumnIndex("id_khach_hang")));

            }while (cursor.moveToNext());
        }
        cursor.close();

        return gioHangArrayList;
    }
    public boolean delete(int i){
        SQLiteDatabase db=sqLiteOpenHelper.getWritableDatabase();
        int result= db.delete("GioHang","id=?",new String[]{String.valueOf(i)});
        db.close();
        return result>0;
    }
    public boolean add(int id,int id_khach_hang ){
        SQLiteDatabase db=sqLiteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("id_khach_hang",id_khach_hang);
        long result = db.insert("GioHang", null, values);
        db.close();

        return result != -1;

    }
    public int layIdGioHangTuUsernameVaMatKhau(String username, String password) {
        int idGioHang = -1;

        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        String query = "SELECT gh.id " +
                "FROM GioHang gh " +
                "JOIN KhachHang kh ON gh.id_khach_hang = kh.id " +
                "WHERE kh.username = ? AND kh.pass = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        if (cursor.moveToFirst()) {
            idGioHang = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return idGioHang;
    }
}
