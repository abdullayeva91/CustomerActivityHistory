package com.customeractivityhistory.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Activity {
    private String description;
    private LocalDateTime timestamp;

    protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public Activity(String description, LocalDateTime timestamp) {
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }
    public String getFormattedTimestamp() {
        return timestamp.format(FORMATTER);
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "description='" + description + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public abstract String toFileString();


}
