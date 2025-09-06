package com.customeractivityhistory.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int id;
    private String name;
    private List<Activity> activities;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
        this.activities = new ArrayList<>();
    }

    // --- Getters and Setters ---
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Activity> getActivities() {
        return new ArrayList<>(activities); // Təhlükəsizlik üçün siyahının kopyasını qaytarırıq.
    }

    // DÜZƏLİŞ: Metodun adı daha anlaşıqlı hala gətirildi.
    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    @Override
    public String toString() {
        return "Müştəri [ID=" + id + ", Ad='" + name + "']";
    }
}