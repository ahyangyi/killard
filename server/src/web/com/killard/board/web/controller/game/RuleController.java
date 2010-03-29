package com.killard.board.web.controller.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.record.ExecutableActions;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.web.util.FormUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jdo.PersistenceManager;
import java.util.List;

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
    public String update(@PathVariable String bundleId,
                         @RequestParam("validator_actionClass") String[] validator_actionClass,
                         @RequestParam("validator_selfTargeted") boolean[] validator_selfTargeted,
                         @RequestParam("validator_function") String[] validator_function,
                         @RequestParam("before_actionClass") String[] before_actionClass,
                         @RequestParam("before_selfTargeted") boolean[] before_selfTargeted,
                         @RequestParam("before_function") String[] before_function,
                         @RequestParam("after_actionClass") String[] after_actionClass,
                         @RequestParam("after_selfTargeted") boolean[] after_selfTargeted,
                         @RequestParam("after_function") String[] after_function,
                         ModelMap modelMap) throws Exception {
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = PersistenceHelper.getPersistenceManager().getObjectById(PackageBundleDO.class, key);
        PackageDO pack = bundle.getDraft();
        List<AttributeHandler> validator =
                FormUtils.buildHandlers(validator_actionClass, validator_selfTargeted, validator_function);
        List<AttributeHandler> before =
                FormUtils.buildHandlers(before_actionClass, before_selfTargeted, before_function);
        List<AttributeHandler> after =
                FormUtils.buildHandlers(after_actionClass, after_selfTargeted, after_function);
        pack.getRule().setValidators(validator);
        pack.getRule().setBefore(before);
        pack.getRule().setAfter(after);
        modelMap.put("package", pack);
        modelMap.put("actions", ExecutableActions.instance.getRegisterActions());
        return "rule/view";
    }

}
