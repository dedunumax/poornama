package com.poornama.logic.object;

import com.poornama.api.logging.GlobalLogger;
import com.poornama.api.objects.Employee;
import com.poornama.api.objects.EmployeeType;
import com.poornama.api.presentation.DataTableGenerator;
import com.poornama.api.presentation.Notification;
import com.poornama.api.presentation.NotificationType;
import com.poornama.data.dao.EmployeeDAO;
import com.poornama.data.dao.EmployeeTypeDAO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author dedunu
 */
@Service
public class EmployeeLogic {
    private static Logger log = GlobalLogger.getLogger();
    private static String className = EmployeeLogic.class.getName();

    /**
     * Creates the employee using HttpServletRequest and returns Notification object with message
     *
     * @param request HttpServletRequest
     * @return the notification with the message
     */
    public Notification createEmployee(HttpServletRequest request) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        EmployeeTypeDAO employeeTypeDAO = new EmployeeTypeDAO();
        Employee employee = new Employee();
        EmployeeType employeeType;
        Notification notification = new Notification();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date dateOfBirth = new Date();
        Date dateOfJoining = new Date();
        int nic = 0;
        int employeeTypeId = 0;

        try {
            dateOfBirth = simpleDateFormat.parse(request.getParameter("dateOfBirth"));
        } catch (ParseException e) {
            log.error("[" + className + "] createEmployee: Error in parsing dateOfBirth");
        }

        try {
            dateOfJoining = simpleDateFormat.parse(request.getParameter("dateOfJoining"));
        } catch (ParseException e) {
            log.error("[" + className + "] createEmployee: Error in parsing dateOfJoining");
        }

        try {
            nic = Integer.parseInt(request.getParameter("nic"));
        } catch (Exception e) {
            log.error("[" + className + "] createEmployee: Error in parsing nic");
        }

        try {
            employeeTypeId = Integer.parseInt(request.getParameter("employeeType"));
        } catch (Exception e) {
            log.error("[" + className + "] createEmployee: Error in parsing employeeTypeId");
        }

        employeeType = employeeTypeDAO.getById(employeeTypeId);

        employee.setAddress(request.getParameter("address"));
        employee.setDateOfBirth(dateOfBirth);
        employee.setDateOfJoining(dateOfJoining);
        employee.setDescription(request.getParameter("description"));
        employee.setEmergencyContact(request.getParameter("emergencyContact"));
        employee.setFirstName(request.getParameter("firstName"));
        employee.setLastName(request.getParameter("lastName"));
        employee.setTelephoneNumber(request.getParameter("telephone"));
        employee.setNic(nic);
        employee.setEmployeeType(employeeType);

        try {
            employeeDAO.create(employee);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.setMessage("Employee created successfully.");
            log.info("[" + className + "] createEmployee: created Employee");
        } catch (Exception e) {
            notification.setNotificationType(NotificationType.DANGER);
            notification.setMessage("Something went wrong with creating employee. Please try again.");
            log.error("[" + className + "] createEmployee: failed creating employee");
        }

        return notification;
    }

    /**
     * Edit the employee using HttpServletRequest and returns Notification object with message
     *
     * @param request    HttpServletRequest
     * @param employeeId String
     * @return the notification with the message
     */
    public Notification editEmployee(HttpServletRequest request, String employeeId) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        EmployeeTypeDAO employeeTypeDAO = new EmployeeTypeDAO();
        Employee employee = new Employee();
        EmployeeType employeeType;
        Notification notification = new Notification();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date dateOfBirth = new Date();
        Date dateOfJoining = new Date();
        int nic = 0;
        int employeeTypeId = 0;
        int id = 0;
        try {
            dateOfBirth = simpleDateFormat.parse(request.getParameter("dateOfBirth"));
        } catch (ParseException e) {
            log.error("[" + className + "] editEmployee: Error in parsing dateOfBirth");
        }

        try {
            dateOfJoining = simpleDateFormat.parse(request.getParameter("dateOfJoining"));
        } catch (ParseException e) {
            log.error("[" + className + "] editEmployee: Error in parsing dateOfJoining");
        }

        try {
            nic = Integer.parseInt(request.getParameter("nic"));
        } catch (Exception e) {
            log.error("[" + className + "] editEmployee: Error in parsing nic");
        }

        try {
            employeeTypeId = Integer.parseInt(request.getParameter("employeeType"));
        } catch (Exception e) {
            log.error("[" + className + "] editEmployee: Error in parsing employeeTypeId");
        }

        try {
            id = Integer.parseInt(employeeId);
        } catch (Exception e) {
            log.error("[" + className + "] editEmployee: Error in parsing employeeId");
        }
        employeeType = employeeTypeDAO.getById(employeeTypeId);
        employee.setId(id);
        employee.setAddress(request.getParameter("address"));
        employee.setDateOfBirth(dateOfBirth);
        employee.setDateOfJoining(dateOfJoining);
        employee.setDescription(request.getParameter("description"));
        employee.setEmergencyContact(request.getParameter("emergencyContact"));
        employee.setFirstName(request.getParameter("firstName"));
        employee.setLastName(request.getParameter("lastName"));
        employee.setTelephoneNumber(request.getParameter("telephone"));
        employee.setNic(nic);
        employee.setEmployeeType(employeeType);

        try {
            employeeDAO.update(employee);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.setMessage("Employee data updated successfully.");
            log.info("[" + className + "] editEmployee: created Employee");
        } catch (Exception e) {
            notification.setNotificationType(NotificationType.DANGER);
            notification.setMessage("Something went wrong with updating employee. Please try again.");
            log.error("[" + className + "] editEmployee: failed updating employee");
        }

        return notification;
    }

    /**
     * Deletes the employee using employeeId and returns the Notification object with message
     *
     * @param employeeId String
     * @return the notification with the message
     */
    public Notification deleteEmployee(String employeeId) {
        Notification notification = new Notification();
        EmployeeDAO employeeDAO = new EmployeeDAO();

        try {
            Employee employee = employeeDAO.getById(employeeId);
            employeeDAO.delete(employee);
            notification.setNotificationType(NotificationType.SUCCESS);
            notification.setMessage("Deleted employee successfully");
        } catch (Exception e) {
            notification.setNotificationType(NotificationType.DANGER);
            notification.setMessage("Deleted employee failed. Please try again.");
        }

        return notification;
    }

    /**
     * Returns employee table for controller classes as a HTML table for given searchCriteria
     *
     * @param searchCriteria String
     * @return employee table as a HTML string
     */
    public String getEmployeeTable(String searchCriteria) {
        List<Employee> employeeList;
        EmployeeDAO employeeDAO = new EmployeeDAO();
        DataTableGenerator dataTableGenerator = new DataTableGenerator();
        String table;

        if (searchCriteria.equals("")) {
            employeeList = employeeDAO.getAll();
        } else {
            employeeList = employeeDAO.searchByFirstName(searchCriteria);
        }

        table = dataTableGenerator.getStartTable();
        String dataArray[] = new String[4];
        dataArray[0] = "Name";
        dataArray[1] = "NIC Number";
        dataArray[2] = "Telephone";
        dataArray[3] = "Emergency";
        table = table + dataTableGenerator.getTableHeader(dataArray);
        table = table + dataTableGenerator.getStartTableBody();

        for (Employee employee : employeeList) {
            dataArray[0] = employee.getFirstName() + " " + employee.getLastName();
            dataArray[1] = Integer.toString(employee.getNic()) + "V";
            dataArray[2] = employee.getTelephoneNumber();
            dataArray[3] = employee.getEmergencyContact();
            table = table + dataTableGenerator.getTableBodyRow(dataArray, "edit/" + employee.getId(), "delete/" + employee.getId());
        }

        table = table + dataTableGenerator.getEndTableBody();
        table = table + dataTableGenerator.getEndTable();
        return table;
    }

    /**
     * Returns the employee object from the given employeeId
     *
     * @param employeeId String
     * @return Employee object
     */
    public Employee getEmployee(String employeeId) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee employee;
        try {
            employee = employeeDAO.getById(Integer.parseInt(employeeId));
        } catch (Exception e) {
            log.error("[" + className + "] getEmployee: error in retrieving Employee by Id");
            return null;
        }
        return employee;
    }

    /**
     * Returns driver list for controller classes as select list
     *
     * @return driver list for controller classes as String
     */
    public String getDriverSelectList() {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        EmployeeTypeDAO employeeTypeDAO = new EmployeeTypeDAO();
        EmployeeType employeeType = employeeTypeDAO.getByName("driver");
        List<Employee> employeeList = employeeDAO.getByEmployeeType(employeeType);
        String list = "";
        for (Employee employee : employeeList) {
            list = list + "\t\t<option value =\"" + employee.getId() + "\">" + employee.getFirstName() + " " + employee.getLastName() + "</option>\n";
        }
        log.debug("[" + className + "] getDriverSelectList()");
        return list;
    }

    /**
     * Returns cleaner list for controller classes as select list
     *
     * @return cleaner list for controller classes as String
     */
    public String getCleanerSelectList() {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        EmployeeTypeDAO employeeTypeDAO = new EmployeeTypeDAO();
        EmployeeType employeeType = employeeTypeDAO.getByName("cleaner");
        List<Employee> employeeList = employeeDAO.getByEmployeeType(employeeType);
        String list = "";
        for (Employee employee : employeeList) {
            list = list + "\t\t<option value =\"" + employee.getId() + "\">" + employee.getFirstName() + " " + employee.getLastName() + "</option>\n";
        }
        log.debug("[" + className + "] getCleanerSelectList()");
        return list;
    }
}
