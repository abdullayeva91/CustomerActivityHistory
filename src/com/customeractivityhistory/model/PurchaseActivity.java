// PurchaseActivity.java
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

    @Override
    public String toString() {
        return super.toString() + String.format(" (Məhsul: %s, Məbləğ: %.2f AZN)", productName, amount);
    }

    //  Məlumatları fayla yazmaq üçün unikal format.
    @Override
    public String toFileString() {
        // Format: TİP|Açıqlama|Tarix|Məbləğ|Məhsul Adı
        return String.format("PURCHASE|%s|%s|%f|%s", getDescription(), getTimestamp(), amount, productName);
    }
}