package com.customeractivityhistory.model.service;

import com.customeractivityhistory.model.Activity;
import com.customeractivityhistory.model.Customer;
import com.customeractivityhistory.model.exception.CustomerNotFoundException;
import com.customeractivityhistory.model.exception.HistoryNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager <T extends Activity>{
    private static int nextId=1;
    private List<T> activities=new ArrayList<>();
    private List<Customer>customers=new ArrayList<>();

    public Customer addCustomer(String name) {
        Customer newCustomer = new Customer(nextId, name);
        customers.add(newCustomer);
        nextId++;
        return newCustomer;
    }

    public Customer findCustomerById(int customerId) throws CustomerNotFoundException{
        for (Customer customer : customers){
            if (customer.getId()==customerId) {
                return customer;
            }
        }
        throw new CustomerNotFoundException("ID " + customerId + " olan müştəri tapılmadı.");
    }
    public boolean removeCustomer(int customerId) throws CustomerNotFoundException {
        Customer customerToDelete=findCustomerById(customerId);
        if (customerToDelete !=null){
            return customers.remove(customerToDelete);
        }
        return false;
    }

    public void addActivity(Customer customer, T activity) {
        customer.addActivity(activity);
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }



        public List<T> getAllActivities() throws HistoryNotFoundException {
       if (activities.isEmpty()){
           throw new HistoryNotFoundException("Hec bir tarixce tapilmadi");
       }
       return activities;
    }

    public List<T> getActivitiesByCustomer(Customer customer) throws CustomerNotFoundException, HistoryNotFoundException {
        if (customer== null){
            throw new CustomerNotFoundException("Musteri tapilmadi");
        }
        if (customer.getActivities().isEmpty()){
            throw new HistoryNotFoundException("Musteri tarixcesi tapilmadi");
        }
        return activities;
    }
    public void removeActivity(T activity)throws HistoryNotFoundException{
        if (!activities.remove(activity)){
            throw new HistoryNotFoundException("Silinecek musteri tapilmadi");
        }
    }
    public void clearActivities(){
        activities.clear();
    }
    public int countActivities(){
        return activities.size();
    }


    public void loadData(List<Customer> loadedCustomers) {
        if (loadedCustomers != null && !loadedCustomers.isEmpty()) {
            this.customers = loadedCustomers;


            int maxId = 0;

            for (Customer customer : loadedCustomers) {

                if (customer.getId() > maxId) {

                    maxId = customer.getId();
                }
            }
            nextId = maxId + 1;
        }
    }
}
