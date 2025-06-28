package com.mycompany.businesscar.models;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;
import org.primefaces.PrimeFaces;

public class TableClients implements Serializable {

    private List<HistoryClient> clients;
    DataSource dataSource;
    private HistoryClient selectedClient;


    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public HistoryClient getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(HistoryClient selectedClient) {
        this.selectedClient = selectedClient;
    }
    
    
    public List<HistoryClient> getClients() {
        try (Connection con = dataSource.getConnection()) {
            String insertQuery = "SELECT * FROM data.historyClients join data.listClients as a on cl_lcl_id = lcl_id;";
            PreparedStatement ps = con.prepareStatement(insertQuery);
            ResultSet rs = ps.executeQuery();
            clients = new ArrayList<>();
            while (rs.next()) {
                Clients client = new Clients();
                HistoryClient cl = new HistoryClient();
                cl.setId(rs.getString("cl_id"));
                cl.setNumberContract(rs.getInt("cl_numbercontract"));
                cl.setClient(client);
                client.setClientLastName(rs.getString("lcl_last_name"));
                client.setClientName(rs.getString("lcl_name"));
                client.setClientPassport(rs.getString("lcl_pasport"));
                client.setClientLocal(rs.getString("lcl_local"));
                cl.setDate(rs.getString("cl_create_date"));
                cl.setDays(rs.getInt("cl_days"));
                clients.addFirst(cl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clients;
    }
    public void saveClient() {
        if (this.selectedClient.getId() == null) {
            this.selectedClient.setId((UUID.randomUUID().toString()));
            clients.addFirst(new HistoryClient(this.selectedClient.getId(),
                    this.selectedClient.getNumberContract(),
                    this.selectedClient.getDate(),
                    this.selectedClient.getDays()));
            java.util.Date utilDate = new java.util.Date();
            Date sqlDate = new Date(utilDate.getTime());
            try (Connection con = dataSource.getConnection()) {
                String insertQuery = "Insert into data.clients(cl_id,cl_first_name,cl_cl_last_name,cl_create_date) values(?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(insertQuery);
                ps.setString(1, this.selectedClient.getId());
                ps.setDate(4, sqlDate);

            } catch (Exception e) {
                e.printStackTrace();
            }

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(this.selectedClient.getNumberContract() + " Added"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(this.selectedClient.getNumberContract() + " Updated"));

        }

        PrimeFaces.current().ajax().update("tableHistory:growl", "tableHistory:dt-clients");
    }

    public void setClients(List<HistoryClient> clients) {
        this.clients = clients;
    }

    public void init() {

    }
}
