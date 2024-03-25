package com.example.duan1_nhom5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_nhom5.dao.KhachHangDao;
import com.example.duan1_nhom5.dao.NhanVienDao;

public class DangNhap extends AppCompatActivity {
private KhachHangDao khachHangDao;
private NhanVienDao nhanVienDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        EditText edtUser=findViewById(R.id.edtUser);
        EditText edtPass=findViewById(R.id.edtPass);
        Button btnLogin=findViewById(R.id.btnLogin);
        TextView txtForgot=findViewById(R.id.txtForgot);
        TextView txtSignUp=findViewById(R.id.txtSignUp);
        khachHangDao =new KhachHangDao(this);
        nhanVienDao =new NhanVienDao(this);
        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhap.this, MainActivity.class);
                startActivity(intent);
            }
        });
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhap.this, DangKy.class);
                startActivity(intent);

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=edtUser.getText().toString();
                String pass=edtPass.getText().toString();
                boolean check= khachHangDao.kiemTraTonTai(user,pass);
                boolean check1= nhanVienDao.kiemTraTonTai(user,pass);

                if(check==true||check1==true||user.equalsIgnoreCase("adm")&&pass.equalsIgnoreCase("adm")){
                    Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DangNhap.this, MainActivity.class);
                    intent.putExtra("username", user);
                    intent.putExtra("password", pass);
                    startActivity(intent);

                }else{
                    Toast.makeText(DangNhap.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }

                }
        });    }
}