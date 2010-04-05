package com.killard.board.web.controller.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.ElementDO;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.parser.Block;
import com.killard.board.parser.Function;
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
import java.util.ArrayList;

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
        PackageDO pack = bundle.getDraft();
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", element);
        return "element/view";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String update(@PathVariable String bundleId, @PathVariable String elementId,
                         @RequestParam("locales") String[] locales,
                         @RequestParam("names") String[] names,
                         @RequestParam("descriptions") String[] descriptions,
                         ModelMap modelMap, HttpServletRequest request) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        FormUtils.updateDescriptors(element, locales, names, descriptions);

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", element);
        return "element/view";
    }

    @RequestMapping(value = "/image.png", method = RequestMethod.GET)
    public void image(@PathVariable String bundleId, @PathVariable String elementId,
                      @RequestParam(value = "v", required = false) String packageId,
                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        Key packageKey = packageId == null
                ? pm.getObjectById(PackageBundleDO.class, bundleKey).getRelease().getKey()
                : KeyFactory.createKey(bundleKey, PackageDO.class.getSimpleName(), packageId);
        Key elementKey = KeyFactory.createKey(packageKey, ElementDO.class.getSimpleName(), elementId);

        ResponseUtils.outputImage(request, response, pm.getObjectById(ElementDO.class, elementKey));
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public String updateImage(@PathVariable String bundleId, @PathVariable String elementId,
                              @RequestParam("image") MultipartFile file,
                              ModelMap modelMap, HttpServletRequest request) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        element.setImageData(file.getBytes());

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", element);
        return "element/view";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public String requestDelete(@PathVariable String bundleId, @PathVariable String elementId,
                                ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", element);
        return "element/delete";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String delete(@PathVariable String bundleId, @PathVariable String elementId,
                         ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        pm.deletePersistent(element);
        PersistenceHelper.commit();

        modelMap.put("package", pack);
        return "package/view";
    }

    @RequestMapping(value = "/newcard", method = RequestMethod.GET)
    public String requestNewCard(@PathVariable String bundleId, @PathVariable String elementId,
                                 ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", element);
        return "element/newcard";
    }

    @RequestMapping(value = "/newcard", method = RequestMethod.POST)
    public void newCard(@PathVariable String bundleId, @PathVariable String elementId,
                        @RequestParam String cardId,
                        ModelMap modelMap,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        Key packageKey = pm.getObjectById(PackageBundleDO.class, bundleKey).getDraft().getKey();
        Key elementKey = KeyFactory.createKey(packageKey, ElementDO.class.getSimpleName(), elementId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        element.newCard(cardId);
        redirect("/game/" + bundleId + "/element/" + elementId + "/card/" + cardId, request, response);
    }

    @RequestMapping(value = "/newattribute", method = RequestMethod.GET)
    public String requestNewAttribute(@PathVariable String bundleId, @PathVariable String elementId,
                                      ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", element);
        return "element/newattribute";
    }

    @RequestMapping(value = "/newattribute", method = RequestMethod.POST)
    public void newAttribute(@PathVariable String bundleId, @PathVariable String elementId,
                             @RequestParam String attributeId,
                             ModelMap modelMap,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        Key packageKey = pm.getObjectById(PackageBundleDO.class, bundleKey).getDraft().getKey();
        Key elementKey = KeyFactory.createKey(packageKey, ElementDO.class.getSimpleName(), elementId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        element.newAttribute(attributeId);
        redirect("/game/" + bundleId + "/element/" + elementId + "/attribute/" + attributeId, request, response);
    }

    @RequestMapping(value = "/newskill", method = RequestMethod.GET)
    public String requestNewSkill(@PathVariable String bundleId, @PathVariable String elementId,
                                  ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();
        Key elementKey = KeyFactory.createKey(pack.getKey(), ElementDO.class.getSimpleName(), elementId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        modelMap.put("element", element);
        return "element/newskill";
    }

    @RequestMapping(value = "/newskill", method = RequestMethod.POST)
    public void newSkill(@PathVariable String bundleId, @PathVariable String elementId,
                         @RequestParam String skillId,
                         ModelMap modelMap,
                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();

        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        Key packageKey = pm.getObjectById(PackageBundleDO.class, bundleKey).getDraft().getKey();
        Key elementKey = KeyFactory.createKey(packageKey, ElementDO.class.getSimpleName(), elementId);

        ElementDO element = pm.getObjectById(ElementDO.class, elementKey);
        element.newSkill(skillId, new ArrayList<String>(), 0, new Function("execute", null, new Block()));
        redirect("/game/" + bundleId + "/element/" + elementId + "/skill/" + skillId, request, response);
    }

}
