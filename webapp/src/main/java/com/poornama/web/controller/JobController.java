package com.poornama.web.controller;

import com.poornama.api.logging.GlobalLogger;
import com.poornama.api.objects.Client;
import com.poornama.api.objects.Job;
import com.poornama.api.objects.JobTemplate;
import com.poornama.api.presentation.Notification;
import com.poornama.api.presentation.NotificationType;
import com.poornama.logic.object.EmployeeLogic;
import com.poornama.logic.object.JobLogic;
import com.poornama.logic.object.JobTemplateLogic;
import com.poornama.logic.object.VehicleLogic;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author dedunu
 */
@Controller
@RequestMapping("/job/")
public class JobController {
    private static Logger log = GlobalLogger.getLogger();
    private static String className = JobController.class.getName();

    @Autowired
    JobTemplateLogic jobTemplateLogic;

    @Autowired
    JobLogic jobLogic;

    @Autowired
    VehicleLogic vehicleLogic;

    @Autowired
    EmployeeLogic employeeLogic;

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm(Model model) {
        try {

            model.addAttribute("jobTemplateList",
                    jobTemplateLogic.getJobTemplateSelectList());
            model.addAttribute("vehicleList",
                    vehicleLogic.getPrimeMoverVehicleSelectList());
            model.addAttribute("driverList",
                    employeeLogic.getDriverSelectList());
            model.addAttribute("cleanerList",
                    employeeLogic.getCleanerSelectList());

            model.addAttribute("pageTitle", "Poornama Transport Service - Job");

            log.debug("[" + className + "] createForm()");
            return "job/create";
        } catch (Exception e) {
            log.error("[" + className + "]" + e.getMessage());
            return "redirect:/system/error";
        }

    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String createJob(Model model, HttpServletRequest request) {
        try {
            Notification notification = jobLogic.createJob(request);
            model.addAttribute("message", notification.getMessage());
            model.addAttribute("pageTitle", "Poornama Transport Service - Job");
            if (notification.getNotificationType() == NotificationType.DANGER) {
                log.error("[" + className + "] createJob: failed");
                return "notify/danger";
            }
            if (notification.getNotificationType() == NotificationType.SUCCESS) {
                log.info("[" + className + "] createJob: success");
                model.addAttribute("jobId", notification.getInteger());
                return "job/createSuccess";
            }
            log.fatal("[" + className + "] createJob: cannot reach this phrase");
            return "redirect:/";
        } catch (Exception e) {
            log.error("[" + className + "]" + e.getMessage());
            return "redirect:/system/error";
        }
    }

    @RequestMapping(value = "edit/{jobId}", method = RequestMethod.GET)
    public String editForm(Model model, @PathVariable("jobId") String jobId) {
        try {
            Job job = jobLogic.getJob(jobId);

            if (job == null) {
                log.error("[" + className + "] editForm: retrieving Job failed");
            }

            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
            Date startDate = job.getStartDate();
            Date endDate = job.getEndDate();

            model.addAttribute("driverList",
                    employeeLogic.getDriverSelectList());
            model.addAttribute("cleanerList",
                    employeeLogic.getCleanerSelectList());
            model.addAttribute("jobTemplateList",
                    jobTemplateLogic.getJobTemplateSelectList());
            model.addAttribute("vehicleList",
                    vehicleLogic.getPrimeMoverVehicleSelectList());

            model.addAttribute("jobId", job.getId());
            model.addAttribute("jobTemplate", job.getJobTemplate().getId());
            model.addAttribute("driver", job.getDriver().getId());
            model.addAttribute("cleaner", job.getCleaner().getId());
            model.addAttribute("vehicle", job.getVehicle().getId());
            model.addAttribute("startDate", dateFormat.format(startDate));
            model.addAttribute("endDate", dateFormat.format(endDate));
            model.addAttribute("labourCharges", job.getLabourCharges().toBigInteger().toString());
            model.addAttribute("hireCharges", job.getHireCharges().toBigInteger().toString());
            model.addAttribute("containerCharges", job.getContainerCharges().toBigInteger().toString());
            model.addAttribute("detentionCharges", job.getDetentionCharges().toBigInteger().toString());
            model.addAttribute("hourlyDetentionCharges", job.getHourlyDetentionCharges().toBigInteger().toString());
            model.addAttribute("dailyContainerCharges", job.getDailyContainerCharges().toBigInteger().toString());
            model.addAttribute("freeHours", job.getFreeHours());

            model.addAttribute("pageTitle", "Poornama Transport Service - Job");
            return "job/edit";
        } catch (Exception e) {
            log.error("[" + className + "]" + e.getMessage());
            return "redirect:/system/error";
        }
    }

    @RequestMapping(value = "edit/{jobId}", method = RequestMethod.POST)
    public String editJob(Model model, @PathVariable("jobId") String jobId, HttpServletRequest request) {
        try {
            Notification notification = jobLogic.editJob(request, jobId);
            log.debug("[" + className + "] editJob()");
            model.addAttribute("pageTitle", "Poornama Transport Service - Job");
            model.addAttribute("message", notification.getMessage());
            if (notification.getNotificationType() == NotificationType.DANGER) {
                log.error("[" + className + "] editJob: failed");
                return "notify/danger";
            }
            if (notification.getNotificationType() == NotificationType.SUCCESS) {
                log.info("[" + className + "] editJob: success");
                return "notify/success";
            }
            log.fatal("[" + className + "] editJob: cannot reach this phrase");
            return "redirect:/";
        } catch (Exception e) {
            log.error("[" + className + "]" + e.getMessage());
            return "redirect:/system/error";
        }
    }

    @RequestMapping(value = "delete/{jobId}", method = RequestMethod.GET)
    public String deleteForm(Model model, @PathVariable("jobId") String jobId) {
        try {
            Job job;
            job = jobLogic.getJob(jobId);
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
            Date startDate = job.getStartDate();
            Date endDate = job.getEndDate();

            model.addAttribute("jobId", job.getId());
            model.addAttribute("fromLocation", job.getJobTemplate().getFromLocation());
            model.addAttribute("toLocation", job.getJobTemplate().getToLocation());
            model.addAttribute("driver", job.getDriver().getFirstName() + " " + job.getDriver().getLastName());
            model.addAttribute("cleaner", job.getCleaner().getFirstName() + " " + job.getCleaner().getLastName());
            model.addAttribute("vehicle", job.getVehicle().getVehicleNumber());
            model.addAttribute("startDate", dateFormat.format(startDate));
            model.addAttribute("endDate", dateFormat.format(endDate));
            model.addAttribute("labourCharges", job.getLabourCharges().toBigInteger().toString());
            model.addAttribute("hireCharges", job.getHireCharges().toBigInteger().toString());
            model.addAttribute("containerCharges", job.getContainerCharges().toBigInteger().toString());
            model.addAttribute("detentionCharges", job.getDetentionCharges().toBigInteger().toString());
            model.addAttribute("hourlyDetentionCharges", job.getHourlyDetentionCharges().toBigInteger().toString());
            model.addAttribute("dailyContainerCharges", job.getDailyContainerCharges().toBigInteger().toString());
            model.addAttribute("freeHours", job.getFreeHours());

            model.addAttribute("pageTitle", "Poornama Transport Service - Job");
            return "job/delete";
        } catch (Exception e) {
            log.error("[" + className + "]" + e.getMessage());
            return "redirect:/system/error";
        }
    }

    @RequestMapping(value = "delete/{jobId}", method = RequestMethod.POST)
    public String deleteJob(Model model, @PathVariable("jobId") String jobId) {
        try {
            Notification notification = jobLogic.deleteJob(jobId);
            model.addAttribute("pageTitle", "Poornama Transport Service - Job");
            switch (notification.getNotificationType()) {
                case DANGER:
                    model.addAttribute("message", notification.getMessage());
                    log.error("[" + className + "] deleteJob: error in deleting Job");
                    return "notify/danger";
                case SUCCESS:
                    model.addAttribute("message", notification.getMessage());
                    log.info("[" + className + "] deleteJob: deleted Job successfully");
                    return "notify/success";
                default:
                    model.addAttribute("message", "Something went wrong. Please contact developer.");
                    log.error("[" + className + "] deleteJob: fatal error in deleting Job");
                    return "notify/danger";
            }
        } catch (Exception e) {
            log.error("[" + className + "]" + e.getMessage());
            return "redirect:/system/error";
        }
    }

    @RequestMapping(value = "print/{jobId}", method = RequestMethod.GET)
    public String printForm(Model model, @PathVariable("jobId") String jobId) {
        try {
            Job job = jobLogic.getJob(jobId);
            JobTemplate jobTemplate = job.getJobTemplate();
            if (job == null) {
                log.error("[" + className + "] printForm: retrieving Job failed");
            }

            DateFormat invoiceDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Client client = jobTemplate.getClient();

            String clientString = client.getOrganizationName() + "<br/>";
            clientString = clientString + client.getAddress();

            model.addAttribute("jobId", job.getId());
            model.addAttribute("date", invoiceDateFormat.format(new Date()));
            model.addAttribute("client", clientString);
            model.addAttribute("jobTemplate", job.getJobTemplate().getId());
            model.addAttribute("table", jobLogic.getInvoiceTable(jobId));

            model.addAttribute("pageTitle", "Poornama Transport Service - Job");
            return "job/print";
        } catch (Exception e) {
            log.error("[" + className + "]" + e.getMessage());
            return "redirect:/system/error";
        }
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String searchForm(Model model) throws IOException {
        try {
            String table = jobLogic.getJobTable("");
            model.addAttribute("table", table);
            model.addAttribute("pageTitle", "Poornama Transport Service - Job");
            log.debug("[" + className + "] searchForm()");
            return "job/search";
        } catch (Exception e) {
            log.error("[" + className + "]" + e.getMessage());
            return "redirect:/system/error";
        }
    }

    @RequestMapping(value = "search/{id}", method = RequestMethod.POST)
    public void searchAJAX(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        try {
            String table = jobLogic.getJobTable(id);
            response.getWriter().print(table);
            log.debug("[" + className + "] searchAJAX()");
        } catch (Exception e) {
            log.error("[" + className + "]" + e.getMessage());
        }
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public void searchAJAXAll(HttpServletResponse response) throws IOException {
        try {
            searchAJAX("", response);
            log.debug("[" + className + "] searchAJAXAll()");
        } catch (Exception e) {
            log.error("[" + className + "]" + e.getMessage());
        }
    }

    @RequestMapping(value = "details/{jobTemplateId}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public JsonObject getJobTemplateDetailsAJAX(@PathVariable("jobTemplateId") String jobTemplateId) throws IOException {
        try {
            log.debug("[" + className + "] getJobTemplateDetailsAJAX()");
            return jobTemplateLogic.getJobTemplateDetails(jobTemplateId);
        } catch (Exception e) {
            log.error("[" + className + "]" + e.getMessage());
            return null;
        }
    }

}
