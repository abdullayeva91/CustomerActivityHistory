package com.customeractivityhistory.model;

import java.time.LocalDateTime;

public class LoginActivity extends Activity{
    private String ipAddress;
    private String device;
    private String browserType;

    public LoginActivity(String description, LocalDateTime timestamp, String ipAddress, String device, String browserType) {
        super(description, timestamp);
        this.ipAddress = ipAddress;
        this.device = device;
        this.browserType = browserType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getDevice() {
        return device;
    }

    public String getBrowserType() {
        return browserType;
    }

    @Override
    public String toFileString() {
            return String.format("LOGIN|%s|%s|%s|%s|%s", getDescription(), getTimestamp(), getIpAddress(), getDevice(), getBrowserType());
    }

    @Override
    public String toString() {
        return "LoginActivity{" +
                "ipAddress='" + ipAddress + '\'' +
                ", device='" + device + '\'' +
                ", browserType='" + browserType + '\'' +
                '}';
    }
}
