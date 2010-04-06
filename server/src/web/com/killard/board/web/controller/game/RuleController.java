package com.killard.board.web.controller.game;

import com.killard.board.card.record.ExecutableActions;
import com.killard.board.jdo.AttributeHandler;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.web.util.FormUtils;
import com.killard.board.web.util.QueryUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
        QueryUtils.fetchPackage(bundleId, packageId, modelMap);
        modelMap.put("actions", ExecutableActions.instance.getRegisterActions());
        return "rule/edit";
    }

    @RequestMapping(method = {RequestMethod.POST})
    public String update(@PathVariable String bundleId,
                         @RequestParam(value = "validator_actionClass", required = false) String[] validator_actionClass,
                         @RequestParam(value = "validator_selfTargeted", required = false) boolean[] validator_selfTargeted,
                         @RequestParam(value = "validator_function", required = false) String[] validator_function,
                         @RequestParam(value = "before_actionClass", required = false) String[] before_actionClass,
                         @RequestParam(value = "before_selfTargeted", required = false) boolean[] before_selfTargeted,
                         @RequestParam(value = "before_function", required = false) String[] before_function,
                         @RequestParam(value = "after_actionClass", required = false) String[] after_actionClass,
                         @RequestParam(value = "after_selfTargeted", required = false) boolean[] after_selfTargeted,
                         @RequestParam(value = "after_function", required = false) String[] after_function,
                         ModelMap modelMap) throws Exception {
        QueryUtils.fetchPackage(bundleId, null, modelMap);
        PackageDO pack = (PackageDO) modelMap.get("package");
        List<AttributeHandler> validator =
                FormUtils.buildHandlers(validator_actionClass, validator_selfTargeted, validator_function);
        List<AttributeHandler> before =
                FormUtils.buildHandlers(before_actionClass, before_selfTargeted, before_function);
        List<AttributeHandler> after =
                FormUtils.buildHandlers(after_actionClass, after_selfTargeted, after_function);
        pack.getRule().setValidators(validator);
        pack.getRule().setBefore(before);
        pack.getRule().setAfter(after);
        PersistenceHelper.commit();
        modelMap.put("actions", ExecutableActions.instance.getRegisterActions());
        return "rule/edit";
    }

}
