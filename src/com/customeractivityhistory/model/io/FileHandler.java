package com.customeractivityhistory.model.io;

import com.customeractivityhistory.model.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String DELIMITER = "\\|"; // Fayl formatı üçün ayırıcı

    /**
     * YENİLİK: Bütün müştəriləri və onların fəaliyyətlərini fayla yazır.
     * @param customers Saxlanılacaq müştərilərin siyahısı
     * @param fileName Faylın adı
     * @throws IOException
     */
    public void saveData(List<Customer> customers, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Customer customer : customers) {
                // Müştəri məlumatını yazırıq: C|ID|AD
                writer.write(String.format("C|%d|%s", customer.getId(), customer.getName()));
                writer.newLine();
                // Həmin müştərinin bütün fəaliyyətlərini yazırıq.
                for (Activity activity : customer.getActivities()) {
                    writer.write(activity.toFileString());
                    writer.newLine();
                }
            }
        }
    }

    /**
     * YENİLİK: Fayldan bütün məlumatları oxuyur və obyektlərə çevirir.
     * @param fileName Faylın adı
     * @return Müştərilərin siyahısı
     * @throws IOException
     */
    public List<Customer> loadData(String fileName) throws IOException {
        List<Customer> customers = new ArrayList<>();
        File file = new File(fileName);

        // Əgər fayl mövcud deyilsə, boş siyahı qaytarırıq.
        if (!file.exists()) {
            return customers;
        }

        Customer currentCustomer = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(DELIMITER);
                String type = parts[0];

                switch (type) {
                    case "C": // Sətir müştəri məlumatıdırsa
                        int id = Integer.parseInt(parts[1]);
                        String name = parts[2];
                        currentCustomer = new Customer(id, name);
                        customers.add(currentCustomer);
                        break;
                    case "LOGIN": // Sətir login fəaliyyətidirsə
                        if (currentCustomer != null) {
                            LoginActivity login = new LoginActivity(parts[1], LocalDateTime.parse(parts[2]), parts[3], parts[4], parts[5]);
                            currentCustomer.addActivity(login);
                        }
                        break;
                    case "PURCHASE": // Sətir alış-veriş fəaliyyətidirsə
                        if (currentCustomer != null) {
                            PurchaseActivity purchase = new PurchaseActivity(parts[1], LocalDateTime.parse(parts[2]), Double.parseDouble(parts[3]), parts[4]);
                            currentCustomer.addActivity(purchase);
                        }
                        break;
                }
            }
        }
        return customers;
    }
}