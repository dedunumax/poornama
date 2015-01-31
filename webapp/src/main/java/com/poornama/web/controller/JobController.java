package com.poornama.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poornama.api.logging.GlobalLogger;
import com.poornama.api.objects.User;
import com.poornama.api.presentation.Notification;
import com.poornama.api.presentation.NotificationType;
import com.poornama.logic.object.UserLogic;
import com.poornama.logic.object.UserRoleLogic;

@Controller
@RequestMapping("/job/")
public class JobController {
	private static Logger log = GlobalLogger.getLogger();
	private static String className = JobController.class.getName();

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		UserRoleLogic userRoleLogic = new UserRoleLogic();
		model.addAttribute("userRoleList",
				userRoleLogic.getUserRoleSelectList());
		model.addAttribute("pageTitle", "Poornama Transport Service - Job");
		log.debug("[" + className + "] createForm()");
		return "job/create";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String createUser(Model model, HttpServletRequest request) {
		UserLogic userLogic = new UserLogic();
		Notification notification = userLogic.createUser(request);
		model.addAttribute("message", notification.getMessage());
		model.addAttribute("pageTitle", "Poornama Transport Service - Job");
		if (notification.getNotificationType() == NotificationType.DANGER) {
			log.error("[" + className + "] createUser: failed");
			return "notify/danger";
		}
		if (notification.getNotificationType() == NotificationType.SUCCESS) {
			log.info("[" + className + "] createUser: success");
			return "notify/success";
		}
		log.fatal("[" + className + "] createUser: cannot reach this phrase");
		return "redirect:/";
	}

	@RequestMapping(value = "edit/{userId}", method = RequestMethod.GET)
	public String editForm(Model model, @PathVariable("userId") String userId) {
		UserLogic userLogic = new UserLogic();
		User user;
		try {
			user = userLogic.getUserById(userId);
		} catch (Exception e) {
			log.error("[" + className
					+ "] editForm: error in retrieving User by Id");
			model.addAttribute("message",
					"Something went wrong with User data. Please try again.");
			return "notify/danger";
		}
		UserRoleLogic userRoleLogic = new UserRoleLogic();
		model.addAttribute("userRoleList",
				userRoleLogic.getUserRoleSelectList());
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("displayName", user.getDisplayName());
		model.addAttribute("userRole", user.getUserRole().getId());
		model.addAttribute("pageTitle", "Poornama Transport Service - User");
		return "user/edit";
	}

	@RequestMapping(value = "edit/{userId}", method = RequestMethod.POST)
	public String editUser(Model model, @PathVariable("userId") String userId,
			HttpServletRequest request) {
		UserLogic userLogic = new UserLogic();
		Notification notification = userLogic.editUser(request, userId);
		log.debug("[" + className + "] editEmployee()");
		model.addAttribute("message", notification.getMessage());
		model.addAttribute("pageTitle", "Poornama Transport Service - User");
		if (notification.getNotificationType() == NotificationType.DANGER) {
			log.error("[" + className + "] editUser: failed");
			return "notify/danger";
		}
		if (notification.getNotificationType() == NotificationType.SUCCESS) {
			log.info("[" + className + "] editUser: success");
			return "notify/success";
		}
		log.fatal("[" + className + "] editEmployee: cannot reach this phrase");
		return "redirect:/";
	}

	@RequestMapping(value = "delete/{userId}", method = RequestMethod.GET)
	public String deleteForm(Model model, @PathVariable("userId") String userId) {
		UserLogic userLogic = new UserLogic();
		User user;
		try {
			user = userLogic.getUserById(userId);
		} catch (Exception e) {
			log.error("[" + className
					+ "] deleteForm: error in retrieving User by Id");
			model.addAttribute("message",
					"Something went wrong with User data. Please try again.");
			return "notify/danger";
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("displayName", user.getDisplayName());
		model.addAttribute("userRole", user.getUserRole().getDisplayName());
		model.addAttribute("pageTitle", "Poornama Transport Service - User");
		return "user/delete";
	}

	@RequestMapping(value = "delete/{userId}", method = RequestMethod.POST)
	public String deleteUser(Model model, @PathVariable("userId") String userId) {
		UserLogic userLogic = new UserLogic();
		Notification notification = userLogic.deleteUser(userId);
		model.addAttribute("pageTitle", "Poornama Transport Service - User");
		switch (notification.getNotificationType()) {
		case DANGER:
			model.addAttribute("message", notification.getMessage());
			log.error("[" + className + "] deleteUser: error in deleting User");
			return "notify/danger";
		case SUCCESS:
			model.addAttribute("message", notification.getMessage());
			log.info("[" + className
					+ "] deleteUser: deleted User successfully");
			return "notify/success";
		default:
			model.addAttribute("message",
					"Something went wrong. Please contact developer.");
			log.error("[" + className
					+ "] deleteUser: fatal error in deleting User");
			return "notify/danger";
		}
	}

	@RequestMapping(value = "settings", method = RequestMethod.GET)
	public String settingPage(Model model) throws IOException {
		log.debug("[" + className + "] settingPage()");
		model.addAttribute("pageTitle", "Poornama Transport Service - User");
		return "user/settings";
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String searchForm(Model model) throws IOException {
		UserLogic userLogic = new UserLogic();
		String table = userLogic.getUserTable();
		model.addAttribute("table", table);
		model.addAttribute("pageTitle", "Poornama Transport Service - User");
		log.debug("[" + className + "] searchForm()");
		return "user/search";
	}
}