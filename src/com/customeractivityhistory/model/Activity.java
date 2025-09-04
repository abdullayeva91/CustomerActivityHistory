package com.customeractivityhistory.model;

import java.time.LocalDateTime;

public abstract class Activity {
    private String description;
    private LocalDateTime timestamp;

    public Activity(String description, LocalDateTime timestamp) {
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
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
}
