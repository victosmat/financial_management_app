package com.example.ktlan2_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
import android.widget.Toast;

import com.example.ktlan2_sqlite.database.SQLiteHelper;
import com.example.ktlan2_sqlite.model.Item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    public Spinner spinerCategory;
    private EditText edtTitle, edtPrice, edtDate;
    private CheckBox check_box1, check_box2, check_box3;
    private Button btnUpdate, btnCancel;
    private RatingBar rating_bar;
    private RadioGroup radio_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        spinerCategory = findViewById(R.id.spinerCategory);
        edtTitle = findViewById(R.id.edtTitle);
        edtPrice = findViewById(R.id.edtPrice);
        edtDate = findViewById(R.id.edtDate);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);
        check_box1 = findViewById(R.id.check_box1);
        check_box2 = findViewById(R.id.check_box2);
        check_box3 = findViewById(R.id.check_box3);
        spinerCategory.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spiner, getResources().getStringArray(R.array.category)));
        btnUpdate.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        edtDate.setOnClickListener(this);
        rating_bar = findViewById(R.id.rating_bar);
        radio_group = findViewById(R.id.radio_group);
    }

    @Override
    public void onClick(View v) {
        if (v == edtDate) {
            final Calendar c = Calendar.getInstance();
            int y = c.get(Calendar.YEAR);
            int m = c.get(Calendar.MONTH);
            int d = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, (view, year, month, dayOfMonth) -> {
                String date = "";
                if (month > 8) date = dayOfMonth + "/" + (month + 1) + "/" + y;
                else date = dayOfMonth + "/0" + (month + 1) + "/" + y;
                edtDate.setText(date);
            }, y, m, d);
            dialog.show();
        }
        if (v == btnCancel) {
            finish();
        }
        if (v == btnUpdate) {
            String title = edtTitle.getText().toString();
            String price = edtPrice.getText().toString();
            String date = edtDate.getText().toString();
            int rate = (int) rating_bar.getRating();
            int scopeID = radio_group.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(scopeID);
            String scope = radioButton.getText().toString();
            String category = spinerCategory.getSelectedItem().toString();
            List<String> object = new ArrayList<>();
            if (check_box1.isChecked()) object.add(check_box1.getText().toString());
            if (check_box2.isChecked()) object.add(check_box2.getText().toString());
            if (check_box3.isChecked()) object.add(check_box3.getText().toString());

            if (!title.isEmpty() && price.matches("\\d+") && !date.isEmpty() && !scope.isEmpty() && !category.isEmpty() && !object.isEmpty()) {
                Item item = new Item(title, category, price, date, scope, rate, object);
                SQLiteHelper db = new SQLiteHelper(this);
                db.addItem(item);
                finish();
            }
        }
    }
}