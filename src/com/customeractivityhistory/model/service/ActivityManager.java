package com.customeractivityhistory.model.service;

import com.customeractivityhistory.model.Activity;
import com.customeractivityhistory.model.Customer;
import com.customeractivityhistory.model.exception.CustomerNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager<T extends Activity> {

    private static int nextId = 1;
    private List<Customer> customers = new ArrayList<>();

    public Customer addCustomer(String name) {
        Customer newCustomer = new Customer(nextId, name);
        customers.add(newCustomer);
        nextId++;
        return newCustomer;
    }

    /**
      Siyahıdakı hər bir müştərini tək-tək yoxlayır, uyğun olanı tapan kimi qaytarır.
      Əgər döngü bitənə qədər tapılmazsa, xəta atır.
     */
    public Customer findCustomerById(int customerId) throws CustomerNotFoundException {
        // Bütün müştərilərin siyahısını döngüyə salırıq.
        for (Customer customer : customers) {
            // Əgər hazırkı müştərinin ID-si axtardığımız ID ilə eynidirsə...
            if (customer.getId() == customerId) {
                // ...həmin müştərini dərhal qaytarırıq və metod işini bitirir.
                return customer;
            }
        }
        // Əgər döngü tam başa çatdısa və heç bir müştəri tapılmadısa,
        // bu sətirə gəlib çıxırıq və xəta mesajı atırıq.
        throw new CustomerNotFoundException("ID " + customerId + " olan müştəri tapılmadı.");
    }

    public void addActivity(Customer customer, T activity) {
        customer.addActivity(activity);
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    /**
      Fayldan yüklənmiş məlumatlar emal edilərkən ən böyük ID-ni tapmaq üçün
     */
    public void loadData(List<Customer> loadedCustomers) {
        if (loadedCustomers != null && !loadedCustomers.isEmpty()) {
            this.customers = loadedCustomers;

            // Ən böyük ID-ni saxlamaq üçün bir dəyişən yaradırıq.
            int maxId = 0;
            // Yüklənmiş müştərilərin siyahısı üzərində döngü qururuq.
            for (Customer customer : loadedCustomers) {
                // Əgər hazırkı müştərinin ID-si indiyə qədər tapdığımız ən böyük ID-dən böyükdürsə...
                if (customer.getId() > maxId) {
                    // ...onda maxId dəyişənini yeniləyirik.
                    maxId = customer.getId();
                }
            }
            // Döngü bitdikdən sonra tapdığımız ən böyük ID-nin bir artığını nextId-yə təyin edirik.
            nextId = maxId + 1;
        }
    }
}