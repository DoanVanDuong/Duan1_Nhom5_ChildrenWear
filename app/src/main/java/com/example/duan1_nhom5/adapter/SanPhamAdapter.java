package com.example.duan1_nhom5.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom5.R;
import com.example.duan1_nhom5.dao.SanPhamDao;
import com.example.duan1_nhom5.model.SanPham;

import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SanPham> list;
    private SanPhamDao sanPhamDao;


    public SanPhamAdapter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
        sanPhamDao = new SanPhamDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = list.get(position);
        holder.textViewName.setText(String.valueOf(sanPham.getName()));
        holder.textViewQuantity.setText(String.valueOf(sanPham.getQuantity()));
        holder.textViewPrice.setText(String.valueOf(sanPham.getPrice()));
        holder.textViewBrand.setText(String.valueOf(sanPham.getBrand()));
        holder.textViewSize.setText(String.valueOf(sanPham.getSize()));
        holder.textViewColor.setText(String.valueOf(sanPham.getColor()));

        // Xóa sản phẩm
        holder.imgxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(holder.getAdapterPosition(), sanPham.getId());
            }
        });

        // Sự kiện nhấn giữ để cập nhật sản phẩm
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showUpdateDialog(holder.getAdapterPosition(), sanPham);
                return true;
            }
        });
    }

    private void showUpdateDialog(final int position, final SanPham sanPham) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Cập nhật sản phẩm");

        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_sanpham, null);
        builder.setView(view);

        final TextView editTextName = view.findViewById(R.id.editTextName);
        final TextView editTextQuantity = view.findViewById(R.id.editTextQuantity);
        final TextView editTextPrice = view.findViewById(R.id.editTextPrice);
        final TextView editTextBrand = view.findViewById(R.id.editTextBrand);
        final TextView editTextSize = view.findViewById(R.id.editTextSize);
        final TextView editTextColor = view.findViewById(R.id.editTextColor);

        editTextName.setText(sanPham.getName());
        editTextQuantity.setText(String.valueOf(sanPham.getQuantity()));
        editTextPrice.setText(String.valueOf(sanPham.getPrice()));
        editTextBrand.setText(sanPham.getBrand());
        editTextSize.setText(sanPham.getSize());
        editTextColor.setText(sanPham.getColor());

        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lấy thông tin mới từ các EditText
                String newName = editTextName.getText().toString();
                String quantityText = editTextQuantity.getText().toString();
                String priceText = editTextPrice.getText().toString();
                String newBrand = editTextBrand.getText().toString();
                String newSize = editTextSize.getText().toString();
                String newColor = editTextColor.getText().toString();

                if (newName.isEmpty() || quantityText.isEmpty() || priceText.isEmpty() || newSize.isEmpty() || newColor.isEmpty() || newBrand.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Chuyển đổi số lượng và giá thành kiểu số nguyên
                int newQuantity = Integer.parseInt(quantityText);
                int newPrice = Integer.parseInt(priceText);

                // Kiểm tra số lượng và giá có lớn hơn 0 không
                if (newQuantity < 0 || newPrice <= 0) {
                    Toast.makeText(context, "Số lượng và giá phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Cập nhật thông tin sản phẩm trong danh sách và cơ sở dữ liệu
                SanPham updatedSanPham = new SanPham(sanPham.getId(), newName, newQuantity, newPrice, newBrand, newSize, newColor);
                list.set(position, updatedSanPham);
                sanPhamDao.updateProduct(updatedSanPham);

                // Thông báo cập nhật thành công và cập nhật giao diện
                Toast.makeText(context, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });


        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void showDeleteConfirmationDialog(final int position, final int sanPhamId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?")
                .setCancelable(true)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Xóa sản phẩm từ cơ sở dữ liệu
                        sanPhamDao.deleteSanPham(sanPhamId);
                        // Xóa sản phẩm khỏi danh sách
                        deleteItem(position);
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Không làm gì cả
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void deleteItem(int position) {
        // Xóa sản phẩm khỏi danh sách
        list.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewColor, textViewSize, textViewBrand, textViewPrice, textViewQuantity;

        ImageView imgxoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewColor = itemView.findViewById(R.id.textViewColor);
            textViewSize = itemView.findViewById(R.id.textViewSize);
            textViewBrand = itemView.findViewById(R.id.textViewBrand);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            imgxoa = itemView.findViewById(R.id.imgxoa);
        }
    }
}
