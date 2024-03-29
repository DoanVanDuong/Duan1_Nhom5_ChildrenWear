package com.example.duan1_nhom5.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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
import android.widget.Toast;

import com.example.duan1_nhom5.R;
import com.example.duan1_nhom5.adapter.GioHangAdapder;
import com.example.duan1_nhom5.dao.GioHangChiTietDao;
import com.example.duan1_nhom5.dao.GioHangDao;
import com.example.duan1_nhom5.dao.KhachHangDao;
import com.example.duan1_nhom5.model.GioHang;
import com.example.duan1_nhom5.model.GioHangChiTiet;
import com.example.duan1_nhom5.model.KhachHang;
import com.example.duan1_nhom5.model.NhanVien;

import java.util.ArrayList;

public class GioHangFragment extends Fragment {
    RecyclerView recyclerView;
    GioHangAdapder gioHangAdapter;
    GioHangDao gioHangDao;
    int idGioHang;

    GioHangChiTietDao gioHangChiTietDao;
    Button btnCuahang,btnDathang;

    private ArrayList<GioHangChiTiet> list= new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
                GioHang gioHang= new GioHang();
                list.clear();
                // Thông báo cho RecyclerView biết rằng dữ liệu đã thay đổi
                gioHangAdapter.notifyDataSetChanged();

                // Hiển thị thông báo hoặc thực hiện các công việc khác sau khi đặt hàng thành công
                Toast.makeText(getContext(), "Đặt Hàng Thành Công", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Đặt Hàng Thành Công", Toast.LENGTH_SHORT).show();

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