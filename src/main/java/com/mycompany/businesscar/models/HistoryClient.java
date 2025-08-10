package com.mycompany.businesscar.models;

import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;

public class HistoryClient implements Serializable {

    private String id;
    private int numberContract;
    private Clients client;
    private Long date;
    private int days;

    public HistoryClient() {
        this.date = Instant.now().toEpochMilli();
    }

    public HistoryClient(String id, int numberContract, Clients client, Long date, int days) {
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

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
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
