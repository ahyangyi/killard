package com.killard.board.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;

import javax.jdo.PersistenceManager;
import javax.jdo.Extent;

import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.PackageBundleDO;

import java.util.List;
import java.util.LinkedList;

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
