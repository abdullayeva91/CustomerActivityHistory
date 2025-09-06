package com.customeractivityhistory.model.app;

import com.customeractivityhistory.model.*;
import com.customeractivityhistory.model.exception.CustomerNotFoundException;
import com.customeractivityhistory.model.io.FileHandler;
import com.customeractivityhistory.model.service.ActivityManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CustomerActivityHistoryMain {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ActivityManager<Activity> activityManager = new ActivityManager<>();
    private static final FileHandler fileHandler = new FileHandler();
    private static final String DATA_FILE = "customer_data.txt";

    public static void main(String[] args) {
        // Proqram başlayanda məlumatları fayldan yükləyirik
        loadDataFromFile();

        while (true) {
            showMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        handleAddCustomer();
                        break;
                    case 2:
                        handleAddActivity();
                        break;
                    case 3:
                        handleViewHistory();
                        break;
                    case 4:
                        // Proqramdan çıxmazdan əvvəl YADDAŞDAKI BÜTÜN məlumatları saxlayırıq
                        saveDataToFile();
                        System.out.println("Bütün məlumatlar saxlanıldı. Proqramdan çıxılır...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Yanlış seçim! Zəhmət olmasa 1-4 arasında bir rəqəm daxil edin.");
                }
            } catch (InputMismatchException e) {
                System.out.println("XƏTA: Zəhmət olmasa rəqəm daxil edin.");
                scanner.nextLine();
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n----- ANA MENYU -----");
        System.out.println("1. Yeni müştəri əlavə et");
        System.out.println("2. Müştəriyə fəaliyyət əlavə et");
        System.out.println("3. Müştərinin tarixçəsinə bax");
        System.out.println("4. Çıxış (və məlumatları saxlamaq)");
        System.out.print("Seçiminizi daxil edin: ");
    }

    private static void handleAddCustomer() {
        System.out.print("Müştərinin adını daxil edin: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("Müştəri adı boş ola bilməz.");
            return;
        }
        Customer newCustomer = activityManager.addCustomer(name);
        System.out.printf("'%s' adlı müştəri %d ID ilə uğurla əlavə edildi.\n", newCustomer.getName(), newCustomer.getId());
    }

    private static void handleAddActivity() {
        System.out.print("Fəaliyyət əlavə etmək üçün müştəri ID-sini daxil edin: ");
        try {
            int customerId = scanner.nextInt();
            scanner.nextLine();

            // DİQQƏT: Bu metod yaddaşdakı müştəri obyektini tapır
            Customer customer = activityManager.findCustomerById(customerId);

            System.out.print("Fəaliyyət növünü daxil edin (login / purchase): ");
            String type = scanner.nextLine();

            Activity newActivity;
            if ("login".equalsIgnoreCase(type)) {
                System.out.print("Açıqlama daxil edin: ");
                String desc = scanner.nextLine();
                System.out.print("IP Ünvan: ");
                String ip = scanner.nextLine();
                System.out.print("Cihaz: ");
                String device = scanner.nextLine();
                System.out.print("Brauzer: ");
                String browser = scanner.nextLine();
                newActivity = new LoginActivity(desc, LocalDateTime.now(), ip, device, browser);
            } else if ("purchase".equalsIgnoreCase(type)) {
                System.out.print("Açıqlama daxil edin: ");
                String desc = scanner.nextLine();
                System.out.print("Məhsulun adı: ");
                String product = scanner.nextLine();
                System.out.print("Məbləğ: ");
                double amount = scanner.nextDouble();
                scanner.nextLine();
                newActivity = new PurchaseActivity(desc, LocalDateTime.now(), amount, product);
            } else {
                System.out.println("Yanlış fəaliyyət növü.");
                return;
            }

            // DİQQƏT: Fəaliyyət birbaşa yaddaşdakı obyektə əlavə edilir
            activityManager.addActivity(customer, newActivity);
            System.out.println("Fəaliyyət uğurla əlavə edildi.");

        } catch (InputMismatchException e) {
            System.out.println("XƏTA: ID rəqəm olmalıdır.");
            scanner.nextLine();
        } catch (CustomerNotFoundException e) {
            System.out.println("XƏTA: " + e.getMessage());
        }
    }

    private static void handleViewHistory() {
        // Bu metod məlumatları yalnız oxuyur, heç nəyi dəyişmir.
        System.out.print("Tarixçəsinə baxmaq üçün müştəri ID-sini daxil edin: ");
        try {
            int customerId = scanner.nextInt();
            scanner.nextLine();

            Customer customer = activityManager.findCustomerById(customerId);
            List<Activity> activities = customer.getActivities();

            System.out.println("\n--- " + customer.getName() + " adlı müştərinin tarixçəsi ---");
            if (activities.isEmpty()) {
                System.out.println("Bu müştəri üçün heç bir fəaliyyət tapılmadı.");
            } else {
                activities.forEach(System.out::println);
            }
            System.out.println("----------------------------------------");

        } catch (InputMismatchException e) {
            System.out.println("XƏTA: ID rəqəm olmalıdır.");
            scanner.nextLine();
        } catch (CustomerNotFoundException e) {
            System.out.println("XƏTA: " + e.getMessage());
        }
    }

    // Yükləmə metodu
    private static void loadDataFromFile() {
        try {
            List<Customer> loadedCustomers = fileHandler.loadData(DATA_FILE);
            activityManager.loadData(loadedCustomers);
            System.out.println(loadedCustomers.size() + " müştəri məlumatı uğurla yükləndi.");
        } catch (IOException e) {
            System.out.println("XƏTA: Məlumatları fayldan oxumaq mümkün olmadı: " + e.getMessage());
        }
    }

    // Saxlama metodu
    private static void saveDataToFile() {
        try {
            // DİQQƏT: Yaddaşdakı BÜTÜN müştərilərin siyahısı bura ötürülür.
            fileHandler.saveData(activityManager.getAllCustomers(), DATA_FILE);
        } catch (IOException e) {
            System.out.println("XƏTA: Məlumatları fayla yazmaq mümkün olmadı: " + e.getMessage());
        }
    }
}