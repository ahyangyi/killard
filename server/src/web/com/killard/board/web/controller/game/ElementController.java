package com.killard.board.web.controller.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.ElementDO;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.web.controller.BasicController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;

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
@RequestMapping("/{bundleId}/element/{elementId}")
public class ElementController extends BasicController {

    @RequestMapping(method = RequestMethod.GET)
    public String view(@PathVariable String bundleId, @PathVariable String elementId,
                       ModelMap modelMap, HttpServletRequest request) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getRelease();
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", element);
        return "element/view";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newElement(@PathVariable String bundleId, @PathVariable String elementId,
                                   ModelMap modelMap, HttpServletRequest request) throws Exception {
        String[] ids = request.getRequestURI().split("/");

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        Key packageKey = pm.getObjectById(PackageBundleDO.class, key).getDraft().getKey();

        PackageDO pack = pm.getObjectById(PackageDO.class, packageKey);
        ElementDO element = pack.newElement(elementId);
        pm.makePersistent(pack);
        modelMap.put("element", element);
        return "element/edit";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String delete(@PathVariable String bundleId, @PathVariable String elementId,
                         ModelMap modelMap, HttpServletRequest request) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        Key packageKey = pm.getObjectById(PackageBundleDO.class, bundleKey).getDraft().getKey();
        Key elementKey = KeyFactory.createKey(packageKey, ElementDO.class.getSimpleName(), elementId);

        PackageDO pack = pm.getObjectById(PackageDO.class, packageKey);
        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        pm.deletePersistent(element);
        
        modelMap.put("package", pack);
        return "package/view";
    }

}
