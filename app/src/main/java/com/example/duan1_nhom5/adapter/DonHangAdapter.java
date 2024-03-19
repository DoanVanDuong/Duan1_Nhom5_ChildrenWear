package com.example.duan1_nhom5.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom5.R;
import com.example.duan1_nhom5.dao.DonHangChiTietDao;
import com.example.duan1_nhom5.dao.DonHangDao;
import com.example.duan1_nhom5.model.DonHang;
import com.example.duan1_nhom5.model.DonHangChiTiet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHoder> {
    Context context;
    private ArrayList<DonHang> list = new ArrayList<>();
    private ArrayList<DonHangChiTiet> listCT = new ArrayList<>();
    DonHangDao donHangDao;
    DonHangChiTietDao donHangChiTietDao;
    ChiTietDonHangAdapter chiTietDonHangAdapter;

    public DonHangAdapter(Context context, ArrayList<DonHang> listDH) {
        this.context = context;
        list = listDH;
    }

    @NonNull
    @Override
    public DonHangAdapter.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_qldonhang, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangAdapter.ViewHoder holder, int position) {
        DonHang donHang = list.get(position);
        holder.txtMaDonHang.setText(String.valueOf(donHang.getId()));
        holder.txtTenKhachHang.setText(donHang.getTenKH());
        holder.txtTenNhanVien.setText(donHang.getTenNV());
        // Format ngày mua từ Date sang String
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngayMuaFormatted = dateFormat.format(donHang.getNgayMua());
        holder.txtNgayMua.setText(ngayMuaFormatted);
        holder.txtTongTien.setText(String.valueOf(donHang.getTongTien()));
        if (donHang.getTrangThai() == 1) {
            holder.txtTrangThai.setText("Thành công");
            holder.txtTrangThai.setTextColor(Color.parseColor("#5F65ED"));
            holder.linearLayout.setVisibility(View.GONE);
        } else {
            holder.txtTrangThai.setText("Chờ xác nhận");
            holder.txtTrangThai.setTextColor(Color.parseColor("#F42936"));
        }
        if (chiTietDonHangAdapter == null) {
            donHangChiTietDao = new DonHangChiTietDao(context);
            listCT = donHangChiTietDao.getList();
            chiTietDonHangAdapter = new ChiTietDonHangAdapter(context, listCT);
            holder.rcvSanPham.setAdapter(chiTietDonHangAdapter);
            holder.rcvSanPham.setLayoutManager(new LinearLayoutManager(context));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView txtMaDonHang, txtTenKhachHang, txtTenNhanVien, txtNgayMua, txtTongTien, txtTrangThai;
        LinearLayout linearLayout;
        Button btnXacNhan, btnHuy;
        RecyclerView rcvSanPham;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            txtMaDonHang = itemView.findViewById(R.id.txtMaDonHang);
            txtTenKhachHang = itemView.findViewById(R.id.txtQLDHTenKhachHang);
            txtTenNhanVien = itemView.findViewById(R.id.txtQLDHTenNV);
            txtNgayMua = itemView.findViewById(R.id.txtNgayThue);
            txtTongTien = itemView.findViewById(R.id.txtTongTien);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            btnHuy = itemView.findViewById(R.id.btnHuy);
            btnXacNhan = itemView.findViewById(R.id.btnXacNhan);
            linearLayout = itemView.findViewById(R.id.lilButon);
            rcvSanPham = itemView.findViewById(R.id.rcvQLDHSanPham);
        }
    }

}
