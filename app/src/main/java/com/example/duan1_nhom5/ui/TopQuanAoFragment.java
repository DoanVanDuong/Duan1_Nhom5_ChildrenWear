package com.example.duan1_nhom5.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom5.R;
import com.example.duan1_nhom5.adapter.AdapterTop;
import com.example.duan1_nhom5.dao.TopDAO;
import com.example.duan1_nhom5.model.Top;

import java.util.ArrayList;

public class TopQuanAoFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterTop adapterTop;
    private ArrayList<Top> topList;
    private TopDAO topDAO;

    public TopQuanAoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Khởi tạo danh sách và Adapter
        topList = new ArrayList<>();
        adapterTop = new AdapterTop(getContext(), topList);
        // Khởi tạo DAO
        topDAO = new TopDAO(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_quan_ao, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterTop);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Load dữ liệu top sản phẩm bán chạy nhất và cập nhật Adapter
        loadTopSellingProducts();
    }

    private void loadTopSellingProducts() {
        // Xóa dữ liệu cũ
        topList.clear();
        // Thêm dữ liệu mới từ DAO
        topList.addAll(topDAO.getTopSellingProducts());
        // Thông báo cho Adapter là dữ liệu đã thay đổi
        adapterTop.notifyDataSetChanged();
    }
}
