package com.poornama.logic.object;

import com.poornama.api.logging.GlobalLogger;
import com.poornama.api.objects.Client;
import com.poornama.api.presentation.DataTableGenerator;
import com.poornama.api.presentation.Notification;
import com.poornama.api.presentation.NotificationType;
import com.poornama.data.dao.ClientDAO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author dedunu
 */
@Service
public class ClientLogic {
    private static Logger log = GlobalLogger.getLogger();
    private static String className = Client.class.getName();

    /**
     * Creates the client using HttpServletRequest and returns Notification object with message
     *
     * @param request HttpServletRequest
     * @return the notification with the message
     */
    public Notification createClient(HttpServletRequest request) {
        ClientDAO clientDAO = new ClientDAO();
        Client client = new Client();
        Notification notification = new Notification();

        client.setOrganizationName(request.getParameter("organizationName"));
        client.setAddress(request.getParameter("address"));
        client.setTelephoneNumber(request.getParameter("telephone"));

        try {
            clientDAO.create(client);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.setMessage("Client created successfully.");
            log.info("[" + className + "] createClient: created Client");
        } catch (Exception e) {
            notification.setNotificationType(NotificationType.DANGER);
            notification.setMessage("Something went wrong with creating client. Please try again.");
            log.error("[" + className + "] createClient: failed creating Client");
        }

        return notification;
    }

    /**
     * Edit the client using HttpServletRequest and returns Notification object with message
     *
     * @param request  HttpServletRequest
     * @param clientId String
     * @return the notification with the message
     */
    public Notification editClient(HttpServletRequest request, String clientId) {
        ClientDAO clientDAO = new ClientDAO();
        Client client;
        Notification notification = new Notification();

        try {
            client = clientDAO.getById(Integer.parseInt(clientId));
        } catch (NumberFormatException e) {
            notification.setNotificationType(NotificationType.DANGER);
            notification.setMessage("Updating client failed. Please try again.");
            log.error("[" + className + "] deleteClient: failed parsing client id");
            return notification;
        }

        client.setOrganizationName(request.getParameter("organizationName"));
        client.setAddress(request.getParameter("address"));
        client.setTelephoneNumber(request.getParameter("telephone"));

        try {
            clientDAO.update(client);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.setMessage("Client updated successfully.");
            log.info("[" + className + "] editClient: updated Client");
        } catch (Exception e) {
            notification.setNotificationType(NotificationType.DANGER);
            notification.setMessage("Something went wrong with updating client. Please try again.");
            log.error("[" + className + "] editClient: failed updating Client");
        }

        return notification;
    }

    /**
     * Deletes the client using clientId and returns the Notification object with message
     *
     * @param clientId String
     * @return the notification with message
     */
    public Notification deleteClient(String clientId) {
        Notification notification = new Notification();
        ClientDAO clientDAO = new ClientDAO();

        try {
            Client client = clientDAO.getById(Integer.parseInt(clientId));
            clientDAO.delete(client);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.setMessage("Deleted client successfully");
        } catch (NumberFormatException e) {
            notification.setNotificationType(NotificationType.DANGER);
            notification.setMessage("Deleted client failed. Please try again.");
            log.error("[" + className + "] deleteClient: failed parsing client id");
        } catch (Exception e) {
            notification.setNotificationType(NotificationType.DANGER);
            notification.setMessage("Deleted client failed. Please try again.");
        }

        return notification;
    }

    /**
     * Returns the client object from the given clientId
     *
     * @param clientId String
     * @return Client object
     */
    public Client getClient(String clientId) {
        ClientDAO clientDAO = new ClientDAO();
        Client client;
        try {
            client = clientDAO.getById(Integer.parseInt(clientId));
        } catch (Exception e) {
            log.error("[" + className + "] getClient: error in retrieving Client by Id");
            return null;
        }
        return client;
    }

    /**
     * Returns client list for controller classes as select list
     *
     * @return client list for controller classes as String
     */
    public String getClientSelectList() {
        ClientDAO clientDAO = new ClientDAO();
        List<Client> clientList = clientDAO.getAll();

        String list = "";
        for (Client client : clientList) {
            list = list + "\t\t<option value =\"" + client.getId() + "\">" + client.getOrganizationName() + "</option>\n";
        }
        log.debug("[" + className + "] getClientSelectList()");
        return list;
    }

    /**
     * Returns client table for controller classes as a HTML table
     *
     * @return client table as a HTML string
     */
    public String getClientTable() {
        List<Client> clientList;
        ClientDAO clientDAO = new ClientDAO();
        DataTableGenerator dataTableGenerator = new DataTableGenerator();
        String table;
        clientList = clientDAO.getAll();

        table = dataTableGenerator.getStartTable();
        String dataArray[] = new String[3];
        dataArray[0] = "Organization Name";
        dataArray[1] = "Address";
        dataArray[2] = "Telephone";
        table = table + dataTableGenerator.getTableHeader(dataArray);
        table = table + dataTableGenerator.getStartTableBody();

        for (Client client : clientList) {
            dataArray[0] = client.getOrganizationName();
            dataArray[1] = client.getAddress();
            dataArray[2] = client.getTelephoneNumber();
            table = table + dataTableGenerator.getTableBodyRow(dataArray, "edit/" + client.getId(), "delete/" + client.getId());
        }

        table = table + dataTableGenerator.getEndTableBody();
        table = table + dataTableGenerator.getEndTable();
        return table;
    }
}
