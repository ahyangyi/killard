package com.killard.board.web.controller.game;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.killard.board.jdo.PersistenceHelper;
import com.killard.board.jdo.board.PackageBundleDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.web.controller.BasicController;
import com.killard.board.web.util.FormUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
public class PackageController extends BasicController {

    @RequestMapping(value = "/{bundleId}", method = {RequestMethod.GET})
    public String view(@PathVariable String bundleId,
                       ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        modelMap.put("package", bundle.getRelease());
        return "package/view";
    }

    @RequestMapping(value = "/{bundleId}", method = {RequestMethod.POST})
    public String update(@PathVariable String bundleId,
                         @RequestParam("locales") String[] locales,
                         @RequestParam("names") String[] names,
                         @RequestParam("descriptions") String[] descriptions,
                         ModelMap modelMap) throws Exception {
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = PersistenceHelper.getPersistenceManager().getObjectById(PackageBundleDO.class, key);
        PackageDO pack = bundle.getDraft();
        FormUtils.updateDescriptors(pack, locales, names, descriptions);
        modelMap.put("package", pack);
        return "package/edit";
    }

    @RequestMapping(value = "/{bundleId}/boards", method = {RequestMethod.GET, RequestMethod.POST})
    public String getBoards(@PathVariable String bundleId, ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        modelMap.put("package", bundle.getRelease());
        modelMap.put("boards", bundle.getRelease().getBoards());
        return "boards";
    }

    @RequestMapping(value = "/new", method = {RequestMethod.POST})
    public String newPackage(@PathVariable String bundleId, ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        PackageBundleDO bundle = new PackageBundleDO(bundleId);
        bundle = pm.makePersistent(bundle);
        modelMap.put("package", bundle.draft());
        return "edit";
    }

    @RequestMapping(value = "/{bundleId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable String bundleId, ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        PackageBundleDO bundle = new PackageBundleDO(bundleId);
        bundle = pm.makePersistent(bundle);
        modelMap.put("package", bundle.draft());
        return "package/delete";
    }

    @RequestMapping(value = "/{bundleId}/delete", method = {RequestMethod.POST, RequestMethod.DELETE})
    public void delete(@PathVariable String bundleId,
                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        pm.deletePersistent(bundle);
        redirect("/game", request, response);
    }

    @RequestMapping(value = "/{bundleId}/release", method = {RequestMethod.POST, RequestMethod.DELETE})
    public void release(@PathVariable String bundleId,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        bundle.release();
        redirect("/game", request, response);
    }

    @RequestMapping(value = "/{bundleId}/addmanager", method = RequestMethod.POST)
    public String addManager(@PathVariable String bundleId, @RequestParam("manager") String manager,
                             ModelMap modelMap, HttpServletRequest request) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        modelMap.put("package", bundle.getDraft());
        return "package/edit";
    }

    @RequestMapping(value = "/{bundleId}/deletemanager", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String deleteManager(@PathVariable String bundleId, @RequestParam("id") String id,
                                ModelMap modelMap, HttpServletRequest request) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, key);
        pm.makePersistent(bundle);
        modelMap.put("package", bundle.getDraft());
        return "/game/" + bundle.getName();
    }

    @RequestMapping(value = {"/{bundleId}/newelement"}, method = RequestMethod.GET)
    public String requestNewElement(@PathVariable String bundleId,
                                    ModelMap modelMap) throws Exception {
        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key bundleKey = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        PackageBundleDO bundle = pm.getObjectById(PackageBundleDO.class, bundleKey);
        PackageDO pack = bundle.getDraft();

        modelMap.put("bundle", bundle);
        modelMap.put("package", pack);
        return "package/newelement";
    }

    @RequestMapping(value = "/{bundleId}/newelement", method = RequestMethod.POST)
    public void newElement(@PathVariable String bundleId,
                           @RequestParam String elementId,
                           ModelMap modelMap,
                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = request.getRequestURI().split("/");

        PersistenceManager pm = PersistenceHelper.getPersistenceManager();
        Key key = KeyFactory.createKey(PackageBundleDO.class.getSimpleName(), bundleId);
        Key packageKey = pm.getObjectById(PackageBundleDO.class, key).getDraft().getKey();

        PackageDO pack = pm.getObjectById(PackageDO.class, packageKey);
        pack.newElement(elementId);
        redirect("/game/" + bundleId + "/element/" + elementId, request, response);
    }

}
