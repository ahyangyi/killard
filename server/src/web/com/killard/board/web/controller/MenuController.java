package com.killard.board.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
@Controller
public class MenuController {

    @RequestMapping(value = "/help.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String help() throws Exception {
        return "help";
    }

    @RequestMapping(value = "/terms.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String terms() throws Exception {
        return "terms";
    }

    @RequestMapping(value = "/privacy.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String privacy() throws Exception {
        return "privacy";
    }

}
