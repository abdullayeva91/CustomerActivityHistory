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
        this.activities= new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Activity> getActivities() {
        return new ArrayList<>(activities);
    }
    public void addActivity(Activity activity){
        this.activities.add(activity);
    }

    public void addActivities(Activity activity) {

        this.activities.add(activity);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", activities=" + activities +
                '}';
    }



    }


