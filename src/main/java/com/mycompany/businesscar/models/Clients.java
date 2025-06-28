package com.mycompany.businesscar.models;

import java.io.Serializable;

public class Clients implements Serializable{
    private String id;
    private String clientName;
    private String clientLastName;
    private String clientPassport;
    private String clientLocal;
    
    public Clients(){
    }

    public Clients(String id,String clientName,String clientLastName,String clientPassport,String clientLocal){
        this.id = id;
        this.clientLastName = clientLastName;
        this.clientName = clientName;
        this.clientPassport = clientPassport;
        this.clientLocal = clientLocal;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getClientPassport() {
        return clientPassport;
    }

    public void setClientPassport(String clientPassport) {
        this.clientPassport = clientPassport;
    }

    public String getClientLocal() {
        return clientLocal;
    }

    public void setClientLocal(String clientLocal) {
        this.clientLocal = clientLocal;
    }
    
}
