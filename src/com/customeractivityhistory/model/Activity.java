package com.customeractivityhistory.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Activity {
    private String description;
    private LocalDateTime timestamp;

    //Tarix və zaman formatını standartlaşdırmaq üçün Formatter.
    protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Activity(String description, LocalDateTime timestamp) {
        this.description = description;
        //  Konstruktorda parametr olaraq gələn `timestamp` dəyəri istifadə edilməlidir.
        // Əvvəlki kodda `LocalDateTime.now()` çağırılırdı ki, bu da parametri mənasız edirdi.

        this.timestamp = timestamp;
    }

    // --- Getters ---
    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // YENİLİK: Alt siniflərin də istifadə edə biləcəyi ortaq məlumat formatı.
    public String getFormattedTimestamp() {
        return timestamp.format(FORMATTER);
    }

    // YENİLİK: Fayla yazmaq üçün standart format təyin edirik.
    public abstract String toFileString();

    @Override
    public String toString() {
        return "[" + getFormattedTimestamp() + "] - " + description;
    }
}