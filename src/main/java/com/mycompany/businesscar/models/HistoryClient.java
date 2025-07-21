package com.mycompany.businesscar.models;

import java.io.Serializable;

public class HistoryClient implements Serializable {

    private String id;
    private int numberContract;
    private Clients client;
    private String date;
    private int days;

    public HistoryClient() {
    }

    public HistoryClient(String id,int numberContract, Clients client, String date,  int days) {
        this.id = id;
        this.numberContract = numberContract;
        this.client = client;
        this.date = date;
        this.days = days;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumberContract() {
        return numberContract;
    }

    public void setNumberContract(int NumberContract) {
        this.numberContract = NumberContract;
    }
    

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    public void remove(HistoryClient selectedClient) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
