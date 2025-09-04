package com.customeractivityhistory.model.service;

import com.customeractivityhistory.model.Activity;
import com.customeractivityhistory.model.Customer;
import com.customeractivityhistory.model.exception.CustomerNotFoundException;
import com.customeractivityhistory.model.exception.HistoryNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager <T extends Activity>{
    private List<T> activities=new ArrayList<>();
    private List<Customer>customer=new ArrayList<>();

    public void addCustomer(Customer newcustomer)throws CustomerNotFoundException {
      if (newcustomer==null){
          throw new CustomerNotFoundException("Customer cannot be null");
      }
      customer.add(newcustomer);
    }

    public Customer findCustomerById(String customerId) throws CustomerNotFoundException{
        for (Customer c : customer){
            if (String.valueOf(c.getId()).equals(customerId)) {
                return c;
            }
        }
        throw new CustomerNotFoundException("Customer with id"+customerId+" not found");
    }
    public void addActivity(Customer customer, T activity) throws CustomerNotFoundException {
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found!");
        }
        customer.addActivities(activity);
        activities.add(activity);
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
}
