package com.example.ktlan2_sqlite.model;

import java.io.Serializable;
import java.util.List;

public class Item implements Serializable {
    private int id;
    private String title, category, price, date, scope;
    private List<String> object;
    private int rate;

    public Item() {
    }

    public Item(int id, String title, String category, String price, String date, String scope, int rate, List<String> object) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.date = date;
        this.scope = scope;
        this.rate = rate;
        this.object = object;
    }

    public Item(String title, String category, String price, String date, String scope, int rate, List<String> object) {
        this.title = title;
        this.category = category;
        this.price = price;
        this.date = date;
        this.scope = scope;
        this.rate = rate;
        this.object = object;
    }

    public List<String> getObject() {
        return object;
    }

    public void setObject(List<String> object) {
        this.object = object;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
