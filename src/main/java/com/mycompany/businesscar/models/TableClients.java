package com.mycompany.businesscar.models;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.primefaces.PrimeFaces;

public class TableClients implements Serializable {

    private static final Logger LOG = Logger.getLogger(TableClients.class.getName());
    private static final String UPDATE_CLIENT = "UPDATE data.listclients set "
            + "lcl_name= ? , lcl_last_name= ? , lcl_pasport= ? ,lcl_local= ? "
            + "WHERE lcl_id= ?";
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
            String insertQuery = "SELECT * FROM data.historyClients join data.listClients as a on cl_lcl_id = lcl_id WHERE lcl_delete_date isnull;";
            PreparedStatement ps = con.prepareStatement(insertQuery);
            ResultSet rs = ps.executeQuery();
            clients = new ArrayList<>();
            while (rs.next()) {
                Clients client = new Clients();
                HistoryClient cl = new HistoryClient();
                cl.setId(rs.getString("cl_id"));
                cl.setNumberContract(rs.getInt("cl_numbercontract"));
                cl.setClient(client);
                client.setId(rs.getString("lcl_id"));
                client.setClientLastName(rs.getString("lcl_last_name"));
                client.setClientName(rs.getString("lcl_name"));
                client.setClientPassport(rs.getString("lcl_pasport"));
                client.setClientLocal(rs.getString("lcl_local"));
                cl.setDate(rs.getLong("cl_create_date"));
                cl.setDays(rs.getInt("cl_days"));
                clients.addFirst(cl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clients;
    }

    public void deleteClient() {
        if (selectedClient == null) {
            throw new IllegalStateException("selectedClient не задан");
        }
        java.util.Date utilDate = new java.util.Date();
        Date sqlDate = new Date(utilDate.getTime());
        try (Connection con = dataSource.getConnection()) {
            String deleteQuery = "Update data.listClients set lcl_delete_date=? where lcl_id=?";
            PreparedStatement ps = con.prepareStatement(deleteQuery);
            ps.setDate(1, sqlDate);
            ps.setString(2, this.selectedClient.getClient().getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveClient() {
        System.out.println("save1");
        if (this.selectedClient.getId() == null) {
            this.selectedClient.setId((UUID.randomUUID().toString()));
            this.selectedClient.getClient().setId((UUID.randomUUID().toString()));
            clients.addFirst(new HistoryClient(this.selectedClient.getId(),
                    this.selectedClient.getNumberContract(),
                    this.selectedClient.getClient(),
                    this.selectedClient.getDate(),
                    this.selectedClient.getDays()));
            try (Connection con = dataSource.getConnection()) {
                System.out.println("Try1");
                String insertQuery = "INSERT INTO data.listclients(lcl_id,lcl_name,lcl_last_name,lcl_pasport,lcl_local) VALUES (?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(insertQuery);
                ps.setString(1, this.selectedClient.getClient().getId());
                ps.setString(2, this.selectedClient.getClient().getClientName());
                ps.setString(3, this.selectedClient.getClient().getClientLastName());
                ps.setString(4, this.selectedClient.getClient().getClientPassport());
                ps.setString(5, this.selectedClient.getClient().getClientLocal());
                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try (Connection con = dataSource.getConnection()) {
                System.out.println("Try2");
                String insertQuery = "INSERT INTO data.historyclients(cl_id,cl_numbercontract,cl_create_date,cl_days,cl_lcl_id)(?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(insertQuery);
                ps.setString(1, this.selectedClient.getId());
                ps.setInt(2, this.selectedClient.getNumberContract());
                ps.setLong(3, this.selectedClient.getDate());
                ps.setInt(4, this.selectedClient.getDays());
                ps.setString(5, this.selectedClient.getClient().getId());
                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(this.selectedClient.getNumberContract() + " Added"));
        } else {
            try (Connection con = dataSource.getConnection()) {
                System.out.println("Try3");
                try (PreparedStatement ps = con.prepareStatement(UPDATE_CLIENT);) {
                    ps.setString(1, this.selectedClient.getClient().getClientName());
                    ps.setString(2, this.selectedClient.getClient().getClientLastName());
                    ps.setString(3, this.selectedClient.getClient().getClientPassport());
                    ps.setString(4, this.selectedClient.getClient().getClientLocal());
                    ps.setString(5, this.selectedClient.getClient().getId());
                    ps.executeUpdate();
                }

                String updateHistory = "UPDATE data.historyclients set cl_numbercontract=?,cl_create_date=?,cl_days=? WHERE cl_lcl_id=?";

                try (PreparedStatement ps = con.prepareStatement(updateHistory);) {
                    ps.setInt(1, this.selectedClient.getNumberContract());
                    ps.setLong(2, this.selectedClient.getDate());
                    ps.setInt(3, this.selectedClient.getDays());
                    ps.setString(4, this.selectedClient.getClient().getId());
                    ps.executeUpdate();
                }

            } catch (Exception e) {
                LOG.log(Level.SEVERE, "ERROR saveClient", e);
            }

        }

    }

    public void setClients(List<HistoryClient> clients) {
        this.clients = clients;
    }

    public void init() {

    }
}
