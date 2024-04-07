package com.example.duan1_nhom5.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_nhom5.R;
import com.example.duan1_nhom5.adapter.ChiTeitAddSPDonHang;
import com.example.duan1_nhom5.adapter.ChiTietDonHangAdapter;
import com.example.duan1_nhom5.adapter.GioHangAdapder;
import com.example.duan1_nhom5.dao.DonHangDao;
import com.example.duan1_nhom5.dao.GioHangChiTietDao;
import com.example.duan1_nhom5.dao.GioHangDao;
import com.example.duan1_nhom5.dao.KhachHangDao;
import com.example.duan1_nhom5.model.DonHangChiTiet;
import com.example.duan1_nhom5.model.GioHang;
import com.example.duan1_nhom5.model.GioHangChiTiet;
import com.example.duan1_nhom5.model.KhachHang;
import com.example.duan1_nhom5.model.NhanVien;
import com.example.duan1_nhom5.model.SanPham;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GioHangFragment extends Fragment {
    RecyclerView recyclerView;
    GioHangAdapder gioHangAdapter;
    GioHangDao gioHangDao;
    int idGioHang,idKH,tong;

    GioHangChiTietDao gioHangChiTietDao;
    DonHangDao donHangDao;
    Button btnCuahang,btnDathang;

    private ArrayList<GioHangChiTiet> list= new ArrayList<>();
    private ArrayList<SanPham> listSP= new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gio_hang, container, false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");

        gioHangDao =new GioHangDao(getContext());
        idGioHang= gioHangDao.layIdGioHangTuUsernameVaMatKhau(username,password);
        recyclerView=view.findViewById(R.id.rcvGioHang);
        gioHangChiTietDao= new GioHangChiTietDao(getContext());
        list = gioHangChiTietDao.getList(idGioHang);
        gioHangAdapter =new GioHangAdapder(getContext(),list);
        recyclerView.setAdapter(gioHangAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btnCuahang=view.findViewById(R.id.btnCuaHang);
        btnDathang=view.findViewById(R.id.btnDatHang);

        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listSP = gioHangDao.getList(idGioHang);
                idKH = gioHangDao.getUserIdByUsernameAndPassword(username, password);
                tong = gioHangDao.tinhTongTien(listSP);
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                String date=dateFormat.format(currentDate);
                  SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                String date1=dateFormat1.format(currentDate);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("Xác nhận đơn hàng");

                alertDialogBuilder.setMessage("Đơn hàng sẽ được gửi ?");
                alertDialogBuilder.setPositiveButton("Có", (dialog, which) -> {
                    for (GioHangChiTiet gioHangChiTiet :list){
                        int soLuong=gioHangChiTiet.getSoLuong();
                        if(soLuong>0){
                            listSP = gioHangDao.getList(idGioHang);
                            idKH = gioHangDao.getUserIdByUsernameAndPassword(username, password);
                            tong = gioHangDao.tinhTongTien(listSP);
                            Date currentDate = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String date=dateFormat.format(currentDate);
                            donHangDao = new DonHangDao(getContext());
                            donHangDao.add(idKH, tong, 0 ,date, listSP);

                            Toast.makeText(getContext(), "Đơn hàng của bạn đã được gửi", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            gioHangDao.xoaHetGioHangChiTiet(idGioHang);
                        }
                        else {
                            Toast.makeText(getContext(), "vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    }

                });
                alertDialogBuilder.setNegativeButton("Không", (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                });
                View view = LayoutInflater.from(getContext()).inflate(R.layout.iteam_add_qldonhang, null);
                alertDialogBuilder.setView(view);
                TextView txtNgay=view.findViewById(R.id.txtNgayThue1);
                TextView txtTongTien=view.findViewById(R.id.txtTongTien1);
                RecyclerView rcv=view.findViewById(R.id.rcvQLDHSanPham1);
                txtNgay.setText(date1);
                txtTongTien.setText(String.valueOf(tong));
                ChiTeitAddSPDonHang ct = new ChiTeitAddSPDonHang(getContext(), listSP);
                rcv.setAdapter(ct);
                rcv.setLayoutManager(new LinearLayoutManager(getContext()));

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                view.findViewById(R.id.btnXacNhan1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(getContext());
                        alertDialogBuilder1.setTitle("Xác nhận gửi");
                        alertDialogBuilder1.setMessage("Đơn hàng của bạn sẽ được gửi ?");
                        alertDialogBuilder1.setPositiveButton("Có", (dialog, which) -> {
                                donHangDao = new DonHangDao(getContext());
                                donHangDao.add(idKH, tong, 0 ,date, listSP);
                                Toast.makeText(getContext(), "Đơn hàng của bạn đã được gửi", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                gioHangDao.xoaHetGioHangChiTiet(idGioHang);
                                alertDialog.dismiss();
                            });
                            alertDialogBuilder1.setNegativeButton("Không", (dialogInterface, which) -> {
                                dialogInterface.dismiss();
                            });

                        AlertDialog alertDialog = alertDialogBuilder1.create();
                        alertDialog.show();

                    }
                });
               view.findViewById(R.id.btnHuy1).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       alertDialog.dismiss();
                   }
               });


            }
        });

        btnCuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SanPhamFragment sanPhamFragment=new SanPhamFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_gio_hang,sanPhamFragment);
                fragmentTransaction.addToBackStack(null); // Để quay lại Fragment giỏ hàng nếu cần
                fragmentTransaction.commit();

            }
        });


        return  view;
    }
}