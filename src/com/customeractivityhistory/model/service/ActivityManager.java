package com.customeractivityhistory.model.service;

import com.customeractivityhistory.model.Activity;
import com.customeractivityhistory.model.Customer;
import com.customeractivityhistory.model.exception.CustomerNotFoundException;
import com.customeractivityhistory.model.exception.HistoryNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager <T extends Activity>{
    private static int nextId=1;
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
            List<T> allActivities = new ArrayList<>();

            for (Customer customer : customers) {
                allActivities.addAll((List<T>) customer.getActivities());
            }

            if (allActivities.isEmpty()) {
                throw new HistoryNotFoundException("Heç bir tarixçə tapılmadı");
            }
            return allActivities;
    }

    public List<T> getActivitiesByCustomer(Customer customer) throws CustomerNotFoundException, HistoryNotFoundException {
        if (customer== null){
            throw new CustomerNotFoundException("Musteri tapilmadi");
        }
        if (customer.getActivities().isEmpty()){
            throw new HistoryNotFoundException("Musteri tarixcesi tapilmadi");
        }
        return (List<T>) customer.getActivities();
    }
    public void removeActivity(T activity)throws HistoryNotFoundException{
        boolean found = false;

        for (Customer customer : customers) {
            if (customer.getActivities().remove(activity)) {
                found = true;
                break;
            }
        }

        if (!found) {
            throw new HistoryNotFoundException("Silinəcək fəaliyyət tapılmadı");
        }
    }
    public void clearActivities() {
        for (Customer customer : customers) {
            customer.getActivities().clear();
        }
    }
    public int countActivities(){
        int totalCount = 0;
        for (Customer customer : customers) {
            totalCount += customer.getActivities().size();
        }
        return totalCount;
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
