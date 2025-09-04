package com.customeractivityhistory.model.io;

import com.customeractivityhistory.model.Activity;
import com.customeractivityhistory.model.Customer;

import java.io.*;

public class FileHandler {
    public void saveActivities(Customer customer, String fileName) throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))){
            bw.write("Activities "+ customer.getId()+ customer.getName());
            for (Activity a : customer.getActivities()){
                bw.write(a.toString());
            }
        }

    }

    public void  readActivities (Customer customer, String fileName) throws IOException{
    try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line=br.readLine())!=null){
            System.out.println(line);
        }
    }

    }

}
