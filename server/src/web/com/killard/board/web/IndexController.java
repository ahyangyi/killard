package com.killard.board.web;

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
public class IndexController {

    @RequestMapping(value = "/index.*", method = {RequestMethod.GET, RequestMethod.POST})
    public String index() throws Exception {
        return "index";
    }

}
