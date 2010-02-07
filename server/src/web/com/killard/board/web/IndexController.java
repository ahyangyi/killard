package com.killard.board.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class IndexController {

    @RequestMapping(value = "/index.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String index() throws Exception {
        return "index";
    }
    
    @RequestMapping(value = "/test.*", method = RequestMethod.POST)
    public void cast(@RequestParam("name") String name,
                     @RequestParam("address") String[] address,
                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("address:" + address.length);
        for (String addr : address) System.out.println(addr);
    }

}
