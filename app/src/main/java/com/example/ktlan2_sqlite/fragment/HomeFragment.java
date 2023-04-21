package com.example.ktlan2_sqlite.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ktlan2_sqlite.R;
import com.example.ktlan2_sqlite.UpdateDeleteActivity;
import com.example.ktlan2_sqlite.adapter.RecycleViewAdapter;
import com.example.ktlan2_sqlite.database.SQLiteHelper;
import com.example.ktlan2_sqlite.model.Item;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class HomeFragment extends Fragment implements RecycleViewAdapter.ItemListener {
    private RecycleViewAdapter adapter;
    private RecyclerView rcv;
    private SQLiteHelper db;
    private TextView tvTong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void OnItemClick(View view, int p) {
        Item item = adapter.getItem(p);
        Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv = view.findViewById(R.id.recycleView);
        tvTong = view.findViewById(R.id.tvTong);
        adapter = new RecycleViewAdapter();
        db = new SQLiteHelper(getContext());
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        List<Item> list = db.getByDate(f.format(date));
        adapter.setList(list);
        tvTong.setText("Tổng tiền: " + tong(list));
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);
        adapter.setItemListener(this);

    }

    private int tong(List<Item> list) {
        int temp = 0;
        for (Item i : list)
            temp += Integer.parseInt(i.getPrice());
        return temp;
    }

    @Override
    public void onResume() {
        super.onResume();
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        List<Item> list = db.getByDate(f.format(date));
        adapter.setList(list);
        tvTong.setText("Tổng tiền: " + tong(list));
    }
}