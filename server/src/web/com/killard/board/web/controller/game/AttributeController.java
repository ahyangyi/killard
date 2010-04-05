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
import com.killard.board.web.util.FormUtils;
import com.killard.board.web.util.ResponseUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.jdo.PersistenceManager;
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
@RequestMapping("/{bundleId}/element/{elementId}/attribute/{attributeId}")
public class AttributeController extends BasicController {

    @RequestMapping(method = {RequestMethod.GET})
    public String view(@PathVariable String bundleId, @PathVariable String elementId,
                       @PathVariable String attributeId,
                       @RequestParam(value = "v", required = false) String packageId,
                       ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        Key packageKey = packageId == null
                ? bundle.getRelease().getKey()
                : KeyFactory.createKey(bundleKey, PackageDO.class.getSimpleName(), packageId);
        Key elementKey = KeyFactory.createKey(packageKey, ElementDO.class.getSimpleName(), elementId);
        Key attributeKey = KeyFactory.createKey(elementKey, AttributeDO.class.getSimpleName(), attributeId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        AttributeDO attribute = pm.getObjectById(AttributeDO.class, attributeKey);

        modelMap.put("bundle", bundle);
        modelMap.put("package", bundle.getDraft());
        modelMap.put("element", element);
        modelMap.put("attribute", attribute);
        return "attribute/view";
    }

    @RequestMapping(method = {RequestMethod.POST})
    public String update(@PathVariable String bundleId, @PathVariable String elementId,
                         @PathVariable String attributeId,
                         @RequestParam("locales") String[] locales,
                         @RequestParam("names") String[] names,
                         @RequestParam("descriptions") String[] descriptions,
                         ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);
        Key attributeKey = KeyFactory.createKey(elementKey, AttributeDO.class.getSimpleName(), attributeId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        AttributeDO attribute = pm.getObjectById(AttributeDO.class, attributeKey);
        FormUtils.updateDescriptors(attribute, locales, names, descriptions);

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", element);
        modelMap.put("attribute", attribute);
        return "attribute/view";
    }

    @RequestMapping(value = "/image.png", method = RequestMethod.GET)
    public void image(@PathVariable String bundleId, @PathVariable String elementId, @PathVariable String attributeId,
                      @RequestParam(value = "v", required = false) String packageId,
                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        Key packageKey = packageId == null
                ? pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey()
                : KeyFactory.createKey(bundleKey, PackageDO.class.getSimpleName(), packageId);
        Key elementKey = KeyFactory.createKey(packageKey, ElementDO.class.getSimpleName(), elementId);
        Key attributeKey = KeyFactory.createKey(elementKey, AttributeDO.class.getSimpleName(), attributeId);

        ResponseUtils.outputImage(request, response, pm.getObjectById(AttributeDO.class, attributeKey));
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public String updateImage(@PathVariable String bundleId, @PathVariable String elementId,
                              @PathVariable String attributeId,
                              @RequestParam("image") MultipartFile file,
                              ModelMap modelMap, HttpServletRequest request) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);
        Key attributeKey = KeyFactory.createKey(elementKey, AttributeDO.class.getSimpleName(), attributeId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        AttributeDO attribute = pm.getObjectById(AttributeDO.class, attributeKey);
        attribute.setImageData(file.getBytes());

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", element);
        modelMap.put("attribute", attribute);
        return "attribute/view";
    }

    @RequestMapping(value = "/handlers", method = {RequestMethod.GET})
    public String handlers(@PathVariable String bundleId, @PathVariable String elementId,
                           @PathVariable String attributeId,
                           @RequestParam(value = "v", required = false) String packageId,
                           @RequestParam(value = "validator_actionClass", required = false)
                           String[] validator_actionClass,
                           @RequestParam(value = "validator_selfTargeted", required = false)
                           boolean[] validator_selfTargeted,
                           @RequestParam(value = "validator_function", required = false) String[] validator_function,
                           @RequestParam(value = "before_actionClass", required = false) String[] before_actionClass,
                           @RequestParam(value = "before_selfTargeted", required = false) boolean[] before_selfTargeted,
                           @RequestParam(value = "before_function", required = false) String[] before_function,
                           @RequestParam(value = "after_actionClass", required = false) String[] after_actionClass,
                           @RequestParam(value = "after_selfTargeted", required = false) boolean[] after_selfTargeted,
                           @RequestParam(value = "after_function", required = false) String[] after_function,
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

        modelMap.put("bundle", bundle);
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
