package com.example.duan1_nhom5.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_nhom5.R;
import com.example.duan1_nhom5.adapter.AdapterSP;
import com.example.duan1_nhom5.adapter.SanPhamAdapter;
import com.example.duan1_nhom5.dao.SanPhamDao;
import com.example.duan1_nhom5.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class SanPhamFragment extends Fragment {
    private ArrayList<SanPham> list = new ArrayList<>();
    private SanPhamDao sanPhamDao;
    private AdapterSP adapterSP;
    private RecyclerView rycSP;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_san_pham, container, false);

        // Khởi tạo các thành phần
        rycSP = view.findViewById(R.id.rycsp);
        sanPhamDao = new SanPhamDao(getContext());
        list = sanPhamDao.getAllProductsWithDetails();
        adapterSP = new AdapterSP(getContext(), list);

        // Thiết lập RecyclerView
        rycSP.setAdapter(adapterSP);
        rycSP.setLayoutManager(new LinearLayoutManager(getContext()));



        return view;
    }
}