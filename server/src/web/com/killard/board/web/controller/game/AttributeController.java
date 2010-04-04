package com.killard.board.web.controller.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.card.record.ExecutableActions;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.AttributeDO;
import com.killard.board.jdo.board.ElementDO;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.web.controller.BasicController;
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
@RequestMapping("/{bundleId}/element/{elementId}/attribute/{attributeId}")
public class AttributeController extends BasicController {

    @RequestMapping(method = {RequestMethod.GET})
    public String view(@PathVariable String bundleId, @PathVariable String elementId,
                       @PathVariable String attributeId,
                       @RequestParam(value = "v", required = false) String packageId,
                       ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        Key packageKey = packageId == null
                ? pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey()
                : KeyFactory.createKey(bundleKey, PackageDO.class.getSimpleName(), packageId);
        Key elementKey = KeyFactory.createKey(packageKey, ElementDO.class.getSimpleName(), elementId);
        Key attributeKey = KeyFactory.createKey(elementKey, AttributeDO.class.getSimpleName(), attributeId);

        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        AttributeDO attribute = pm.getObjectById(AttributeDO.class, attributeKey);

        modelMap.put("package", bundle.getDraft());
        modelMap.put("element", element);
        modelMap.put("attribute", attribute);
        return "attribute/view";
    }

    @RequestMapping(value = "/handlers", method = {RequestMethod.GET})
    public String handlers(@PathVariable String bundleId, @PathVariable String elementId,
                           @PathVariable String attributeId,
                           @RequestParam(value = "v", required = false) String packageId,
                           ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        Key packageKey = packageId == null
                ? pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey()
                : KeyFactory.createKey(bundleKey, PackageDO.class.getSimpleName(), packageId);
        Key elementKey = KeyFactory.createKey(packageKey, ElementDO.class.getSimpleName(), elementId);
        Key attributeKey = KeyFactory.createKey(elementKey, AttributeDO.class.getSimpleName(), attributeId);

        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        AttributeDO attribute = pm.getObjectById(AttributeDO.class, attributeKey);

        modelMap.put("package", bundle.getDraft());
        modelMap.put("element", element);
        modelMap.put("attribute", attribute);
        modelMap.put("actions", ExecutableActions.instance.getRegisterActions());
        return "attribute/handlers";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public String requestDelete(@PathVariable String bundleId, @PathVariable String elementId,
                                @PathVariable String attributeId,
                                ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);
        Key attributeKey = KeyFactory.createKey(elementKey, AttributeDO.class.getSimpleName(), attributeId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        AttributeDO attribute = pm.getObjectById(AttributeDO.class, attributeKey);

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", element);
        modelMap.put("attribute", attribute);
        return "attribute/delete";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String delete(@PathVariable String bundleId, @PathVariable String elementId,
                         @PathVariable String attributeId,
                         ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);
        Key attributeKey = KeyFactory.createKey(elementKey, AttributeDO.class.getSimpleName(), attributeId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        AttributeDO attribute = pm.getObjectById(AttributeDO.class, attributeKey);

        pm.deletePersistent(attribute);
        PersistenceHelper.commit();

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", element);
        return "element/view";
    }

}
