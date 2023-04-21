package com.example.ktlan2_sqlite.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ktlan2_sqlite.R;
import com.example.ktlan2_sqlite.adapter.RecycleViewAdapter;
import com.example.ktlan2_sqlite.database.SQLiteHelper;
import com.example.ktlan2_sqlite.model.Item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SearchFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recycleView;
    private TextView tvTong;
    private Button btnSearch;
    private SearchView search;
    private EditText From, To;
    private RadioGroup radio_group;
    private CheckBox check_box1, check_box2, check_box3;
    private Spinner spinerCategory;
    private RecycleViewAdapter adapter;
    private SQLiteHelper db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycleView = view.findViewById(R.id.recycleView);
        tvTong = view.findViewById(R.id.tvTong);
        btnSearch = view.findViewById(R.id.btnSearch);
        search = view.findViewById(R.id.search);
        From = view.findViewById(R.id.From);
        To = view.findViewById(R.id.To);
        radio_group = view.findViewById(R.id.radio_group);
        check_box1 = view.findViewById(R.id.check_box1);
        check_box2 = view.findViewById(R.id.check_box2);
        check_box3 = view.findViewById(R.id.check_box3);
        spinerCategory = view.findViewById(R.id.spinerCategory);
        String[] arr = getResources().getStringArray(R.array.category);
        String[] arr1 = new String[arr.length + 1];
        arr1[0] = "All";
        for (int i = 0; i < arr.length; i++) {
            arr1[i + 1] = arr[i];
        }
        spinerCategory.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.item_spiner, arr1));

        adapter = new RecycleViewAdapter();
        db = new SQLiteHelper(getContext());
        List<Item> list = db.getAll();
        adapter.setList(list);
        tvTong.setText("Tổng tiền: " + tong(list) + "K");

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recycleView.setLayoutManager(manager);
        recycleView.setAdapter(adapter);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Item> list = db.searchByTitle(s);
                tvTong.setText("Tổng tiền: " + tong(list));
                adapter.setList(list);
                return true;
            }
        });

        From.setOnClickListener(this);
        To.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        spinerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = spinerCategory.getItemAtPosition(position).toString();
                List<Item> list;
                if (!category.equalsIgnoreCase("All")) {
                    list = db.searchByCategory(category);
                } else {
                    list = db.getAll();
                }
                adapter.setList(list);
                tvTong.setText("Tổng tiền: " + tong(list));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private int tong(List<Item> list) {
        int temp = 0;
        for (Item i : list) {
            temp += Integer.parseInt(i.getPrice());
        }
        return temp;
    }

    @Override
    public void onClick(View v) {
        if (v == From) {
            final Calendar c = Calendar.getInstance();
            int y = c.get(Calendar.YEAR);
            int m = c.get(Calendar.MONTH);
            int d = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                String date = "";
                if (month > 8) {
                    date = dayOfMonth + "/" + (month + 1) + "/" + y;
                } else {
                    date = dayOfMonth + "/0" + (month + 1) + "/" + y;
                }
                From.setText(date);
            }, y, m, d);
            dialog.show();
        }
        if (v == To) {
            final Calendar c = Calendar.getInstance();
            int y = c.get(Calendar.YEAR);
            int m = c.get(Calendar.MONTH);
            int d = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                String date = "";
                if (month > 8) {
                    date = dayOfMonth + "/" + (month + 1) + "/" + y;
                } else {
                    date = dayOfMonth + "/0" + (month + 1) + "/" + y;
                }
                To.setText(date);
            }, y, m, d);
            dialog.show();
        }
        if (v == btnSearch) {
            String f = From.getText().toString();
            String t = To.getText().toString();
            List<Item> list1 = db.getAll();
            if (!f.isEmpty() && !t.isEmpty()) list1 = db.getByDateFromTo(f, t);

            List<Item> list2 = db.getAll();
            if (radio_group.getCheckedRadioButtonId() != -1) {
                int scopeID = radio_group.getCheckedRadioButtonId();
                RadioButton radioButton = radio_group.findViewById(scopeID);
                String scope = radioButton.getText().toString();
                list2 = db.getByScope(scope);
            }

            List<Item> list3 = db.getAll();
            List<String> object = new ArrayList<>();
            if (check_box1.isChecked()) object.add(check_box1.getText().toString());
            if (check_box2.isChecked()) object.add(check_box2.getText().toString());
            if (check_box3.isChecked()) object.add(check_box3.getText().toString());
            if (object.size() > 0) list3 = db.getByObject(object);

            List<Item> list = new ArrayList<>();
            for (int i = 0; i < list1.size(); i++) {
                for (int j = 0; j < list2.size(); j++) {
                    if (list1.get(i).getId() == list2.get(j).getId()) {
                        for (int k = 0; k < list3.size(); k++) {
                            if (list2.get(j).getId() == list3.get(k).getId()) {
                                list.add(list3.get(k));
                            }
                        }
                    }
                }
            }

            adapter.setList(list);
            tvTong.setText("Tổng tiền: " + tong(list));
        }

    }
}