package com.killard.board.web.controller.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.record.ExecutableActions;
import com.killard.board.jdo.JdoCardBuilder;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.RoleDO;
import com.killard.board.parser.ScriptEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jdo.PersistenceManager;

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

    @RequestMapping(method = {RequestMethod.GET})
    public String viewPackage(@PathVariable String bundleId, @PathVariable String roleId,
                              @RequestParam(value = "v", required = false) String packageId,
                              ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        Key packageKey = packageId == null
                ? pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey()
                : KeyFactory.createKey(bundleKey, PackageDO.class.getSimpleName(), packageId);
        Key roleKey = KeyFactory.createKey(packageKey, RoleDO.class.getSimpleName(), roleId);

        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        RoleDO role = pm.getObjectById(RoleDO.class, roleKey);

        modelMap.put("package", bundle.getDraft());
        modelMap.put("role", role);
        modelMap.put("actions", ExecutableActions.instance.getRegisterActions());
        return "role/view";
    }

}
