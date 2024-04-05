package com.example.duan1_nhom5.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom5.R;
import com.example.duan1_nhom5.dao.GioHangChiTietDao;
import com.example.duan1_nhom5.dao.GioHangDao;
import com.example.duan1_nhom5.model.GioHang;
import com.example.duan1_nhom5.model.GioHangChiTiet;

import java.util.ArrayList;

public class GioHangAdapder extends RecyclerView.Adapter<GioHangAdapder.ViewHolder> {
    private Context context;
    int soLuong=0;
    private ArrayList<GioHang> list;
    private GioHangDao gioHangDao;
    private GioHangChiTietDao gioHangChiTietDao;
    private ArrayList<GioHangChiTiet> list1;


    public GioHangAdapder(Context context, ArrayList<GioHangChiTiet> list1) {
        this.context = context;
        this.list1 = list1;
        gioHangChiTietDao = new GioHangChiTietDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gio_hang,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GioHangChiTiet gioHangChiTiet= list1.get(position);
        holder.txtTen.setText(gioHangChiTiet.getTenSP());
        holder.btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soLuong++;
                holder.txtSoLuong.setText(String.valueOf(soLuong));
                gioHangChiTiet.setSoLuong(String.valueOf(soLuong));
                gioHangChiTietDao.updateSoLuong(gioHangChiTiet);

            }
        });
        holder.btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soLuong > 0) {
                    soLuong--;
                    holder.txtSoLuong.setText(String.valueOf(soLuong));
                    gioHangChiTiet.setSoLuong(String.valueOf(soLuong));
                    gioHangChiTietDao.updateSoLuong(gioHangChiTiet);
                } else {
                    // Hiển thị thông báo khi số lượng đã là 0
                    Toast.makeText(holder.itemView.getContext(), "Số lượng không thể nhỏ hơn 0", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.txtGia.setText(String.valueOf(gioHangChiTiet.getGiaTien()));
        holder.btnHuy.setOnClickListener(v -> {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Xác nhận hủy");
            alertDialogBuilder.setMessage("Bạn có chắc chắn muốn hủy ?");
            alertDialogBuilder.setPositiveButton("Có", (dialog, which) -> {

                gioHangChiTietDao.deleteSanPham(gioHangChiTiet.getId_San_Pham());

                list1.remove(position);
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


    }

    @Override
    public int getItemCount() {
       return list1.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton btnTang,btnGiam,btnHuy;
        TextView txtTen, txtMau, txtSize, txtSoLuong, txtGia,txtHang,txtID,txtTong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnGiam=itemView.findViewById(R.id.btnGiam);
            btnTang=itemView.findViewById(R.id.btnTang);
            txtTen = itemView.findViewById(R.id.lvTen);

            txtSoLuong = itemView.findViewById(R.id.lvSoLuong);
            txtGia = itemView.findViewById(R.id.lvGia);

            btnHuy=itemView.findViewById(R.id.btnHuyGH);
        }
    }
    }
