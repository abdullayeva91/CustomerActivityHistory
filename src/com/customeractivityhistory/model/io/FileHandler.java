package com.customeractivityhistory.model.io;

import com.customeractivityhistory.model.Activity;
import com.customeractivityhistory.model.Customer;
import com.customeractivityhistory.model.LoginActivity;
import com.customeractivityhistory.model.PurchaseActivity;


import java.io.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class FileHandler {
    private static final String DELIMITER = "\\|";


    public void saveActivities(Customer customer, String fileName) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write("Activities " + customer.getId() + customer.getName());
            for (Activity a : customer.getActivities()) {
                bw.write(a.toString());
            }
        }

    }

    public void readActivities(Customer customer, String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }

    }

    public void saveData(List<Customer> customers, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Customer customer : customers) {

                writer.write(String.format("C|%d|%s", customer.getId(), customer.getName()));
                writer.newLine();

                for (Activity activity : customer.getActivities()) {
                    writer.write(activity.toFileString());
                    writer.newLine();
                }
            }
        }
    }

    public List<Customer> loadData(String fileName) throws IOException {
        List<Customer> customers = new ArrayList<>();
        File file = new File(fileName);


        if (!file.exists()) {
            return customers;
        }


        Customer currentCustomer = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(DELIMITER);
                String type = parts[0];

                switch (type) {
                    case "C":
                        int id = Integer.parseInt(parts[1]);
                        String name = parts[2];
                        currentCustomer = new Customer(id, name);
                        customers.add(currentCustomer);
                        break;
                    case "LOGIN":
                        if (currentCustomer != null) {
                            LoginActivity login = new LoginActivity(parts[1],
                                    LocalDateTime.parse(parts[2]),
                                    parts[3], parts[4], parts[5]);
                            currentCustomer.addActivity(login);
                        }
                        break;
                    case "PURCHASE":
                        if (currentCustomer != null) {
                            String priceString = parts[3].replace(',', '.');
                            PurchaseActivity purchase = new PurchaseActivity(parts[1], LocalDateTime.parse(parts[2]), Double.parseDouble(priceString), parts[4]);
                            currentCustomer.addActivity(purchase);
                        }
                        break;
                }
            }
        }
        return customers;

    }


}



