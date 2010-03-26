package com.killard.board.web.controller.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.record.ExecutableActions;
import com.killard.board.jdo.JdoCardBuilder;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.parser.ScriptEngine;
import com.killard.board.web.util.DescriptorUtils;
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
@RequestMapping("/{bundleId}/rule")
public class RuleController {

    private final ScriptEngine engine = new ScriptEngine();

    private final JdoCardBuilder builder = new JdoCardBuilder();

    @RequestMapping(method = {RequestMethod.GET})
    public String viewPackage(@PathVariable String bundleId,
                              @RequestParam(value = "v", required = false) String packageId,
                              ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        Key packageKey = packageId == null
                ? pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey()
                : KeyFactory.createKey(bundleKey, PackageDO.class.getSimpleName(), packageId);

        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        modelMap.put("package", bundle.getDraft());
        modelMap.put("actions", ExecutableActions.instance.getRegisterActions());
        return "rule/view";
    }

    @RequestMapping(method = {RequestMethod.POST})
    public String updatePackage(@PathVariable String bundleId,
                                @RequestParam("locales") String[] locales,
                                @RequestParam("names") String[] names,
                                @RequestParam("descriptions") String[] descriptions,
                                ModelMap modelMap) throws Exception {
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = PersistenceHelper.getPersistenceManager().getObjectById(PackageBundleDO.class, key);
        PackageDO pack = bundle.getDraft();
        DescriptorUtils.updateDescriptors(pack, locales, names, descriptions);
        modelMap.put("package", pack);
        return "rule/edit";
    }

}
