package com.example.ktlan2_sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.ktlan2_sqlite.model.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ChiTieu.db";
    private static int DATABASE_VERSION = 1;


    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE items(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT, category TEXT, price TEXT, date TEXT, scope TEXT, rate INTEGER, object TEXT)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public List<Item> getAll() {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        // sắp xếp tăng dần date asc
//        String order = "date ASC";
        String order = "rate DESC";
        Cursor rs = sqLiteDatabase.query("items",
                null, null, null,
                null, null, order);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            String scope = rs.getString(5);
            String objectStr = rs.getString(7);
            Log.e("objectStr", objectStr);
            List<String> object = new ArrayList<>();
            if (objectStr != null) {
                String[] arr = objectStr.split(",");
                for (String s : arr) {
                    object.add(s.trim());
                }
            }
            int rate = rs.getInt(6);
            list.add(new Item(id, title, category, price, date, scope, rate, object));
        }
        return list;
    }

    public long addItem(Item i) {
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("category", i.getCategory());
        values.put("price", i.getPrice());
        values.put("date", i.getDate());
        values.put("scope", i.getScope());
        values.put("rate", i.getRate());
        String object = "";
        for (int j = 0; j < i.getObject().size() - 1; j++) object += i.getObject().get(j) + ", ";
        object += i.getObject().get(i.getObject().size() - 1);

        values.put("object", object);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("items", null, values);
    }

    public List<Item> getByDate(String date) {
        List<Item> list = new ArrayList<>();
        String whereClause = "date like ?";
        String[] whereArgs = {date};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("items",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String scope = rs.getString(5);
            int rate = rs.getInt(6);
            String objectStr = rs.getString(7);
            List<String> object = new ArrayList<>();
            if (objectStr != null) {
                String[] arr = objectStr.split(",");
                for (String s : arr) {
                    object.add(s.trim());
                }
            }
            list.add(new Item(id, title, category, price, date, scope, rate, object));
        }
        return list;
    }

    public int updateItem(Item i) {
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("category", i.getCategory());
        values.put("price", i.getPrice());
        values.put("date", i.getDate());
        values.put("scope", i.getScope());
        values.put("rate", i.getRate());
        List<String> object = i.getObject();
        String objectStr = "";
        for (String s : object) objectStr += s + ", ";
        values.put("object", objectStr);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(i.getId())};
        return sqLiteDatabase.update("items",
                values, whereClause, whereArgs);
    }

    public int deleteItem(int id) {
        String whereClause = "id= ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase st = getWritableDatabase();
        return st.delete("items", whereClause, whereArgs);
    }

    public List<Item> searchByTitle(String key) {
        List<Item> list = new ArrayList<>();
        String whereClause = "title like ?";
        String[] whereArgs = {"%" + key + "%"};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("items",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            String scope = rs.getString(5);
            String objectStr = rs.getString(7);
            List<String> object = new ArrayList<>();
            if (objectStr != null) {
                String[] arr = objectStr.split(",");
                for (String s : arr) {
                    object.add(s.trim());
                }
            }
            int rate = rs.getInt(6);
            list.add(new Item(id, title, category, price, date, scope, rate, object));
        }
        return list;
    }


    public List<Item> searchByCategory(String category) {
        List<Item> list = new ArrayList<>();
        String whereClause = "category like ?";
        String[] whereArgs = {"%" + category + "%"};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("items",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String price = rs.getString(3);
            String date = rs.getString(4);
            String scope = rs.getString(5);
            int rate = rs.getInt(6);
            String objectStr = rs.getString(7);
            List<String> object = new ArrayList<>();
            if (objectStr != null) {
                String[] arr = objectStr.split(",");
                for (String s : arr) {
                    object.add(s.trim());
                }
            }
            list.add(new Item(id, title, category, price, date, scope, rate, object));
        }
        return list;
    }

    public List<Item> getByDateFromTo(String from, String to) {
        List<Item> list = new ArrayList<>();
        String whereClause = "date BETWEEN ? AND ?";
        String[] whereArgs = {from.trim(), to.trim()};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("items",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            String scope = rs.getString(5);
            int rate = rs.getInt(6);
            String objectStr = rs.getString(7);
            List<String> object = new ArrayList<>();
            if (objectStr != null) {
                String[] arr = objectStr.split(",");
                for (String s : arr) {
                    object.add(s.trim());
                }
            }
            list.add(new Item(id, title, category, price, date, scope, rate, object));
        }
        return list;
    }

    public List<Item> getByScope(String scope) {
        List<Item> list = new ArrayList<>();
        String whereClause = "scope like ?";
        String[] whereArgs = {"%" + scope + "%"};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("items",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            int rate = rs.getInt(6);
            String objectStr = rs.getString(7);
            List<String> object = new ArrayList<>();
            if (objectStr != null) {
                String[] arr = objectStr.split(",");
                for (String s : arr) {
                    object.add(s.trim());
                }
            }
            list.add(new Item(id, title, category, price, date, scope, rate, object));
        }
        return list;
    }

    public List<Item> getByObject(List<String> object) {
        List<Item> list = new ArrayList<>();
        String objectStr = "";
        for (int i = 0; i < object.size(); i++) {
            if (i == object.size() - 1) objectStr += object.get(i);
            else objectStr += object.get(i) + ", ";
        }
        String whereClause = "object like ?";
        String[] whereArgs = {"%" + objectStr + "%"};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("items",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            String scope = rs.getString(5);
            int rate = rs.getInt(6);
            objectStr = rs.getString(7);
            List<String> objectList = new ArrayList<>();
            if (objectStr != null) {
                String[] arr = objectStr.split(",");
                for (String s : arr) {
                    objectList.add(s.trim());
                }
            }
            list.add(new Item(id, title, category, price, date, scope, rate, objectList));
        }
        return list;
    }
}
