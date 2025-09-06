package com.customeractivityhistory.model;

import java.time.LocalDateTime;

public class PurchaseActivity extends Activity {
private double amount;
private String productName;

    public PurchaseActivity(String description, LocalDateTime timestamp, double amount, String productName) {
        super(description, timestamp);
        this.amount = amount;
        this.productName = productName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return super.toString()+ "Product: "+productName+ "Amount: $ "+amount;
    }

    @Override
    public String toFileString() {
        return String.format("PURCHASE|%s|%s|%f|%s", getDescription(), getTimestamp(), amount, productName);
    }
}
