package com.customeractivityhistory.model.app;

import com.customeractivityhistory.model.Activity;
import com.customeractivityhistory.model.Customer;
import com.customeractivityhistory.model.LoginActivity;
import com.customeractivityhistory.model.PurchaseActivity;
import com.customeractivityhistory.model.exception.CustomerNotFoundException;
import com.customeractivityhistory.model.io.FileHandler;
import com.customeractivityhistory.model.service.ActivityManager;
 import java.io.IOException;
import java.util.InputMismatchException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class CustomerActivityHistoryMain {

      private static Scanner scanner = new Scanner(System.in);
      private static final ActivityManager<Activity> activityActivityManager= new ActivityManager<>();
    private static final FileHandler fileHandler = new FileHandler();
    private static final String DATA_FILE = "customer_data.txt";

    public static void main(String[] args) {
        System.out.println("\n");
        System.out.println("------------------------------------------------------");
        System.out.println("|            MÜŞTƏRİ FƏALİYYƏT TARİXÇƏSİ             |");
        System.out.println("|              💼 İDARƏETMƏ SİSTEMİ 💼               |");
        System.out.println("|               🌟 XOŞ GƏLMİŞSİNİZ! 🌟               |");
        System.out.println("______________________________________________________");


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
                        handleDeleteCustomer();
                        break;
                    case 5:
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
        System.out.println("4. Müsterini sil");
        System.out.println("5. Çıxış (və məlumatları saxlamaq)");
        System.out.print("Seçiminizi daxil edin: ");
    }

    private static void handleAddCustomer() {
        System.out.print("Müştərinin adını daxil edin: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("Müştəri adı boş ola bilməz.");
            return;
        }
        Customer newCustomer = activityActivityManager.addCustomer(name);
        System.out.printf("'%s' adlı müştəri %d ID ilə uğurla əlavə edildi.\n", newCustomer.getName(), newCustomer.getId());
    }

    private static void handleAddActivity() {
        System.out.print("Fəaliyyət əlavə etmək üçün müştəri ID-sini daxil edin: ");
        try {
            int customerId = scanner.nextInt();
            scanner.nextLine();


            Customer customer = activityActivityManager.findCustomerById(customerId);

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


          activityActivityManager.addActivity(customer, newActivity);
            System.out.println("Fəaliyyət uğurla əlavə edildi.");

        } catch (InputMismatchException e) {
            System.out.println("XƏTA: ID rəqəm olmalıdır.");
            scanner.nextLine();
        } catch (CustomerNotFoundException e) {
            System.out.println("XƏTA: " + e.getMessage());
        }
    }

    private static void handleViewHistory() {

        System.out.print("Tarixçəsinə baxmaq üçün müştəri ID-sini daxil edin: ");
        try {
            int customerId = scanner.nextInt();
            scanner.nextLine();

            Customer customer = activityActivityManager.findCustomerById(customerId);
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
    public static void handleDeleteCustomer() {

        List<Customer> allCustomers = activityActivityManager.getAllCustomers();
        if (allCustomers.isEmpty()) {
            System.out.println(" Silinəcək müştəri yoxdur. Əvvəlcə müştəri əlavə edin.");
            return;
        }

        System.out.println(" MÖVCUD MÜŞTƏRİLƏR:");
        System.out.println("----------------------------");
        for (Customer customer : allCustomers) {
            System.out.printf("ID: %d - %s (Fəaliyyət sayı: %d) ",
                    customer.getId(),
                    customer.getName(),
                    customer.getActivities().size());
        }
        System.out.println("Silmək istədiyiniz müştərinin ID-sini daxil edin: ");
        try {
            int customerID = scanner.nextInt();
            scanner.nextLine();
            Customer customerToDelete = null;
            for (Customer customer : allCustomers) {
                if (customer.getId() == customerID) {
                    customerToDelete = customer;
                    break;
                }
            }
            if (customerToDelete == null) {
                System.out.println("Bu ID- ilə müştəri tapılmadı");
                return;
            }
            System.out.println("Ad" + customerToDelete.getName() + "ID" + customerToDelete.getId() + "Fəaliyyət"
                    + customerToDelete.getActivities());
            System.out.println("Silmək istədiyinizə əminsiz? (Hə/yox): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();
            if (confirmation.equalsIgnoreCase("Hə")) {
                boolean deleted = activityActivityManager.removeCustomer(customerID);
                if (deleted) {
                    System.out.println("Müştəri uğurla silindi");

                } else {
                    System.out.println("Silərkən xəta baş verdi");
                }
            } else {
                System.out.println("Silmə əməliyyatı ləğv edildi");
            }

        } catch (InputMismatchException e) {
            System.out.println("XƏTA: ID rəqəm olmalıdır.");
            scanner.nextLine();
        }catch (CustomerNotFoundException e){
            System.out.println("Xəta"+e.getMessage());
        }

    }

    private static void loadDataFromFile() {
        try {
            List<Customer> loadedCustomers = fileHandler.loadData(DATA_FILE);
            activityActivityManager.loadData(loadedCustomers);
            System.out.println(loadedCustomers.size() + " müştəri məlumatı uğurla yükləndi.");
        } catch (IOException e) {
            System.out.println("XƏTA: Məlumatları fayldan oxumaq mümkün olmadı: " + e.getMessage());
        }
    }


    private static void saveDataToFile() {
        try {

            fileHandler.saveData(activityActivityManager.getAllCustomers(), DATA_FILE);
        } catch (IOException e) {
            System.out.println("XƏTA: Məlumatları fayla yazmaq mümkün olmadı: " + e.getMessage());
        }
    }
}