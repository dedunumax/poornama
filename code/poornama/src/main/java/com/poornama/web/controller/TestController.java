package com.poornama.web.controller;

import com.poornama.api.logging.GlobalLogger;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by dedunu on 11/6/14.
 */
@Controller
@RequestMapping("/test/")
public class TestController {

    private static Logger log = GlobalLogger.getLogger();
    private static String className = TestController.class.getName();

    @RequestMapping(value = "test1", method = RequestMethod.GET)
    public String test1(Model model) {
        model.addAttribute("message", "This is a notification. And we have created a patient on my system.");
        return "notify/success";
    }

    @RequestMapping(value = "test2", method = RequestMethod.GET)
    public String test2() {
        return "test/testSelect";
    }

    @RequestMapping(value = "test3", method = RequestMethod.GET)
    public String test3() {
        return "test/testSelect";
    }
}
