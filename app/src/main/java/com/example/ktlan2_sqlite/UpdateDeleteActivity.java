package com.example.ktlan2_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.example.ktlan2_sqlite.database.SQLiteHelper;
import com.example.ktlan2_sqlite.model.Item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {
    public Spinner spinerCategory;
    private EditText edtTitle, edtPrice, edtDate;
    private Button btnUpdate, btnBack, btnRemove;
    private CheckBox check_box1, check_box2, check_box3;
    private RadioGroup radio_group;
    private RatingBar rating_bar;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        spinerCategory = findViewById(R.id.spinerCategory);
        edtTitle = findViewById(R.id.edtTitle);
        edtPrice = findViewById(R.id.edtPrice);
        edtDate = findViewById(R.id.edtDate);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnBack = findViewById(R.id.btnBack);
        btnRemove = findViewById(R.id.btnRemove);
        radio_group = findViewById(R.id.radio_group);
        rating_bar = findViewById(R.id.rating_bar);
        check_box1 = findViewById(R.id.check_box1);
        check_box2 = findViewById(R.id.check_box2);
        check_box3 = findViewById(R.id.check_box3);

        spinerCategory.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spiner,
                getResources().getStringArray(R.array.category)));


        btnUpdate.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        edtDate.setOnClickListener(this);


        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");
        edtTitle.setText(item.getTitle());
        edtPrice.setText(item.getPrice());
        edtDate.setText(item.getDate());
        int p = 0;
        for (int i = 0; i < spinerCategory.getCount(); i++) {
            if (spinerCategory.getItemAtPosition(i).toString().equalsIgnoreCase(item.getCategory())) {
                p = i;
                break;
            }
        }
        spinerCategory.setSelection(p);
        rating_bar.setRating(item.getRate());
        String scope = item.getScope();
        if (scope.equals("Gia dinh")) radio_group.check(R.id.radio_button_1);
        else radio_group.check(R.id.radio_button_2);

        for (String s : item.getObject()) {
            if (s.equals("Mua sam")) check_box1.setChecked(true);
            if (s.equals("An uong")) check_box2.setChecked(true);
            if (s.equals("Du lich")) check_box3.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        SQLiteHelper db = new SQLiteHelper(this);
        if (v == edtDate) {
            final Calendar c = Calendar.getInstance();
            int y = c.get(Calendar.YEAR);
            int m = c.get(Calendar.MONTH);
            int d = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteActivity.this, (view, year, month, dayOfMonth) -> {
                String date = "";
                if (month > 8) {
                    date = dayOfMonth + "/" + (month + 1) + "/" + y;
                } else {
                    date = dayOfMonth + "/0" + (month + 1) + "/" + y;
                }
                edtDate.setText(date);
            }, y, m, d);
            dialog.show();
        }
        if (v == btnBack) {
            finish();
        }
        if (v == btnUpdate) {
            String title = edtTitle.getText().toString();
            String price = edtPrice.getText().toString();
            String date = edtDate.getText().toString();
            String catory = spinerCategory.getSelectedItem().toString();
            int rate = (int) rating_bar.getRating();
            int scopeID = radio_group.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(scopeID);
            String scope = radioButton.getText().toString();
            List<String> object = new ArrayList<>();
            if (check_box1.isChecked()) object.add("Mua sam");
            if (check_box2.isChecked()) object.add("An uong");
            if (check_box3.isChecked()) object.add("Du lich");

            if (!title.isEmpty() && price.matches("\\d+")) {
                int id = item.getId();
                Item item = new Item(id, title, catory, price, date, scope, rate, object);
//                db = new SQLiteHelper(this);
                db.updateItem(item);
                finish();
            }
        }
        if (v == btnRemove) {
            int id = item.getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Thông báo xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa " + item.getTitle() + " không?");
            builder.setIcon(R.drawable.remove);
            builder.setPositiveButton("Có", (dialog, which) -> {
                db.deleteItem(id);
                finish();
            });
            builder.setNegativeButton("Không", (dialog, which) -> {

            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }
}