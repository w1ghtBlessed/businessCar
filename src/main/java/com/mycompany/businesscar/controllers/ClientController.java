package com.mycompany.businesscar.controllers;

import com.mycompany.businesscar.models.Clients;
import com.mycompany.businesscar.models.HistoryClient;
import com.mycompany.businesscar.models.TableClients;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;
import org.primefaces.PrimeFaces;

@Named("clientController")
@ViewScoped
public class ClientController implements Serializable {

    @Resource(lookup = "jdbc/crm")
    DataSource dataSource;
    @Inject
    private TableClients model;
    private List<HistoryClient> clients;
    private HistoryClient selectedClient;

    @PostConstruct
    public void init() {
        model.setDataSource(this.dataSource);
        clients = model.getClients();
    }

    public void saveClient(){
        System.out.println("CONT1");
        System.out.println("Name="+selectedClient.getClient().getClientName());
        model.setDataSource(dataSource);
        model.setSelectedClient(this.selectedClient);
        model.saveClient();
        PrimeFaces.current().ajax().update("tableHistory:dt-clients-history");
    }
    public List<HistoryClient> getClients() {
        return clients;
    }

    public void setClients(List<HistoryClient> clients) {
        this.clients = clients;
    }

    public HistoryClient getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(HistoryClient selectedClient) {
        this.selectedClient = selectedClient;
    }

    public void openNew() {
        this.selectedClient = new HistoryClient();
        this.selectedClient.setClient(new Clients());
    }

    public void deleteClient() {
        model.setSelectedClient(selectedClient);
        model.deleteClient();
        this.clients.remove(this.selectedClient);
        selectedClient = null;
        PrimeFaces.current().ajax().update("tableHistory:dt-clients-history");
    }
}
