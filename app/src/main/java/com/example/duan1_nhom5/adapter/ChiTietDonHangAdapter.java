package com.example.duan1_nhom5.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom5.R;
import com.example.duan1_nhom5.dao.DonHangChiTietDao;
import com.example.duan1_nhom5.dao.DonHangDao;
import com.example.duan1_nhom5.model.DonHang;
import com.example.duan1_nhom5.model.DonHangChiTiet;

import java.util.ArrayList;

public class ChiTietDonHangAdapter extends RecyclerView.Adapter<ChiTietDonHangAdapter.ViewHoder>{
    Context context;
    private ArrayList<DonHangChiTiet> listCT = new ArrayList<>();
    DonHangChiTietDao donHangChiTietDao;
    public ChiTietDonHangAdapter(Context context, ArrayList<DonHangChiTiet> listDH) {
        this.context = context;
        listCT = listDH;
    }
    @NonNull
    @Override
    public ChiTietDonHangAdapter.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qldh_sanpham, parent, false);
        return new ChiTietDonHangAdapter.ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietDonHangAdapter.ViewHoder holder, int position) {
        DonHangChiTiet donHangChiTiet=listCT.get(position);
        holder.txtTen.setText(String.valueOf(donHangChiTiet.getTenSP()));
        holder.txtMau.setText(donHangChiTiet.getMau());
        holder.txtSize.setText(donHangChiTiet.getKichThuoc());
        holder.txtSoLuong.setText(String.valueOf(donHangChiTiet.getSoLuong()));
    }

    @Override
    public int getItemCount() {
        return listCT.size();
    }
    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView txtTen, txtMau, txtSize, txtSoLuong;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtMau = itemView.findViewById(R.id.txtMau);
            txtSize = itemView.findViewById(R.id.txtSize);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
        }
    }
    public void updateData(ArrayList<DonHangChiTiet> newList) {
        listCT = new ArrayList<>(newList);
        notifyDataSetChanged();
    }
}
