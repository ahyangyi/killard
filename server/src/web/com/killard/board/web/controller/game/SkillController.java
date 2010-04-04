package com.killard.board.web.controller.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.ElementDO;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.SkillDO;
import com.killard.board.web.controller.BasicController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
@RequestMapping("/{bundleId}/element/{elementId}/card/{cardId}/skill/{skillId}")
public class SkillController extends BasicController {

    @RequestMapping(method = {RequestMethod.GET})
    public String view(@PathVariable String bundleId, @PathVariable String elementId,
                       @PathVariable String skillId,
                       ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);
        Key skillKey = KeyFactory.createKey(elementKey, SkillDO.class.getSimpleName(), skillId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        SkillDO skill = pm.getObjectById(SkillDO.class, skillKey);

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", element);
        modelMap.put("skill", skill);
        return "skill/view";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public String requestDelete(@PathVariable String bundleId, @PathVariable String elementId,
                                @PathVariable String skillId,
                                ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);
        Key skillKey = KeyFactory.createKey(elementKey, SkillDO.class.getSimpleName(), skillId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        SkillDO skill = pm.getObjectById(SkillDO.class, skillKey);

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", element);
        modelMap.put("skill", skill);
        return "skill/delete";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String delete(@PathVariable String bundleId, @PathVariable String elementId,
                         @PathVariable String skillId,
                         ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);
        Key skillKey = KeyFactory.createKey(elementKey, SkillDO.class.getSimpleName(), skillId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        SkillDO skill = pm.getObjectById(SkillDO.class, skillKey);

        pm.deletePersistent(skill);
        PersistenceHelper.commit();

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", element);
        return "element/view";
    }
}
