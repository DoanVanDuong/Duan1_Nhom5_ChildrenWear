package com.example.duan1_nhom5.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom5.R;
import com.example.duan1_nhom5.dao.DonHangDao;
import com.example.duan1_nhom5.model.DonHang;
import com.example.duan1_nhom5.model.DonHangChiTiet;
import com.example.duan1_nhom5.dao.DonHangChiTietDao;
import com.example.duan1_nhom5.model.NhanVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DonHang> list;
    private DonHangDao donHangDao;
    private DonHangChiTietDao donHangChiTietDao;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public DonHangAdapter(Context context, ArrayList<DonHang> list) {
        this.context = context;
        this.list = list;
        donHangChiTietDao = new DonHangChiTietDao(context);
        donHangDao = new DonHangDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_qldonhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHang donHang = list.get(position);
        holder.txtMaDonHang.setText(String.valueOf(donHang.getId()));
        holder.txtTenKhachHang.setText(donHang.getTenKH());
        holder.txtTenNhanVien.setText(donHang.getTenNV());
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
            holder.btnHuy.setOnClickListener(v -> {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Xác nhận hủy");
                alertDialogBuilder.setMessage("Bạn có chắc chắn muốn hủy ?");
                alertDialogBuilder.setPositiveButton("Có", (dialog, which) -> {
                    donHangChiTietDao.deleteByDonHangId(donHang.getId());
                    donHangDao.delete(donHang.getId());
                    list.remove(position);
                    notifyDataSetChanged();

                    Toast.makeText(holder.itemView.getContext(), "Hủy thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });
                alertDialogBuilder.setNegativeButton("Không", (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            });
            holder.btnXacNhan.setOnClickListener(v -> {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Xác nhận đơn hàng");
                alertDialogBuilder.setMessage("Đơn hàng sẽ được xác nhận?");
                alertDialogBuilder.setPositiveButton("Có", (dialog, which) -> {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                    String username = sharedPreferences.getString("username", "");
                    String password = sharedPreferences.getString("password", "");
                    NhanVien nhanVien=donHangDao.getNhanVienByUsernameAndPassword(username,password);
                    donHangDao.updateNameAndStatus(donHang.getId(), nhanVien.getId(), 1);
                    list.get(position).setTenNV(nhanVien.getTenNV());
                    list.get(position).setTrangThai(1);
                    ArrayList<DonHangChiTiet> listCT = donHangChiTietDao.getList(donHang.getId());
                    donHangChiTietDao.updateProductQuantities(listCT);
                    notifyDataSetChanged();


                    Toast.makeText(holder.itemView.getContext(), "Xác nhận thành công đơn hàng", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });
                alertDialogBuilder.setNegativeButton("Không", (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            });
        }

        ArrayList<DonHangChiTiet> listCT = donHangChiTietDao.getList(donHang.getId());
        ChiTietDonHangAdapter chiTietDonHangAdapter = new ChiTietDonHangAdapter(context, listCT);
        holder.rcvSanPham.setAdapter(chiTietDonHangAdapter);
        holder.rcvSanPham.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaDonHang, txtTenKhachHang, txtTenNhanVien, txtNgayMua, txtTongTien, txtTrangThai;
        LinearLayout linearLayout;
        Button btnXacNhan, btnHuy;
        RecyclerView rcvSanPham;

        public ViewHolder(@NonNull View itemView) {
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

    ;
}
