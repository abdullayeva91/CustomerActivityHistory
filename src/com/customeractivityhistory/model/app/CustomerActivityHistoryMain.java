package com.customeractivityhistory.model.app;

import com.customeractivityhistory.model.Activity;
import com.customeractivityhistory.model.Customer;
import com.customeractivityhistory.model.LoginActivity;
import com.customeractivityhistory.model.PurchaseActivity;
import com.customeractivityhistory.model.exception.CustomerNotFoundException;
import com.customeractivityhistory.model.service.ActivityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class CustomerActivityHistoryMain {

      private static Scanner scanner = new Scanner(System.in);
      private static ActivityManager<Activity> activityActivityManager= new ActivityManager<>();

    public static void main(String[] args) {
        while (true){
            System.out.println("-----Menu-----");
            System.out.println("1. Add Customer ");
            System.out.println("2. Add activity ");
            System.out.println("3. View history");
            System.out.println("4. Exit");
             try {
                 int secim= scanner.nextInt();
                 scanner.nextLine();
                 switch (secim){
                     case 1:
                         System.out.println("Ad customer ");
                         System.out.println("Enter customer ID:  ");
                         String customerId= scanner.nextLine();
                         if (!customerId.isEmpty()){
                             System.out.println("Enter customer's name: ");
                             String customerName= scanner.nextLine();
                             System.out.println("Enter email");
                             String email= scanner.nextLine();
                             System.out.println("Customer added successfully: ");
                             Customer customer=new Customer(Integer.parseInt(customerId),customerName);
                             activityActivityManager.addCustomer(customer);

                         }else {
                             System.out.println("Customer Id cannot be empty! ");
                         }
                         break;
                     case 2:
                         System.out.println("Add activity: ");
                         System.out.println("Enter customer ID");
                         String customerID= scanner.nextLine();
                          if (!customerID.isEmpty()){
                              try{
                                  Customer customer= activityActivityManager.findCustomerById(customerID);
                                  System.out.println("Enter activity type(login/Purchase): ");
                                  String activityType= scanner.nextLine();
                                  System.out.println(" Enter description");
                                  String description= scanner.nextLine();
                                  Activity activity= null;
                                  if (activityType.equalsIgnoreCase("login")){
                                      System.out.println("Enter IP address: ");
                                      String ip=scanner.nextLine();
                                      System.out.println("Enter device: ");
                                      String device=scanner.nextLine();
                                      System.out.println("Enter browser: ");
                                      String browser=scanner.nextLine();
                                      activity= new LoginActivity(description, LocalDateTime.now(),ip,device,browser);
                                  } else if (activityType.equalsIgnoreCase("Purchase")) {
                                      System.out.println("Enter amount: ");
                                      double amount=scanner.nextDouble();
                                      scanner.nextLine();
                                      System.out.println("Enter product name");
                                      String product=scanner.nextLine();

                                      activity= new PurchaseActivity(description, LocalDateTime.now(),amount,product);
                                  }
                                  if (activity!=null){
                                      activityActivityManager.addActivity(customer,activity);
                                      System.out.println("Activity added successfully");
                                  }else {
                                      System.out.println("Invalid activity type: ");
                                  }
                              }catch (CustomerNotFoundException e){
                                  System.out.println("Customer not found! Please add customer: ");
                              }

                          }else {
                              System.out.println("Customer ID cannot be empty!!! ");
                          }
                          break;
                     case 3:
                         System.out.println("View history: ");
                         System.out.println("Enter customer id");
                         String historyId= scanner.nextLine();
                        if (!historyId.isEmpty()){
                            try{
                                Customer customer =activityActivityManager.findCustomerById(historyId);
                                List<Activity> activities=customer.getActivities();
                                System.out.println("Customer history"+customer.getName());
                                System.out.println("-------------------------------");
                                if (activities.isEmpty()){
                                    System.out.println("No activities found for this customer: ");
                                }else {
                                    for (int i=0; i< activities.size();i++){
                                        Activity activity= activities.get(i);
                                        System.out.println(activity);
                                    }
                                }
                            }catch (CustomerNotFoundException e){
                                System.out.println("Customer not found!!!! ");
                            }

                        }else {
                            System.out.println("Customer id cannot be empty");
                        }
                        break;
                     case 4:
                         System.out.println("Exiting!............");
                         scanner.close();
                         return;
                     default:
                         System.out.println("Invalid choice! Please select between 1-4: ");

                 }
             }catch (Exception e){
                 System.out.println("Error: Please enter a valid number");
                 scanner.nextLine();
             }
        }
    }
}
