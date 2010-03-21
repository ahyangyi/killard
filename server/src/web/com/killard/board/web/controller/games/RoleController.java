package com.killard.board.web.controller.games;

import com.killard.board.jdo.JdoCardBuilder;
import com.killard.board.parser.ScriptEngine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/{bundleId}/role/{roleId}")
public class RoleController {

    private final ScriptEngine engine = new ScriptEngine();

    private final JdoCardBuilder builder = new JdoCardBuilder();

}
