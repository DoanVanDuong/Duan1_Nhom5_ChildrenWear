package com.example.duan1_nhom5.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom5.R;
import com.example.duan1_nhom5.dao.SanPhamDao;
import com.example.duan1_nhom5.model.SanPham;

import java.util.ArrayList;

public class AdapterSP extends RecyclerView.Adapter<AdapterSP.ViewHolder> {
    private Context context;
    private ArrayList<SanPham> list;
    private SanPhamDao sanPhamDao;


    public AdapterSP(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
        sanPhamDao = new SanPhamDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qlysp, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SanPham sanPham = list.get(position);
        holder.textViewName.setText("Tên: " + sanPham.getName());
        holder.textViewQuantity.setText("Số lượng: " + sanPham.getQuantity());
        holder.textViewPrice.setText("Giá: " + sanPham.getPrice());
        holder.textViewColor.setText("Màu: " + sanPham.getColor());
        holder.textViewSize.setText("Size: " + sanPham.getSize());
        holder.textViewBrand.setText("Thương hiệu: " + sanPham.getBrand());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewColor, textViewSize, textViewBrand, textViewPrice, textViewQuantity;

        ImageView imggiohang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewColor = itemView.findViewById(R.id.textViewColor);
            textViewSize = itemView.findViewById(R.id.textViewSize);
            textViewBrand = itemView.findViewById(R.id.textViewBrand);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            imggiohang = itemView.findViewById(R.id.imggiohang);
        }
    }
}
